package ro.sd.a2.factory;

import ro.sd.a2.entity.Book;
import ro.sd.a2.entity.Genre;

import java.util.UUID;

public class BookFactory {

    public static Book generateBook(){
        Book book = new Book();
        book.setDeleted("no");
        book.setPromotionPrice(-1);
        book.setId(UUID.randomUUID().toString());
        return book;
    }
}
