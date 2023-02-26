package space.cybeel.libraryaccounting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import space.cybeel.libraryaccounting.dao.AuthorDAO;
import space.cybeel.libraryaccounting.dao.BookDAO;
import space.cybeel.libraryaccounting.dao.IssuedBookDAO;
import space.cybeel.libraryaccounting.dao.PersonDAO;
import space.cybeel.libraryaccounting.dto.Author;
import space.cybeel.libraryaccounting.dto.Book;
import space.cybeel.libraryaccounting.dto.Person;
import space.cybeel.libraryaccounting.util.BookInfoFormer;
import space.cybeel.libraryaccounting.util.DTOList;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    private final IssuedBookDAO issuedBookDAO;

    private final AuthorDAO authorDAO;
    private final BookDAO bookDAO;

    private final PersonDAO personDAO;

    @Autowired
    public BookController(IssuedBookDAO issuedBookDAO, AuthorDAO authorDAO, BookDAO bookDAO, PersonDAO personDAO) {
        this.issuedBookDAO = issuedBookDAO;
        this.authorDAO = authorDAO;
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping({"/", ""})
    public String index(Model model) {
        List<Book> books = bookDAO.index();

        model.addAttribute("bookList", books);

        return "book/index";
    }

    @PatchMapping({"/{id}", ""})
    public String edit(@PathVariable("id") int id,
                       @ModelAttribute("book") Book book) {
        bookDAO.update(id, book);

        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.show(id));

        return "book/edit";
    }

    @PostMapping({"/", ""})
    public String add(@ModelAttribute("book") Book book) {
        bookDAO.save(book);

        return "redirect:/book";
    }

    @GetMapping("/new")
    public String addPage(Model model) {
        model.addAttribute("book", new Book());
        return "book/new";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int bookId) {
        bookDAO.delete(bookId);

        return "redirect:/book";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.show(id);
        if (book == null)
            return "404";

        addShowAttributes(book, model);
        return "book/show";
    }

    private void addShowAttributes(Book book, Model model) {
        boolean isIssued = issuedBookDAO.isIssued(book.getId());

        if (isIssued) {
            String issuerName = issuedBookDAO.getIssuerName(book.getId());
            model.addAttribute("bookAvailability",
                    issuerName + Constants.TOOK_BOOK);
        } else {
            model.addAttribute("personList", personDAO.index());
            model.addAttribute("person", new Person());
            model.addAttribute("bookAvailability",
                    Constants.BOOK_IS_AVAILABLE);
        }

        model.addAttribute("book", book);
        model.addAttribute("isIssued", isIssued);
        model.addAttribute("bookInfo", BookInfoFormer.form(book, authorDAO.show(book.getAuthorId())));
    }

    @ModelAttribute
    public void defaultAttributes(Model model) {
        model.addAttribute("issuedBookDAO", issuedBookDAO);
        model.addAttribute("authorDAO", authorDAO);
        model.addAttribute("unknownAuthor", Author.UNKNOWN_AUTHOR);
        model.addAttribute("authorList", (DTOList<Author>) authorDAO.index());
    }

    private interface Constants {
        String TOOK_BOOK = " has took this book";
        String BOOK_IS_AVAILABLE = "This book is available now";
    }
}
