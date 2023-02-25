package space.cybeel.libraryaccounting.dao;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import space.cybeel.libraryaccounting.dto.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Component
public class PersonDAO extends DataAccesObject<Person> {
    public PersonDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Person show(int id) {
        return null;
    }

    @Override
    public List<Person> index() {
        return null;
    }

    @Override
    public void save(Person person) {

    }

    @Override
    public void update(int id, Person updated) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    @SneakyThrows
    public boolean isExists(int id) {
        PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM book WHERE id=?");

        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
    }
}
