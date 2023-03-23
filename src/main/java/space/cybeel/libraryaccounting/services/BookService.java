package space.cybeel.libraryaccounting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import space.cybeel.libraryaccounting.models.Book;
import space.cybeel.libraryaccounting.models.Person;
import space.cybeel.libraryaccounting.repositories.BookRepository;
import space.cybeel.libraryaccounting.util.BookInfoFormer;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository repository;
    private final PersonService personService;


    @Autowired
    public BookService(BookRepository repository, PersonService personService) {
        this.repository = repository;
        this.personService = personService;
    }

    public Book findOne(int id){
        return repository.findById(id).orElse(null);
    }

    public List<Book> findAll(){
        return repository.findAll();
    }

    public List<Book> findAllTaken(){
        return repository.findByTakerIdIsNotNull();
    }

    public List<Book> findByTaker(Person person){
        return repository.findByTakerId(person.getId());
    }

    public List<Book> findAllByAuthorId(int authorId){
        return repository.findByAuthorId(authorId);
    }

    @Transactional
    public void update(int id, Book updated){
        System.out.println("new author is "+ updated.getAuthor());
        updated.setId(id);
        repository.save(updated);
    }

    @Transactional
    public void save(Book book){
        repository.save(book);
    }

    @Transactional
    public void deleteById(int id){
        repository.deleteById(id);
    }

    @Transactional
    public void giveOut(int bookId, int personId){
        Person person = personService.findOne(personId);
        Book book = findOne(bookId);

        book.giveOut(person);
    }

    @Transactional
    public void giveBack(int bookId){
        Book book = findOne(bookId);

        book.giveBack();
    }

    public List<Book> indexList(){
        List<Book> b = repository.findAll();

        b.forEach(x -> x.setFormedInfo(BookInfoFormer.form(x)));
        return b;
    }

    public void addShowAttributes(int bookId, Model model) {
        Book book = repository.findOneById(bookId);

        boolean isTaken = book.isTaken();

        if (isTaken)
            model.addAttribute("bookAvailability",
                    book.getTaker().getFullName() + " has took this book");

        else {
            model.addAttribute("personList", personService.findAll());
            model.addAttribute("person", new Person());
            model.addAttribute("bookAvailability",
                    "This book is available now");
        }

        model.addAttribute("bookInfo", BookInfoFormer.form(book));
        model.addAttribute("book", book);
        model.addAttribute("isIssued", isTaken);
    }


}
