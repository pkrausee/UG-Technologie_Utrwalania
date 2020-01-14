package com.pkrause.project2.result;

import java.util.List;

public class MultipleResult<T> implements IResult<List<T>> {
    private List<T> result;
    private boolean success;
    private String message;

    public MultipleResult() {
    }

    public MultipleResult(List<T> result, boolean success, String message) {
        this.result = result;
        this.success = success;
        this.message = message;
    }

    public List<T> getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
