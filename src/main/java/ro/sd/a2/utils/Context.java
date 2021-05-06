package ro.sd.a2.utils;

import ro.sd.a2.entity.Book;

import java.util.List;

public class Context {

    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public void executeStrategy(List<Book> books){
         strategy.generateDocument(books);
    }
}
