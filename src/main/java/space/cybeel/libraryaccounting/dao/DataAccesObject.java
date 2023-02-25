package space.cybeel.libraryaccounting.dao;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.util.List;

public abstract class DataAccesObject<T> {
    protected Connection connection;

    @Autowired
    public DataAccesObject(Connection connection) {
        this.connection = connection;
    }

    public abstract void save(T obj);

    public abstract T show(int id);

    public abstract List<T> index();

    public abstract void delete(int id);

    public abstract void update(int id, T updated);

    public abstract boolean isExists(int id);
}
