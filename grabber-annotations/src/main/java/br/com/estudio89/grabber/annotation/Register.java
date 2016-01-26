package br.com.estudio89.grabber.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by luccascorrea on 12/16/15.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Register {

    Class type();

    int priority() default 0;
}
