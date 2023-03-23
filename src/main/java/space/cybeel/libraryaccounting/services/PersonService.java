package space.cybeel.libraryaccounting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.cybeel.libraryaccounting.models.Person;
import space.cybeel.libraryaccounting.repositories.PersonRepository;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Person findOne(int id){
        return repository.findById(id).orElse(null);
    }

    public List<Person> findAll(){
        return repository.findAll();
    }

    @Transactional
    public void update(int id, Person updated){
        updated.setId(id);
        repository.save(updated);
    }

    @Transactional
    public void save(Person person){
        repository.save(person);
    }

    @Transactional
    public void delete(Person person){
        repository.delete(person);
    }

    @Transactional
    public void deleteById(int id){
        repository.deleteById(id);
    }
}
