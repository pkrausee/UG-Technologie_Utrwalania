package com.pkrause.project2.result;

public interface IResult<T> {
    T getResult();

    boolean isSuccess();

    String getMessage();
}
