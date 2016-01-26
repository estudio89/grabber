package br.com.estudio89.grabber.annotation;

/**
 * Created by luccascorrea on 1/25/16.
 *
 */
public interface InstantiationListener<E> {
    void onNewInstance(E instance);
}
