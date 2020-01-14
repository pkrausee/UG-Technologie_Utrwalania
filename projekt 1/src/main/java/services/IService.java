package services;

import response.IResult;

import java.util.List;

public interface IService<TEntity, TKey> {
    IResult<TEntity> tryCreate(TEntity entity);

    IResult<TEntity> readByKey(TKey key);

    IResult<List<TEntity>> readAll();

    IResult<TEntity> tryUpdate(TKey id, TEntity entity);

    IResult<TEntity> tryDelete(TKey id);

    void drop();
}
