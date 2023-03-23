package space.cybeel.libraryaccounting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import space.cybeel.libraryaccounting.models.Author;
import space.cybeel.libraryaccounting.models.Book;
import space.cybeel.libraryaccounting.repositories.AuthorRepository;
import space.cybeel.libraryaccounting.util.BookInfoFormer;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AuthorService {
    private final AuthorRepository repository;
    private final BookService bookService;

    @Autowired
    public AuthorService(AuthorRepository repository, BookService bookService) {
        this.repository = repository;
        this.bookService = bookService;
    }

    public Author findOne(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Author> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void update(int id, Author updated) {
        updated.setId(id);
        System.out.println(updated);
        repository.save(updated);
    }

    @Transactional
    public void save(Author author) {
        repository.save(author);
    }

    @Transactional
    public void delete(Author author) {
        repository.delete(author);
    }

    @Transactional
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public void addShowAttributes(int authorId, Model model) {
        Author author = repository.findById(authorId).orElse(Author.UNKNOWN_AUTHOR);

        List<Book> bookList = bookService.findAllByAuthorId(author.getId());

        bookList.forEach(x -> { x.setFormedInfo(BookInfoFormer.form(x, author).
                replaceFirst("([A-Za-z]+ )+- ", ""));
            System.out.println(x.getFormedInfo());});

        model.addAttribute("author", author);
        model.addAttribute("bookList", bookList);
    }
}

