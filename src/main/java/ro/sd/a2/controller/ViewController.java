package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.entity.Book;
import ro.sd.a2.entity.Sort;
import ro.sd.a2.service.BookService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private static final Logger log = LoggerFactory.getLogger(ViewController.class);
    @Autowired
    private BookService bookService;

    @GetMapping("/indexView")
    public ModelAndView mainMenuUser(){
        log.info("Called /indexView page");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("indexView");
        return mav;
    }

    @GetMapping("/showBooks")
    public ModelAndView showBooks(Sort sort){
        log.info("Called /showBooks page");
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
        books.removeIf(p -> p.getDeleted().equals("yes"));
        mav.addObject("sort", sort);
        mav.addObject("books", books);
        mav.setViewName("showBooks");
        return mav;
    }

    @GetMapping("/romanceBooks")
    public ModelAndView romanceBooks(Sort sort){
        log.info("Called /romanceBooks page");
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("Romance"));
        mav.addObject("sort", sort);
        mav.addObject("books", books);
        mav.setViewName("romanceBooks");
        return mav;
    }

    @GetMapping("/scienceFictionBooks")
    public ModelAndView scienceFictionBooks(Sort sort){
        log.info("Called /scienceFictionBooks page");
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("Science Fiction"));
        mav.addObject("sort", sort);
        mav.addObject("books", books);
        mav.setViewName("scienceFictionBooks");
        return mav;
    }

    @GetMapping("/historyBooks")
    public ModelAndView historyBooks(Sort sort){
        log.info("Called /historyBooks page");
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("History"));
        mav.addObject("sort", sort);
        mav.addObject("books", books);
        mav.setViewName("historyBooks");
        return mav;
    }


    @GetMapping("/travelBooks")
    public ModelAndView travelBooks(Sort sort){
        log.info("Called /travelBooks page");
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("Travel"));
        mav.addObject("sort", sort);
        mav.addObject("books", books);
        mav.setViewName("travelBooks");
        return mav;
    }
    @GetMapping("/actionBooks")
    public ModelAndView actionBooks(Sort sort){
        log.info("Called /actionBooks page");
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("Action"));
        mav.addObject("sort", sort);
        mav.addObject("books", books);
        mav.setViewName("actionBooks");
        return mav;
    }

    @GetMapping("/personalDevelopmentBooks")
    public ModelAndView personalDevelopmentBooks(Sort sort){
        log.info("Called /personalDevelopmentBooks page");
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("Personal Development"));
        mav.addObject("sort", sort);
        mav.addObject("books", books);
        mav.setViewName("personalDevelopmentBooks");
        return mav;
    }

    @GetMapping("/otherBooks")
    public ModelAndView otherBooks(Sort sort){
        log.info("Called /otherBooks page");
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
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

    /**
     * Metoda responsabila cu afisarea tuturor cartilor
     * @param sort - criteriul de sortare utilizat
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/showBooks")
    public ModelAndView sortBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
        books.removeIf(p -> p.getDeleted().equals("yes"));

        if(sort.getOrder().equals("low-to-high")){
            mav.addObject("books", Book.lowToHigh(books));
            log.info("Sort low to high on");
        }
        else if(sort.getOrder().equals("high-to-low")){
            mav.addObject("books", Book.highToLow(books));
            log.info("Sort high to low on");
        }
        mav.setViewName("showBooks");
        return mav;
    }

    /**
     * Metoda responsabila cu afisarea tuturor cartilor din categoria Romance
     * @param sort - criteriul de sortare utilizat
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/romanceBooks")
    public ModelAndView sortRomanceBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("Romance"));

        if(sort.getOrder().equals("low-to-high")){
            mav.addObject("books", Book.lowToHigh(books));
            log.info("Sort low to high on");
        }
        else if(sort.getOrder().equals("high-to-low")){
            mav.addObject("books", Book.highToLow(books));
            log.info("Sort high to low on");
        }
        mav.setViewName("romanceBooks");
        return mav;
    }

    /**
     * Metoda responsabila cu afisarea tuturor cartilor din categoria Science Fiction
     * @param sort - criteriul de sortare utilizat
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/scienceFictionBooks")
    public ModelAndView sortScienceFictionBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("Science Fiction"));

        if(sort.getOrder().equals("low-to-high")){
            mav.addObject("books", Book.lowToHigh(books));
            log.info("Sort low to high on");
        }
        else if(sort.getOrder().equals("high-to-low")){
            mav.addObject("books", Book.highToLow(books));
            log.info("Sort high to low on");
        }
        mav.setViewName("scienceFictionBooks");
        return mav;
    }

    /**
     * Metoda responsabila cu afisarea tuturor cartilor din categoria History
     * @param sort - criteriul de sortare utilizat
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/historyBooks")
    public ModelAndView sortHistoryBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("History"));

        if(sort.getOrder().equals("low-to-high")){
            mav.addObject("books", Book.lowToHigh(books));
            log.info("Sort low to high on");
        }
        else if(sort.getOrder().equals("high-to-low")){
            mav.addObject("books", Book.highToLow(books));
            log.info("Sort high to low on");
        }
        mav.setViewName("historyBooks");
        return mav;
    }

    /**
     * Metoda responsabila cu afisarea tuturor cartilor din categoria Travel
     * @param sort - criteriul de sortare utilizat
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/travelBooks")
    public ModelAndView sortTravelBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("Travel"));

        if(sort.getOrder().equals("low-to-high")){
            mav.addObject("books", Book.lowToHigh(books));
            log.info("Sort low to high on");
        }
        else if(sort.getOrder().equals("high-to-low")){
            mav.addObject("books", Book.highToLow(books));
            log.info("Sort high to low on");
        }
        mav.setViewName("travelBooks");
        return mav;
    }
    /**
     * Metoda responsabila cu afisarea tuturor cartilor din categoria Action
     * @param sort - criteriul de sortare utilizat
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/actionBooks")
    public ModelAndView sortActionBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("Action"));

        if(sort.getOrder().equals("low-to-high")){
            mav.addObject("books", Book.lowToHigh(books));
            log.info("Sort low to high on");
        }
        else if(sort.getOrder().equals("high-to-low")){
            mav.addObject("books", Book.highToLow(books));
            log.info("Sort high to low on");
        }
        mav.setViewName("actionBooks");
        return mav;
    }

    /**
     * Metoda responsabila cu afisarea tuturor cartilor din categoria Personal Development
     * @param sort - criteriul de sortare utilizat
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/personalDevelopmentBooks")
    public ModelAndView sortPersonalDevelopmentBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> !p.getGenre().getType().equals("Personal Development"));

        if(sort.getOrder().equals("low-to-high")){
            mav.addObject("books", Book.lowToHigh(books));
            log.info("Sort low to high on");
        }
        else if(sort.getOrder().equals("high-to-low")){
            mav.addObject("books", Book.highToLow(books));
            log.info("Sort high to low on");
        }
        mav.setViewName("personalDevelopmentBooks");
        return mav;
    }

    /**
     * Metoda responsabila cu afisarea tuturor cartilor din categoria Others
     * @param sort - criteriul de sortare utilizat
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/otherBooks")
    public ModelAndView sortOtherBooks(Sort sort){
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
        books.removeIf(p -> p.getDeleted().equals("yes"));
        books.removeIf( p -> p.getGenre().getType().equals("Romance"));
        books.removeIf( p -> p.getGenre().getType().equals("Science Fiction"));
        books.removeIf( p -> p.getGenre().getType().equals("History"));
        books.removeIf( p -> p.getGenre().getType().equals("Travel"));
        books.removeIf( p -> p.getGenre().getType().equals("Action"));
        books.removeIf( p -> p.getGenre().getType().equals("Personal Development"));

        if(sort.getOrder().equals("low-to-high")){
            mav.addObject("books", Book.lowToHigh(books));
            log.info("Sort low to high on");
        }
        else if(sort.getOrder().equals("high-to-low")){
            mav.addObject("books", Book.highToLow(books));
            log.info("Sort high to low on");
        }
        mav.setViewName("otherBooks");
        return mav;
    }
}
