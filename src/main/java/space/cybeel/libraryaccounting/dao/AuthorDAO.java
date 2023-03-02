package space.cybeel.libraryaccounting.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.cybeel.libraryaccounting.dto.Author;
import space.cybeel.libraryaccounting.dto.Book;
import space.cybeel.libraryaccounting.util.DTOList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Component
public class AuthorDAO extends DataAccessObject<Author> {


    @Autowired
    public AuthorDAO(Connection connection) {
        super(connection);
    }

    public void initAuthors(List<? extends Book> books) {
        if (books.isEmpty())
            return;
        DTOList<Author> authors = (DTOList<Author>) index();
        books.forEach(x -> x.setAuthor(authors.getByIdOrDefault(x.getAuthorId(), Author.UNKNOWN_AUTHOR)));
    }

    @Override
    public void save(Author obj) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO author(name) VALUES(?)");

            statement.setString(1, obj.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
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
        } catch (SQLException e) {
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
                } catch (SQLException ignored) {
                }
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM author WHERE id=?");

            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int id, Author updated) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("UPDATE author SET name=? WHERE id=?");
            statement.setString(1, updated.getName());
            statement.setInt(2, updated.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isExists(Author author) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM author WHERE name=? or id=?");

            statement.setString(1, author.getName());
            statement.setInt(2, author.getId());

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Author parseAuthor(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");

        return new Author(name, id);
    }
}
