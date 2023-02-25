package space.cybeel.libraryaccounting.dao;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.cybeel.libraryaccounting.dto.Book;
import space.cybeel.libraryaccounting.util.DTOList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Component
public class BookDAO extends DataAccesObject<Book> {
    @Autowired
    public BookDAO(Connection connection) {
        super(connection);
    }


    @Override
    public void save(Book book) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO book(title, authorId, year) VALUES(?, ?, ?)");
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getAuthorId());
            statement.setInt(3, book.getYear());

            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Book show(int id) {
        Book book = null;
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM book WHERE id=?");

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            book = parseBook(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return book;
    }

    @Override
    public List<Book> index() {
        try {
            List<Book> list = new DTOList<>();
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM book");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                try {
                    list.add(parseBook(resultSet));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            resultSet.close();

            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    @Override
    @SneakyThrows
    public void delete(int id) {
        PreparedStatement statement =
                connection.prepareStatement("DELETE FROM book WHERE id=?");
        statement.setInt(1, id);

        statement.executeUpdate();
    }

    @SneakyThrows
    @Override
    public void update(int id, Book updated) {
        PreparedStatement statement =
                connection.prepareStatement("UPDATE book SET authorId = ?, title = ?, year = ? WHERE id = ?");

        statement.setInt(1, updated.getAuthorId());
        statement.setString(2, updated.getTitle());
        statement.setInt(3, updated.getYear());
        statement.setInt(4, updated.getId());

        statement.executeUpdate();
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

    private Book parseBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();

        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthorId(resultSet.getInt("authorId"));
        book.setYear(resultSet.getInt("year"));

        return book;
    }
}
