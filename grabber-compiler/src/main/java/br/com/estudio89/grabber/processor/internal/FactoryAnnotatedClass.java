package br.com.estudio89.grabber.processor.internal;


import br.com.estudio89.grabber.annotation.Register;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

/**
 * Created by luccascorrea on 12/16/15.
 *
 */
public class FactoryAnnotatedClass implements Comparable<FactoryAnnotatedClass> {

    private TypeElement annotatedClassElement;
    private String qualifiedSuperClassName;
    private String simpleTypeName;
    private int priority;

    public FactoryAnnotatedClass(TypeElement classElement) throws IllegalArgumentException {
        this.annotatedClassElement = classElement;
        Register annotation = classElement.getAnnotation(Register.class);

        // Get the full QualifiedTypeName
        try {
            Class<?> clazz = annotation.type();
            qualifiedSuperClassName = clazz.getCanonicalName();
            simpleTypeName = clazz.getSimpleName();
        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            qualifiedSuperClassName = classTypeElement.getQualifiedName().toString();
            simpleTypeName = classTypeElement.getSimpleName().toString();
        }
        priority = annotation.priority();
    }

    /**
     * Get the id
     * return the id
     */
    public String getId() {
        return annotatedClassElement.getQualifiedName().toString();
    }

    /**
     * Get the full qualified name of the type specified in  {@link Register#type()}.
     *
     * @return qualified name
     */
    public String getQualifiedFactoryGroupName() {
        return qualifiedSuperClassName;
    }


    /**
     * Get the simple name of the type specified in  {@link Register#type()}.
     *
     * @return qualified name
     */
    public String getSimpleFactoryGroupName() {
        return simpleTypeName;
    }

    /**
     * The original element that was annotated with @Factory
     */
    public TypeElement getTypeElement() {
        return annotatedClassElement;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(FactoryAnnotatedClass o) {
        if (priority == o.getPriority()) {
            return 0;
        } else if (priority < o.getPriority()) {
            return -1;
        } else {
            return 1;
        }

    }
}
