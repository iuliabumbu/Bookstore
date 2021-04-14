package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.UserDto;
import ro.sd.a2.entity.*;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.factory.BookFactory;
import ro.sd.a2.factory.GenreFactory;
import ro.sd.a2.factory.ShipperFactory;
import ro.sd.a2.factory.UserFactory;
import ro.sd.a2.messages.ErrorMessages;
import ro.sd.a2.service.*;
import ro.sd.a2.validators.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private final AdminService adminService;
    private final GenreService genreService;
    private final ShipperService shipperService;
    private final BookService bookService;
    private final UserService userService;


    @GetMapping("/adminLogin")
    public ModelAndView adminLogin(Administrator administrator){
        ModelAndView mav = new ModelAndView();
        mav.addObject("administrator", administrator);
        mav.setViewName("adminLogin");
        return mav;
    }

    @GetMapping("/indexAdmin")
    public ModelAndView mainMenuAdmin(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("indexAdmin");
        return mav;
    }

    @GetMapping("/errorAdminLogin")
    public ModelAndView errorAdminLogin(String error){
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", error);
        mav.setViewName("errorAdminLogin");
        return mav;
    }

    @GetMapping("/errorAdmin")
    public ModelAndView errorAdmin(String error){
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", error);
        mav.setViewName("errorAdmin");
        return mav;
    }

    @GetMapping("/successAdmin")
    public ModelAndView successAdmin(String message){
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", message);
        mav.setViewName("successAdmin");
        return mav;
    }

    @GetMapping("/addGenre")
    public ModelAndView addGenre(Genre genre){
        ModelAndView mav = new ModelAndView();
        mav.addObject("genre", genre);
        mav.setViewName("addGenre");
        return mav;
    }

    @GetMapping("/viewGenre")
    public ModelAndView viewGenre(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("genres", genreService.findAllGenres());
        mav.setViewName("viewGenre");
        return mav;
    }

    @GetMapping("/addShipper")
    public ModelAndView addShipper(Shipper shipper){
        ModelAndView mav = new ModelAndView();
        mav.addObject("shipper", shipper);
        mav.setViewName("addShipper");
        return mav;
    }

    @GetMapping("/viewShipper")
    public ModelAndView viewShipper(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("shippers", shipperService.findAllShippers());
        mav.setViewName("viewShipper");
        return mav;
    }

    @GetMapping("/addBook")
    public ModelAndView addBook(Book book){
        ModelAndView mav = new ModelAndView();
        mav.addObject("book", book);
        mav.addObject("genres", genreService.findAllGenres());
        mav.setViewName("addBook");
        return mav;
    }

    @GetMapping("/viewBook")
    public ModelAndView viewBook(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("books", bookService.findAllBooks());
        mav.setViewName("viewBook");
        return mav;
    }

    @GetMapping("/addUser")
    public ModelAndView addUser(User user){
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", user);
        mav.setViewName("addUser");
        return mav;
    }

    @GetMapping("/viewUser")
    public ModelAndView viewUser(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userService.findAllUsers());
        mav.setViewName("viewUser");
        return mav;
    }

    @GetMapping("/addPromotions")
    public ModelAndView addPromotions(Book book){
        ModelAndView mav = new ModelAndView();
        mav.addObject("book", book);
        mav.setViewName("addPromotions");
        return mav;
    }

    @GetMapping("/viewOrder")
    public ModelAndView viewOrder(){
        ModelAndView mav = new ModelAndView();
       // mav.addObject("orders", userService.findAllUsers());
        mav.setViewName("viewOrder");
        return mav;
    }

    @PostMapping("/adminLogin")
    public ModelAndView processAdminLogin(Administrator administrator){
        ModelAndView mav = new ModelAndView();

        System.out.println(administrator.toString());

        try{
            AdminValidators.validateAdminByUsernameAndPassword(administrator.getUsername(), administrator.getPassword());
            Administrator currentAdmin = adminService.loginAdmin(administrator.getUsername(), administrator.getPassword());
            if(currentAdmin == null){
                throw new InvalidParameterException(ErrorMessages.INVALID_LOGIN_ADMIN);
            }
            mav.setViewName("indexAdmin");

            log.info("New administrator login "+ administrator.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdminLogin");

            log.info("Error occured during admin login "+ administrator.toString());
        }

        return mav;
    }

    @PostMapping("/addGenre")
    public ModelAndView processAddGenre(Genre genre){
        ModelAndView mav = new ModelAndView();

        try{
            Genre newGenre = GenreFactory.generateGenre();
            newGenre.setType(genre.getType());
            GenreValidators.validateGenreType(newGenre.getType());
            List<String> types = genreService.findAllGenres().stream().map(Genre::getType).collect(Collectors.toList());

            if(types.contains(genre.getType())){
                throw new InvalidParameterException(ErrorMessages.INVALID_GENRE);
            }

            genreService.saveGenre(newGenre);

            mav.addObject("message", "New genre was added successfully!");
            mav.setViewName("successAdmin");

            log.info("New add genre "+ genre.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.info("Error occured during add genre "+ genre.toString());
        }

        return mav;
    }

    @PostMapping("/addShipper")
    public ModelAndView processAddShipper(Shipper shipper){
        ModelAndView mav = new ModelAndView();

        try{
            Shipper newShipper = ShipperFactory.generateShipper();
            newShipper.setName(shipper.getName());
            newShipper.setCost(shipper.getCost());
            ShipperValidators.validateShipper(newShipper.getName(), newShipper.getCost());

            List<String> names = shipperService.findAllShippers().stream().map(Shipper::getName).collect(Collectors.toList());

            if(names.contains(shipper.getName())){
                throw new InvalidParameterException(ErrorMessages.INVALID_SHIPPER);
            }

            shipperService.saveShipper(newShipper);

            mav.addObject("message", "New shipper was added successfully!");
            mav.setViewName("successAdmin");

            log.info("New add shipper "+ shipper.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.info("Error occured during add shipper "+ shipper.toString());
        }

        return mav;
    }

    @PostMapping("/addBook")
    public ModelAndView processAddBook(Book book){
        ModelAndView mav = new ModelAndView();

        try{
            Book newBook = BookFactory.generateBook();
            newBook.setTitle(book.getTitle());
            newBook.setAuthor(book.getAuthor());
            newBook.setDescription(book.getDescription());
            newBook.setImage(book.getImage());
            newBook.setGenre(book.getGenre());
            newBook.setPrice(book.getPrice());
            BookValidators.validateBook(newBook);

            bookService.saveBook(newBook);

            mav.addObject("message", "New book was added successfully!");
            mav.setViewName("successAdmin");

            log.info("New add book "+ book.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.info("Error occured during add book "+ book.toString());
        }

        return mav;
    }

    @PostMapping("/addUser")
    public ModelAndView processAddUser(User user){
        ModelAndView mav = new ModelAndView();

        try{
            User newUser = UserFactory.generateUser();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setName(user.getName());
            newUser.setSurname(user.getSurname());
            newUser.setPhoneNumber(user.getPhoneNumber());

            UserValidators.validateInsertUser(newUser);

            userService.saveUser(newUser);

            mav.addObject("message", "New user was added successfully!");
            mav.setViewName("successAdmin");

            log.info("New add user "+ user.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.info("Error occured during add user "+ user.toString());
        }

        return mav;
    }





}
