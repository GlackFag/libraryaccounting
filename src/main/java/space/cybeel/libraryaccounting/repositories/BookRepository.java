package space.cybeel.libraryaccounting.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.cybeel.libraryaccounting.models.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
     List<Book> findByTakerId(int takerId);

    List<Book> findByTakerIdIsNotNull();

    List<Book> findByAuthorId(int id);

    Page<Book> findAll(Pageable pageable);
    Book findOneById(int id);



}
