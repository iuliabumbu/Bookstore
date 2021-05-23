package ro.sd.a2.utils;


import com.itextpdf.text.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ro.sd.a2.dto.MessageDto;
import ro.sd.a2.dto.SimpleBookDto;
import ro.sd.a2.dto.UserDto;
import ro.sd.a2.entity.Book;
import ro.sd.a2.service.BookService;
import ro.sd.a2.service.OrderService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class ScheduledTasks {

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;
/*

    @Scheduled(fixedRate = 120000)
    public void reportUnusedBooks() {

        List<Book> books = bookService.findAllBooks();
        List<Book> orderBooks = orderService.findAllBoughtBooks();

        List<SimpleBookDto> allBooks = new ArrayList<>();
        List<SimpleBookDto> allOrderBooks = new ArrayList<>();


        for(Book b: books){
            allBooks.add(new SimpleBookDto(b.getTitle(), b.getAuthor()));
        }

        for(Book b: orderBooks){
            allOrderBooks.add(new SimpleBookDto(b.getTitle(), b.getAuthor()));
        }

        allBooks.removeAll(allOrderBooks);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        // build the request
        HttpEntity<List<SimpleBookDto>> request = new HttpEntity<>(allBooks, headers);
        // send POST request
        ResponseEntity<MessageDto> res = restTemplate.exchange("http://localhost:7700/unusedBooks",
                HttpMethod.POST, request, MessageDto.class);

    }

 */
}
