package br.com.examples.caique.examplesproject.common.model.generic;

import java.io.Serializable;

public abstract class GenericResult<T> implements Serializable {

    private T result;

    public abstract T parseResult(String json);

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
