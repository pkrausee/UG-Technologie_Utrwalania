package pl.ug.tuj.lab4;

import java.util.List;

public interface IListOperation <TType> {
    TType get (int id);
    boolean delete(int id);
    boolean add (TType element);

    void clear();
}
