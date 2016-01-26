package br.com.estudio89.grabber;

import br.com.estudio89.grabber.annotation.GrabberFactory;

/**
 * Created by luccascorrea on 1/25/16.
 *
 */
public class Grabber {

    public static <T> GrabberFactory<T> getFactory(Class<T> klass) throws ClassNotFoundException {
        String pkgName = klass.getPackage().getName();
        String factoryName = klass.getSimpleName() + "Factory";

        try {
            return (GrabberFactory<T>) Class.forName(pkgName + "." + factoryName).newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
