package space.cybeel.libraryaccounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.cybeel.libraryaccounting.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
