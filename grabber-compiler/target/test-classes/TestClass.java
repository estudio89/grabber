package br.com.estudio89.grabber.test;

import br.com.estudio89.grabber.annotation.Register;

/**
 * Created by luccascorrea on 12/16/15.
 */

interface TestInterface {
    void myMethod();
}

@Register(type = TestInterface.class)
public class TestClass implements TestInterface {

    public void myMethod() {

    }
}
