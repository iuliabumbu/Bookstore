package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.entity.Book;
import ro.sd.a2.entity.Sort;
import ro.sd.a2.service.BookService;
import ro.sd.a2.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private static final Logger log = LoggerFactory.getLogger(ViewController.class);
    @Autowired
    private BookService bookService;

    @GetMapping("/indexView")
    public ModelAndView mainMenuUser(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("indexView");
        return mav;
    }

    @GetMapping("/showBooks")
    public ModelAndView showBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        books.removeIf(p -> p.getDeleted().equals("yes"));
        mav.addObject("sort", sort);
        mav.addObject("books", books);
        mav.setViewName("showBooks");
        return mav;
    }

    @PostMapping("/showBooks")
    public ModelAndView sortBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        books.removeIf(p -> p.getDeleted().equals("yes"));

        if(sort.getOrder().equals("low-to-high")){
            mav.addObject("books", Book.lowToHigh(books));
        }
        else if(sort.getOrder().equals("high-to-low")){
            mav.addObject("books", Book.highToLow(books));
        }
        mav.setViewName("showBooks");
        return mav;
    }
    @GetMapping("/romanceBooks")
    public ModelAndView romanceBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("Romance"));
        mav.addObject("sort", sort);
        mav.addObject("books", books);
        mav.setViewName("romanceBooks");
        return mav;
    }

    @PostMapping("/romanceBooks")
    public ModelAndView sortRomanceBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        books.removeIf(p -> p.getDeleted().equals("yes"));

        if(sort.getOrder().equals("low-to-high")){
            mav.addObject("books", Book.lowToHigh(books));
        }
        else if(sort.getOrder().equals("high-to-low")){
            mav.addObject("books", Book.highToLow(books));
        }
        mav.setViewName("romanceBooks");
        return mav;
    }
    @GetMapping("/scienceFictionBooks")
    public ModelAndView scienceFictionBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("Science Fiction"));
        mav.addObject("sort", sort);
        mav.addObject("books", books);
        mav.setViewName("scienceFictionBooks");
        return mav;
    }

    @PostMapping("/scienceFictionBooks")
    public ModelAndView sortScienceFictionBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        books.removeIf(p -> p.getDeleted().equals("yes"));

        if(sort.getOrder().equals("low-to-high")){
            mav.addObject("books", Book.lowToHigh(books));
        }
        else if(sort.getOrder().equals("high-to-low")){
            mav.addObject("books", Book.highToLow(books));
        }
        mav.setViewName("scienceFictionBooks");
        return mav;
    }
    @GetMapping("/historyBooks")
    public ModelAndView historyBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("History"));
        mav.addObject("sort", sort);
        mav.addObject("books", books);
        mav.setViewName("historyBooks");
        return mav;
    }

    @PostMapping("/historyBooks")
    public ModelAndView sortHistoryBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        books.removeIf(p -> p.getDeleted().equals("yes"));

        if(sort.getOrder().equals("low-to-high")){
            mav.addObject("books", Book.lowToHigh(books));
        }
        else if(sort.getOrder().equals("high-to-low")){
            mav.addObject("books", Book.highToLow(books));
        }
        mav.setViewName("historyBooks");
        return mav;
    }

    @GetMapping("/travelBooks")
    public ModelAndView travelBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("Travel"));
        mav.addObject("sort", sort);
        mav.addObject("books", books);
        mav.setViewName("travelBooks");
        return mav;
    }

    @PostMapping("/travelBooks")
    public ModelAndView sortTravelBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        books.removeIf(p -> p.getDeleted().equals("yes"));

        if(sort.getOrder().equals("low-to-high")){
            mav.addObject("books", Book.lowToHigh(books));
        }
        else if(sort.getOrder().equals("high-to-low")){
            mav.addObject("books", Book.highToLow(books));
        }
        mav.setViewName("travelBooks");
        return mav;
    }

    @GetMapping("/actionBooks")
    public ModelAndView actionBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("Action"));
        mav.addObject("sort", sort);
        mav.addObject("books", books);
        mav.setViewName("actionBooks");
        return mav;
    }

    @PostMapping("/actionBooks")
    public ModelAndView sortActionBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        books.removeIf(p -> p.getDeleted().equals("yes"));

        if(sort.getOrder().equals("low-to-high")){
            mav.addObject("books", Book.lowToHigh(books));
        }
        else if(sort.getOrder().equals("high-to-low")){
            mav.addObject("books", Book.highToLow(books));
        }
        mav.setViewName("actionBooks");
        return mav;
    }

    @GetMapping("/personalDevelopmentBooks")
    public ModelAndView personalDevelopmentBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("Personal Development"));
        mav.addObject("sort", sort);
        mav.addObject("books", books);
        mav.setViewName("personalDevelopmentBooks");
        return mav;
    }

    @PostMapping("/personalDevelopmentBooks")
    public ModelAndView sortPersonalDevelopmentBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        books.removeIf(p -> p.getDeleted().equals("yes"));

        if(sort.getOrder().equals("low-to-high")){
            mav.addObject("books", Book.lowToHigh(books));
        }
        else if(sort.getOrder().equals("high-to-low")){
            mav.addObject("books", Book.highToLow(books));
        }
        mav.setViewName("personalDevelopmentBooks");
        return mav;
    }
    @GetMapping("/otherBooks")
    public ModelAndView otherBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> p.getGenre().getType().equals("Romance"));
        books.removeIf( p -> p.getGenre().getType().equals("Science Fiction"));
        books.removeIf( p -> p.getGenre().getType().equals("History"));
        books.removeIf( p -> p.getGenre().getType().equals("Travel"));
        books.removeIf( p -> p.getGenre().getType().equals("Action"));
        books.removeIf( p -> p.getGenre().getType().equals("Personal Development"));

        mav.addObject("sort", sort);
        mav.addObject("books", books);
        mav.setViewName("otherBooks");
        return mav;
    }

    @PostMapping("/otherBooks")
    public ModelAndView sortOtherBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        books.removeIf(p -> p.getDeleted().equals("yes"));

        if(sort.getOrder().equals("low-to-high")){
            mav.addObject("books", Book.lowToHigh(books));
        }
        else if(sort.getOrder().equals("high-to-low")){
            mav.addObject("books", Book.highToLow(books));
        }
        mav.setViewName("otherBooks");
        return mav;
    }
}
