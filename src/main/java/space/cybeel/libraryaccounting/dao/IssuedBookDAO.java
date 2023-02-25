package space.cybeel.libraryaccounting.dao;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Component
public class IssuedBookDAO extends DataAccesObject {
    public IssuedBookDAO(Connection connection) {
        super(connection);
    }

    @SneakyThrows
    public void issue(int personId, int bookId) {
        PreparedStatement statement =
                connection.prepareStatement("INSERT INTO issuedBook VALUES(?,?)");
        statement.setInt(1, personId);
        statement.setInt(2, bookId);

        statement.executeUpdate();
    }


    @SneakyThrows
    public void release(int bookId) {
        PreparedStatement statement =
                connection.prepareStatement("DELETE FROM issuedBook WHERE bookId=?");

        statement.setInt(1, bookId);

        statement.executeUpdate();
    }

    @SneakyThrows
    public boolean isIssued(int bookId) {
        PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM issuedBook WHERE bookId=?");

        statement.setInt(1, bookId);

        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
    }

    @SneakyThrows
    public int getIssuerId(int bookId) {
        PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM issuedBook WHERE bookId=?");

        statement.setInt(1, bookId);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return resultSet.getInt("personId");
    }

    @SneakyThrows
    public String getIssuerName(int bookId){
        PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM person WHERE id=?");

        statement.setInt(1, getIssuerId(bookId));

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return resultSet.getString("name");
    }

    @Override
    public boolean isExists(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object show(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List index() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(int id, Object updated) {
        throw new UnsupportedOperationException();
    }
}
