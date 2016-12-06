package br.com.examples.caique.examplesproject.common.controller.exceptions;

/**
 * Created by FBisca on 16/06/2015.
 */
public class TaskException extends RuntimeException {

    private int code;
    private String message;

    public TaskException() {
        super();
    }

    public TaskException(Throwable throwable) {
        super(throwable);
        setMessage(throwable.getMessage());
    }

    public TaskException(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
