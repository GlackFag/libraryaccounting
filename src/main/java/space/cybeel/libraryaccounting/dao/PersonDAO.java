package space.cybeel.libraryaccounting.dao;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import space.cybeel.libraryaccounting.dto.Person;
import space.cybeel.libraryaccounting.util.DTOList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO extends DataAccesObject<Person> {
    public PersonDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Person show(int id) {
        Person person = null;
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM person WHERE id=?");

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
                person = parsePerson(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public List<Person> index() {
        List<Person> people = new DTOList<>();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM person");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
                people.add(parsePerson(resultSet));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return people;
    }

    @Override
    public void save(Person person) {
        try{
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO person(fullname, yearborn) VALUES(?, ?)");

            statement.setString(1, person.getFullName());
            statement.setInt(2, person.getYearBorn());

            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(int id, Person updated) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("UPDATE person SET fullname=?, yearborn=? WHERE id=?");

            statement.setString(1, updated.getFullName());
            statement.setInt(2, updated.getYearBorn());
            statement.setInt(3, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM person WHERE id=?");

            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public boolean isExists(int id) {
        PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM person WHERE id=?");

        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
    }

    private static Person parsePerson(ResultSet resultSet) throws SQLException {
        Person person = new Person();

        person.setId(resultSet.getInt("id"));
        person.setFullName(resultSet.getString("fullname"));
        person.setYearBorn(resultSet.getInt("yearBorn"));

        return person;
    }
}
