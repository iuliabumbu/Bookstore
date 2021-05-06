package ro.sd.a2.utils;

import ro.sd.a2.entity.Book;

import java.util.List;

public interface Strategy {

    public void generateDocument(List<Book> books);
}
