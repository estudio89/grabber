package br.com.estudio89.grabber;

import br.com.estudio89.grabber.processor.GrabberProcessor;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;
import org.junit.Test;

import static com.google.common.truth.Truth.assert_;

/**
 * Created by luccascorrea on 12/16/15.
 */
public class ProcessorTests {

    @Test
    public void testProcessor() throws Exception {
        assert_().about(JavaSourceSubjectFactory.javaSource())
                .that(JavaFileObjects.forResource("TestSyncManagerOne.java"))
                .processedWith(new GrabberProcessor())
                .compilesWithoutError()
                .and().generatesSources(JavaFileObjects.forResource("SyncManagerFactory.java"));

    }
}

