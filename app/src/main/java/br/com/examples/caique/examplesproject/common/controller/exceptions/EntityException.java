package br.com.examples.caique.examplesproject.common.controller.exceptions;

/**
 * Created by FBisca on 30/07/2015.
 */
public class EntityException extends RuntimeException {

    public EntityException() {
    }

    public EntityException(String detailMessage) {
        super(detailMessage);
    }
}
