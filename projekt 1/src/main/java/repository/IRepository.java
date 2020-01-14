package repository;

import domain.IEntity;

import java.sql.Connection;
import java.util.List;

public interface IRepository<TEntity extends IEntity, TKey> {
    Connection getConnection();

    boolean create(TEntity entity);

    TEntity readByKey(TKey key);

    List<TEntity> readAll();

    boolean update(TKey id, TEntity entity);

    boolean delete(TKey id);

    void drop();
}
