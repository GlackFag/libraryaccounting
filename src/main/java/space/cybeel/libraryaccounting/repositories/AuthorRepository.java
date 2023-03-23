package space.cybeel.libraryaccounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.cybeel.libraryaccounting.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
