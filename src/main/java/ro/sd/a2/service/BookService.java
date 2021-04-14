package ro.sd.a2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.sd.a2.entity.Book;
import ro.sd.a2.entity.Genre;
import ro.sd.a2.entity.User;
import ro.sd.a2.service.repository.BookRepository;
import ro.sd.a2.service.repository.GenreRepository;
import ro.sd.a2.validators.BookValidators;
import ro.sd.a2.validators.GenreValidators;
import ro.sd.a2.validators.UserValidators;

import java.util.List;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){

        this.bookRepository = bookRepository;
    }

    public void saveBook(Book book){
        BookValidators.validateBook(book);
        bookRepository.save(book);

        log.info("Successfully created book " + book.toString());
    }

    public List<Book> findAllBooks(){
        List<Book> books = bookRepository.findAll();

        return books;
    }
}
