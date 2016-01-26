package br.com.estudio89.grabber.annotation;

import java.util.List;

/**
 * Created by luccascorrea on 1/25/16.
 */
public interface GrabberFactory<T> {
    List<T> listAll(InstantiationListener<T> listener);
    List<T> listAll();
}
