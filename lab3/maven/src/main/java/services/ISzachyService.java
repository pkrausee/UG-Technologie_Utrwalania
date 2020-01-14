package services;

import domain.Szachy;

public interface ISzachyService {
    void create(Szachy szachy);
    void read();
    void update(int id, Szachy szachy);
    void delete(int id);
}
