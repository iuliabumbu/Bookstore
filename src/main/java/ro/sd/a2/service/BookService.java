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

    public void saveBook(BookDto bookDto){
        Book book = Mapper.BookDtoMapping(bookDto);
        BookValidators.validateBook(book);
        bookRepository.save(book);

        log.info("Successfully created book " + book.toString());
    }

    public void updateBook(BookDto bookDto){

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

    public void deleteBook(BookDto bookDto){

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

    public Optional<Book> findBookById(String id){
        BookValidators.validateBookId(id);
        Optional<Book> book = bookRepository.findById(id);

        return book;
    }

    public List<Book> findAllBooks(){
        List<Book> books = bookRepository.findAll();
        return books;
    }

    public void addPromotion(float price, Book promotionBook){

        Optional<Book> book = findBookById(promotionBook.getId());

        if( book.isPresent()){
            book.get().setPromotionPrice(book.get().getPrice() - book.get().getPrice() * price / 100);
            BookValidators.validateBook(book.get());
            bookRepository.save(book.get());

            log.info("Successfully added promotion to book " + book.toString());

        }
        else{
            throw new InvalidParameterException(ErrorMessages.INVALID_FIND);
        }
    }

    public void deletePromotions(){
        List<Book> books = findAllBooks();

        for(Book book : books){
            book.setPromotionPrice(-1);
            BookValidators.validateBook(book);
            bookRepository.save(book);
        }
    }
}
