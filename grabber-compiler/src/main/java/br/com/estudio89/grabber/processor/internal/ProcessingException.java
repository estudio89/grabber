package br.com.estudio89.grabber.processor.internal;

import javax.lang.model.element.Element;

/**
 * Created by luccascorrea on 12/16/15.
 */
public class ProcessingException extends Exception {

    Element element;

    public ProcessingException(Element element, String msg, Object... args) {
        super(String.format(msg, args));
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
