package space.cybeel.libraryaccounting.dao;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.cybeel.libraryaccounting.dto.Author;
import space.cybeel.libraryaccounting.util.DTOList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Component
public class AuthorDAO extends DataAccesObject<Author> {


    @Autowired
    public AuthorDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void save(Author obj) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO author(name) VALUES(?)");

            statement.setString(1, obj.getName());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Author show(int id) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM author WHERE id=?");

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
                return parseAuthor(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Author.UNKNOWN_AUTHOR;
    }

    @Override
    public List<Author> index() {
        try {
            List<Author> result = new DTOList<>();
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM author");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                try {
                    result.add(parseAuthor(resultSet));
                } catch (Exception ignored) {
                }
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    @SneakyThrows
    public void delete(int id) {
        PreparedStatement statement =
                connection.prepareStatement("DELETE FROM author WHERE id=?");

        statement.executeUpdate();
    }

    @Override
    public void update(int id, Author updated) {
        //todo
    }

    @Override
    @SneakyThrows
    public boolean isExists(int id) {
        PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM author WHERE id=?");

        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
    }

    private static Author parseAuthor(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");

        return new Author(name, id);
    }
}
