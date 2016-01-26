package br.com.estudio89.grabber.test;

import br.com.estudio89.grabber.annotation.Register;

/**
 * Created by luccascorrea on 12/16/15.
 */

interface SyncManager<T> {
    void myMethod();
}

@Register(type = SyncManager.class)
public class TestSyncManagerOne implements SyncManager<Object> {

    public void myMethod() {

    }

    @Register(type = SyncManager.class, priority=1)
    public static class TestSyncManagerTwo implements SyncManager<Object> {

        public void myMethod() {

        }
    }
}
