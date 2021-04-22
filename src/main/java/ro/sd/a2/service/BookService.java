package ro.sd.a2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.sd.a2.dto.BookDto;
import ro.sd.a2.entity.Book;
import ro.sd.a2.entity.Genre;
import ro.sd.a2.entity.User;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.mappers.Mapper;
import ro.sd.a2.messages.ErrorMessages;
import ro.sd.a2.service.repository.BookRepository;
import ro.sd.a2.service.repository.GenreRepository;
import ro.sd.a2.validators.BookValidators;
import ro.sd.a2.validators.GenreValidators;
import ro.sd.a2.validators.UserValidators;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    /**
     * Metoda responsabila cu inserarea in baza de date a unei noi carti
     * @param bookDto - cartea pe care dorim sa o introducem
     */
    public void saveBook(BookDto bookDto){
        log.info("Insert book attempt");

        Book book = Mapper.BookDtoMapping(bookDto);
        BookValidators.validateBook(book);
        bookRepository.save(book);

        log.info("Successfully created book " + book.toString());
    }
    /**
     * Metoda responsabila cu actualizarea datelor unei carti
     * @param bookDto - cartea pe care dorim sa o actualizam
     * @throws InvalidParameterException - daca cartea pe care vrem sa o actualizam nu exista in baza de date
     */
    public void updateBook(BookDto bookDto){
        log.info("Update book attempt");

        Optional<Book> book = findBookById(bookDto.getId());

        if( book.isPresent()){
            book.get().setTitle(bookDto.getTitle());
            book.get().setAuthor(bookDto.getAuthor());
            book.get().setDescription(bookDto.getDescription());
            book.get().setPrice(bookDto.getPrice());

            BookValidators.validateBook(book.get());
            bookRepository.save(book.get());

            log.info("Successfully updated book " + book.toString());

        }
        else{
            throw new InvalidParameterException(ErrorMessages.INVALID_FIND);
        }
    }
    /**
     * Metoda responsabila cu stergerea unei carti (stergere marcata prin setarea "yes" a campului deleted, nu stergere fizica)
     * @param bookDto - cartea pe care dorim sa o stergem
     * @throws InvalidParameterException - daca cartea pe care vrem sa o stergem nu exista in baza de date
     */
    public void deleteBook(BookDto bookDto){
        log.info("Delete book attempt");

        Optional<Book> book = findBookById(bookDto.getId());

        if( book.isPresent()){
            book.get().setDeleted("yes");

            BookValidators.validateBook(book.get());
            bookRepository.save(book.get());

            log.info("Successfully deleted book " + book.toString());

        }
        else{
            throw new InvalidParameterException(ErrorMessages.INVALID_FIND);
        }
    }
    /**
     * Metoda responsabila cu gasirea unei carti din baza de date
     * @param id - id-ul cartii pe care dorim sa o gasim
     * @return - returneaza cartea gasita
     */
    public Optional<Book> findBookById(String id){
        log.info("Find book by id attempt " + id);
        BookValidators.validateBookId(id);
        Optional<Book> book = bookRepository.findById(id);

        return book;
    }
    /**
     * Metoda responsabila cu gasirea tuturor cartilor din baza de date
     * @return - returneaza o lista ce contine toate cartile gasite
     */
    public List<Book> findAllBooks(){
        log.info("Find all books attempt");
        List<Book> books = bookRepository.findAll();
        return books;
    }
    /**
     * Metoda responsabila cu adaugarea unei promotii
     * @param procent - procentul cu care vrem sa reducem
     * @param promotionBook - cartea careia ii adaugam promotia
     * @return - returneaza cartea gasita
     */
    public void addPromotion(float procent, Book promotionBook){
        log.info("Add promotion attempt");

        Optional<Book> book = findBookById(promotionBook.getId());

        if( book.isPresent()){
            book.get().setPromotionPrice(book.get().getPrice() - book.get().getPrice() * procent / 100);
            BookValidators.validateBook(book.get());
            bookRepository.save(book.get());

            log.info("Successfully added promotion to book " + book.toString());

        }
        else{
            throw new InvalidParameterException(ErrorMessages.INVALID_FIND);
        }
    }
    /**
     * Metoda responsabila cu stergerea tuturor promotiilor adaugate
     * codificarea -1 a campului promotionPrice inseamna ca este eliminata promotia
     */
    public void deletePromotions(){
        log.info("Delete all promotions attempt");
        List<Book> books = findAllBooks();

        for(Book book : books){
            book.setPromotionPrice(-1);
            BookValidators.validateBook(book);
            bookRepository.save(book);
        }
    }
}
