package com.pkrause.project2.result;

public class SingleResult<T> implements IResult<T> {
    private T result;
    private boolean success;
    private String message;

    public SingleResult() {
    }

    public SingleResult(T result, boolean success, String message) {
        this.result = result;
        this.success = success;
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
