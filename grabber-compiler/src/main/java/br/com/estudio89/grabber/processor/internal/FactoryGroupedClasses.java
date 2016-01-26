package br.com.estudio89.grabber.processor.internal;

import br.com.estudio89.grabber.annotation.GrabberFactory;
import br.com.estudio89.grabber.annotation.InstantiationListener;
import br.com.estudio89.grabber.annotation.Register;
import com.squareup.javapoet.*;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.*;

/**
 * This class holds all {@link FactoryAnnotatedClass}s that belongs to one grabber. In other words,
 * this class holds a list with all @Factory annotated classes. This class also checks if the id of
 * each @Factory annotated class is unique.
 *
 * @author Hannes Dorfmann
 */
public class FactoryGroupedClasses {

    /**
     * Will be added to the name of the generated grabber class
     */
    private static final String SUFFIX = "Factory";

    private String qualifiedClassName;

    private Map<String, FactoryAnnotatedClass> itemsMap =
            new LinkedHashMap<String, FactoryAnnotatedClass>();

    public FactoryGroupedClasses(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
    }

    /**
     * Adds an annotated class to this grabber.
     *
     * @throws ProcessingException if another annotated class with the same id is
     * already present.
     */
    public void add(FactoryAnnotatedClass toInsert) throws ProcessingException {

        FactoryAnnotatedClass existing = itemsMap.get(toInsert.getId());
        if (existing != null) {

            // Alredy existing
            throw new ProcessingException(toInsert.getTypeElement(),
                    "Conflict: The class %s is annotated with @%s with id ='%s' but %s already uses the same id",
                    toInsert.getTypeElement().getQualifiedName().toString(), Register.class.getSimpleName(),
                    toInsert.getId(), existing.getTypeElement().getQualifiedName().toString());
        }

        itemsMap.put(toInsert.getId(), toInsert);
    }

    public void generateCode(Elements elementUtils, Filer filer) throws IOException {

        TypeElement superClassName = elementUtils.getTypeElement(qualifiedClassName);
        String factoryClassName = superClassName.getSimpleName() + SUFFIX;
        PackageElement pkg = elementUtils.getPackageOf(superClassName);
        String packageName = pkg.getQualifiedName().toString();

        ClassName generatedFactory = ClassName.get(packageName, factoryClassName);
        ClassName superName = ClassName.get(superClassName);
        ClassName grabberFactory = ClassName.get(GrabberFactory.class);
        TypeName grabberFactoryOfItems = ParameterizedTypeName.get(grabberFactory, superName);
        ClassName list = ClassName.get(List.class);
        ClassName arrayList = ClassName.get(ArrayList.class);
        TypeName listOfItems = ParameterizedTypeName.get(list, superName);
        ClassName listener = ClassName.get(InstantiationListener.class);
        TypeName listenerOfItems = ParameterizedTypeName.get(listener, superName);


        MethodSpec.Builder listAllWithListener = MethodSpec.methodBuilder("listAll")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(listenerOfItems, "listener")
                .returns(listOfItems);

        // Generate items list
        listAllWithListener.beginControlFlow("if (items == null)");
        listAllWithListener.addStatement("items = new $T<>()", arrayList);
        int idx = 0;

        List<FactoryAnnotatedClass> values = new ArrayList<FactoryAnnotatedClass>(itemsMap.values());
        Collections.sort(values);
        Collections.reverse(values);

        for (FactoryAnnotatedClass item : values) {
            listAllWithListener.addStatement("$T item$L = new $T()", superName, idx, item.getTypeElement());
            listAllWithListener.beginControlFlow("if (listener != null)");
            listAllWithListener.addStatement("listener.onNewInstance(item$L)", idx);
            listAllWithListener.endControlFlow();
            listAllWithListener.addStatement("items.add(item$L)", idx);
            idx++;
        }
        listAllWithListener.endControlFlow();
        listAllWithListener.addStatement("return items");
        MethodSpec listAllWithListenerBuilt = listAllWithListener.build();

        MethodSpec.Builder listAll = MethodSpec.methodBuilder("listAll")
                .addModifiers(Modifier.PUBLIC)
                .returns(listOfItems);
        listAll.addStatement("return $N(null)", listAllWithListenerBuilt);
        MethodSpec listAllBuilt = listAll.build();

        TypeSpec typeSpec = TypeSpec.classBuilder(factoryClassName)
                .addSuperinterface(grabberFactoryOfItems)
                .addMethod(listAllWithListenerBuilt)
                .addMethod(listAllBuilt)
                .addModifiers(Modifier.PUBLIC)
                .addField(listOfItems, "items", Modifier.PRIVATE, Modifier.STATIC)
                .build();

        // Write file
        JavaFile.builder(packageName, typeSpec).build().writeTo(filer);
    }

}