package space.cybeel.libraryaccounting.dao;

import org.springframework.stereotype.Component;
import space.cybeel.libraryaccounting.dto.Book;
import space.cybeel.libraryaccounting.dto.IssuedBook;
import space.cybeel.libraryaccounting.dto.Person;
import space.cybeel.libraryaccounting.util.DTOList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class IssuedBookDAO extends DataAccessObject<IssuedBook> {
    private final BookDAO bookDAO;

    private final PersonDAO personDAO;

    public IssuedBookDAO(Connection connection, BookDAO bookDAO, PersonDAO personDAO) {
        super(connection);
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    public void issue(int personId, int bookId) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO issuedBook VALUES(?,?)");
            statement.setInt(1, personId);
            statement.setInt(2, bookId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void release(int bookId) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM issuedBook WHERE bookId=?");

            statement.setInt(1, bookId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isIssued(int bookId) {
        try{
        PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM issuedBook WHERE bookId=?");

        statement.setInt(1, bookId);

        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getIssuerId(int bookId) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT personId FROM issuedBook WHERE bookId=?");

            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return resultSet.getInt("personId");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getIssuerName(int bookId) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT fullname FROM person WHERE id=(SELECT personId FROM issuedBook WHERE bookId=?)");
            statement.setInt(1, bookId);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return resultSet.getString("fullname");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    public List<IssuedBook> index() {
        List<IssuedBook> issuedBooks = new DTOList<>();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM issuedBook");

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                DTOList<Book> books = (DTOList<Book>) bookDAO.index();
                DTOList<Person> people = (DTOList<Person>) personDAO.index();

                books.forEach(IssuedBook::new);
                do {
                    int bookId = resultSet.getInt("bookId");
                    IssuedBook ib = new IssuedBook(books.getByIdOrNull(bookId));

                    int personId = resultSet.getInt("personId");
                    ib.setIssuer(people.getByIdOrNull(personId));

                    issuedBooks.add(ib);
                } while (resultSet.next());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return issuedBooks;
    }

    @Override
    public boolean isExists(IssuedBook obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(IssuedBook obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IssuedBook show(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(int id, IssuedBook updated) {
        throw new UnsupportedOperationException();
    }

}
