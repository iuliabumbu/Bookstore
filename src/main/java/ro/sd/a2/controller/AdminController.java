package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.BookDto;
import ro.sd.a2.dto.GenreDto;
import ro.sd.a2.dto.ShipperDto;
import ro.sd.a2.dto.UserDto;
import ro.sd.a2.entity.*;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.messages.ErrorMessages;
import ro.sd.a2.service.*;
import ro.sd.a2.utils.Context;
import ro.sd.a2.utils.GeneratePDF;
import ro.sd.a2.utils.GenerateTXT;
import ro.sd.a2.validators.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private AdminService adminService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private ShipperService shipperService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;


    @GetMapping("/adminLogin")
    public ModelAndView adminLogin(Administrator administrator){
        log.info("Called /adminLogin page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("administrator", administrator);
        mav.setViewName("adminLogin");
        return mav;
    }

    @GetMapping("/indexAdmin")
    public ModelAndView mainMenuAdmin(){
        log.info("Called /indexAdmin page");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("indexAdmin");
        return mav;
    }

    @GetMapping("/errorAdminLogin")
    public ModelAndView errorAdminLogin(String error){
        log.info("Called /errorAdminLogin page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", error);
        mav.setViewName("errorAdminLogin");
        return mav;
    }

    @GetMapping("/errorAdmin")
    public ModelAndView errorAdmin(String error){
        log.info("Called /errorAdmin page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", error);
        mav.setViewName("errorAdmin");
        return mav;
    }

    @GetMapping("/successAdmin")
    public ModelAndView successAdmin(String message){
        log.info("Called /successAdmin page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", message);
        mav.setViewName("successAdmin");
        return mav;
    }

    @GetMapping("/addGenre")
    public ModelAndView addGenre(GenreDto genreDto){
        log.info("Called /addGenre page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("genreDto", genreDto);
        mav.setViewName("addGenre");
        return mav;
    }

    @GetMapping("/viewGenre")
    public ModelAndView viewGenre(){
        log.info("Called /viewGenre page");
        ModelAndView mav = new ModelAndView();
        List<Genre> genres = genreService.findAllGenres();
        if (CollectionUtils.isEmpty(genres)) {
            log.warn("No genres were found!");
        } else {
            log.info("Successfully found all genres!");
        }
        genres.removeIf(p ->  p.getDeleted().equals("yes"));
        mav.addObject("genres", genres);
        mav.setViewName("viewGenre");
        return mav;
    }

    @GetMapping("/addShipper")
    public ModelAndView addShipper(ShipperDto shipperDto){
        log.info("Called /addShipper page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("shipperDto", shipperDto);
        mav.setViewName("addShipper");
        return mav;
    }

    @GetMapping("/viewShipper")
    public ModelAndView viewShipper(){
        log.info("Called /viewShipper page");
        ModelAndView mav = new ModelAndView();
        List<Shipper> shippers = shipperService.findAllShippers();
        if (CollectionUtils.isEmpty(shippers)) {
            log.warn("No shippers were found!");
        } else {
            log.info("Successfully found all shippers!");
        }
        shippers.removeIf(p -> p.getDeleted().equals("yes"));
        mav.addObject("shippers", shippers);
        mav.setViewName("viewShipper");
        return mav;
    }

    @GetMapping("/addBook")
    public ModelAndView addBook(BookDto bookDto){
        log.info("Called /addBook page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("bookDto", bookDto);
        mav.addObject("genres", genreService.findAllGenres());
        mav.setViewName("addBook");
        return mav;
    }

    @GetMapping("/viewBook")
    public ModelAndView viewBook(){
        log.info("Called /viewBook page");
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        if (CollectionUtils.isEmpty(books)) {
            log.warn("No books were found!");
        } else {
            log.info("Successfully found all books!");
        }
        books.removeIf(p -> p.getDeleted().equals("yes"));
        mav.addObject("books", books);
        mav.setViewName("viewBook");
        return mav;
    }

    @GetMapping("/addUser")
    public ModelAndView addUser(UserDto userDto){
        log.info("Called /addUser page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("userDto", userDto);
        mav.setViewName("addUser");
        return mav;
    }

    @GetMapping("/viewUser")
    public ModelAndView viewUser(){
        log.info("Called /viewUser page");
        ModelAndView mav = new ModelAndView();
        List<User> users = userService.findAllUsers();
        if (CollectionUtils.isEmpty(users)) {
            log.warn("No users were found!");
        } else {
            log.info("Successfully found all users!");
        }
        users.removeIf(p -> p.getDeleted().equals("yes"));
        mav.addObject("users", users);
        mav.setViewName("viewUser");
        return mav;
    }

    @GetMapping("/addPromotions")
    public ModelAndView addPromotions(Promotion promotion){
        log.info("Called /addPromotions page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("promotion", promotion);
        mav.addObject("books", bookService.findAllBooks());
        mav.setViewName("addPromotions");
        return mav;
    }

    @GetMapping("/viewOrder")
    public ModelAndView viewOrder(){
        log.info("Called /viewOrder page");
        ModelAndView mav = new ModelAndView();
       // mav.addObject("orders", userService.findAllUsers());
        mav.setViewName("viewOrder");
        return mav;
    }

    @GetMapping("/toUpdateGenre")
    public ModelAndView toUpdateGenre(GenreDto genreDto){
        log.info("Called /toUpdateGenre page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("genreDto", genreDto);
        mav.setViewName("toUpdateGenre");
        return mav;
    }

    @GetMapping("/updateGenre")
    public ModelAndView updateGenre(GenreDto genreDto){
        log.info("Called /updateGenre page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("genreDto", genreDto);
        mav.setViewName("updateGenre");
        return mav;
    }

    @GetMapping("/deleteGenre")
    public ModelAndView deleteGenre(GenreDto genreDto){
        log.info("Called /deleteGenre page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("genre", genreDto);
        mav.setViewName("deleteGenre");
        return mav;
    }

    @GetMapping("/toUpdateShipper")
    public ModelAndView toUpdateShipper(ShipperDto shipperDto){
        log.info("Called /toUpdateShipper page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("shipperDto", shipperDto);
        mav.setViewName("toUpdateShipper");
        return mav;
    }

    @GetMapping("/updateShipper")
    public ModelAndView updateShipper(ShipperDto shipperDto){
        log.info("Called /updateShipper page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("shipperDto", shipperDto);
        mav.setViewName("updateShipper");
        return mav;
    }

    @GetMapping("/deleteShipper")
    public ModelAndView deleteShipper(ShipperDto shipperDto){
        log.info("Called /deleteShipper page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("shipperDto", shipperDto);
        mav.setViewName("deleteShipper");
        return mav;
    }

    @GetMapping("/toUpdateUser")
    public ModelAndView toUpdateUser(UserDto userDto){
        log.info("Called /toUpdateUser page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("userDto", userDto);
        mav.setViewName("toUpdateUser");
        return mav;
    }

    @GetMapping("/updateUser")
    public ModelAndView updateUser(UserDto userDto){
        log.info("Called /updateUser page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("userDto", userDto);
        mav.setViewName("updateUser");
        return mav;
    }

    @GetMapping("/deleteUser")
    public ModelAndView deleteUser(UserDto userDto){
        log.info("Called /deleteUser page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("userDto", userDto);
        mav.setViewName("deleteUser");
        return mav;
    }

    @GetMapping("/toUpdateBook")
    public ModelAndView toUpdateBook(BookDto bookDto){
        log.info("Called /toUpdateBook page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("bookDto", bookDto);
        mav.setViewName("toUpdateBook");
        return mav;
    }

    @GetMapping("/updateBook")
    public ModelAndView updateBook(Book bookDto){
        log.info("Called /updateBook page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("bookDto", bookDto);
        mav.setViewName("updateBook");
        return mav;
    }

    @GetMapping("/deleteBook")
    public ModelAndView deleteBook(BookDto bookDto){
        log.info("Called /deleteBook page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("bookDto", bookDto);
        mav.setViewName("deleteBook");
        return mav;
    }

    @GetMapping("/generateInventory")
    public ModelAndView generateInventory(Document document){
        log.info("Called /generateInventory page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("document", document);
        mav.setViewName("generateInventory");
        return mav;
    }

    /**
     * Metoda responsabila cu logarea administratorului
     * @param administrator - obiectul contine credentialele administratorului
     * @throws InvalidParameterException - daca credentialele nu sunt corecte
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/adminLogin")
    public ModelAndView processAdminLogin(Administrator administrator){
        ModelAndView mav = new ModelAndView();

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

            log.warn("Error occured during admin login "+ administrator.toString());
        }

        return mav;
    }

    /**
     * Metoda responsabila cu adaugarea unei noi categorii
     * @param genreDto - categoria pe care dorim sa o introducem
     * @throws InvalidParameterException - daca categoria pe care dorim sa o introducem exista deja
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/addGenre")
    public ModelAndView processAddGenre(GenreDto genreDto){
        ModelAndView mav = new ModelAndView();

        try{

            List<String> types = genreService.findAllGenres().stream().map(Genre::getType).collect(Collectors.toList());

            if(types.contains(genreDto.getType())){
                throw new InvalidParameterException(ErrorMessages.INVALID_GENRE);
            }

            genreService.saveGenre(genreDto);

            mav.addObject("message", "New genre was added successfully!");
            mav.setViewName("successAdmin");

            log.info("New add genre "+ genreDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.warn("Error occured during add genre "+ genreDto.toString());
        }

        return mav;
    }

    /**
     * Metoda responsabila cu adaugarea unui nou curier
     * @param shipperDto - curierul pe care dorim sa il introducem
     * @throws InvalidParameterException - daca curierul pe care dorim sa il introducem exista deja
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/addShipper")
    public ModelAndView processAddShipper(ShipperDto shipperDto){
        ModelAndView mav = new ModelAndView();

        try{

            List<String> names = shipperService.findAllShippers().stream().map(Shipper::getName).collect(Collectors.toList());

            if(names.contains(shipperDto.getName())){
                throw new InvalidParameterException(ErrorMessages.INVALID_SHIPPER);
            }

            shipperService.saveShipper(shipperDto);

            mav.addObject("message", "New shipper was added successfully!");
            mav.setViewName("successAdmin");

            log.info("New add shipper "+ shipperDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.warn("Error occured during add shipper "+ shipperDto.toString());
        }

        return mav;
    }

    /**
     * Metoda responsabila cu adaugarea unei noi carti
     * @param bookDto - cartea pe care dorim sa o introducem
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/addBook")
    public ModelAndView processAddBook(BookDto bookDto){
        ModelAndView mav = new ModelAndView();

        try{
            bookService.saveBook(bookDto);

            mav.addObject("message", "New book was added successfully!");
            mav.setViewName("successAdmin");

            log.info("New add book "+ bookDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.warn("Error occured during add book "+ bookDto.toString());
        }

        return mav;
    }

    /**
     * Metoda responsabila cu adaugarea unui nou utilizator
     * @param userDto - utilizatorul pe care dorim sa il introducem
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/addUser")
    public ModelAndView processAddUser(UserDto userDto){
        ModelAndView mav = new ModelAndView();

        try{

            userService.saveUser(userDto);

            mav.addObject("message", "New user was added successfully!");
            mav.setViewName("successAdmin");

            log.info("New add user "+ userDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.warn("Error occured during add user "+ userDto.toString());
        }

        return mav;
    }

    /**
     * Metoda responsabila cu adaugarea unei noi promotii
     * @param promotion - promotia pe care dorim sa o introducem
     * @throws InvalidParameterException -daca procentul nu este intre 0-100 (%)
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/addPromotions")
    public ModelAndView processAddPromotions(Promotion promotion){
        ModelAndView mav = new ModelAndView();
        try{
            if(promotion.getDiscount() > 100 || promotion.getDiscount() < 0){
                throw new InvalidParameterException(ErrorMessages.INVALID_DISCOUNT);
            }

            bookService.addPromotion(promotion.getDiscount(), promotion.getBook());

            mav.addObject("message", "Promotion was added successfully!");
            mav.setViewName("successAdmin");

            log.info("New promotion discount:"+ promotion.getDiscount());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.warn("Error occured during add promotion ");
        }
        return mav;
    }
    /**
     * Metoda responsabila cu redirectionarea categoriei pe care dorim sa o editam
     * @param genreDto - categoria pe care dorim sa o editam
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/toUpdateGenre")
    public ModelAndView processToUpdateGenre(GenreDto genreDto){
        ModelAndView mav = new ModelAndView();
        mav.addObject("genreDto", genreDto);
        mav.setViewName("updateGenre");
        return mav;
    }

    /**
     * Metoda responsabila cu editarea unei categorii
     * @param genreDto - noua categorie
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/updateGenre")
    public ModelAndView processUpdateGenre(GenreDto genreDto){
        ModelAndView mav = new ModelAndView();
        try{
            genreService.updateGenre(genreDto);
            mav.addObject("message", "Genre updated successfully!");
            mav.setViewName("successAdmin");

            log.info("New update genre "+ genreDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.warn("Error occured during update genre "+ genreDto.toString());
        }

        return mav;
    }

    /**
     * Metoda responsabila cu stergerea unei categorii
     * @param genreDto - categoria pe care dorim sa o stergem
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/deleteGenre")
    public ModelAndView processDeleteGenre(GenreDto genreDto){
        ModelAndView mav = new ModelAndView();
        try{
            List<String> mainTypes = Collections.unmodifiableList(Arrays.asList("Science Fiction", "Romance",
                                    "History", "Travel", "Personal Development", "Action"));
            if(mainTypes.contains(genreDto.getType())){
                throw new InvalidParameterException(ErrorMessages.INVALID_DELETE_GENRE);
            }
            genreService.deleteGenre(genreDto);
            mav.addObject("message", "Genre deleted successfully!");
            mav.setViewName("successAdmin");

            log.info("New delete genre "+ genreDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.warn("Error occured during delete genre "+ genreDto.toString());
        }

        return mav;
    }
    /**
     * Metoda responsabila cu redirectionarea curierului pe care dorim sa il editam
     * @param shipperDto - curierul pe care dorim sa il editam
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/toUpdateShipper")
    public ModelAndView processToUpdateGenre(ShipperDto shipperDto){
        ModelAndView mav = new ModelAndView();
        mav.addObject("shipperDto", shipperDto);
        mav.setViewName("updateShipper");
        return mav;
    }

    /**
     * Metoda responsabila cu editarea unui curier
     * @param shipperDto - noul curier
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/updateShipper")
    public ModelAndView processUpdateShipper(ShipperDto shipperDto){
        ModelAndView mav = new ModelAndView();
        try{
            shipperService.updateShipper(shipperDto);
            mav.addObject("message", "Shipper updated successfully!");
            mav.setViewName("successAdmin");

            log.info("New update shipper "+ shipperDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.warn("Error occured during update shipper "+ shipperDto.toString());
        }

        return mav;
    }
    /**
     * Metoda responsabila cu stergerea unui curier
     * @param shipperDto - curierul pe care dorim sa il stergem
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/deleteShipper")
    public ModelAndView processDeleteShipper(ShipperDto shipperDto){
        ModelAndView mav = new ModelAndView();
        try{

            shipperService.deleteShipper(shipperDto);
            mav.addObject("message", "Shipper deleted successfully!");
            mav.setViewName("successAdmin");

            log.info("New delete shipper "+ shipperDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.warn("Error occured during delete shipper "+ shipperDto.toString());
        }

        return mav;
    }
    /**
     * Metoda responsabila cu redirectionarea utilizatorului pe care dorim sa il editam
     * @param userDto - utilizatorul pe care dorim sa il editam
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/toUpdateUser")
    public ModelAndView processToUpdateUser(UserDto userDto){
        ModelAndView mav = new ModelAndView();
        mav.addObject("userDto", userDto);
        mav.setViewName("updateUser");
        return mav;
    }

    /**
     * Metoda responsabila cu editarea unui utilizator
     * @param userDto - noul utilizator
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/updateUser")
    public ModelAndView processUpdateUser(UserDto userDto){
        ModelAndView mav = new ModelAndView();
        try{
            userService.updateUser(userDto);

            mav.addObject("message", "User updated successfully!");
            mav.setViewName("successAdmin");

            log.info("New update user "+ userDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.warn("Error occured during update user "+ userDto.toString());
        }

        return mav;
    }
    /**
     * Metoda responsabila cu stergerea unui utilizator
     * @param userDto - utilizatorul pe care dorim sa il stergem
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/deleteUser")
    public ModelAndView processDeleteUser(UserDto userDto){
        ModelAndView mav = new ModelAndView();
        try{

            userService.deleteUser(userDto);

            mav.addObject("message", "User deleted successfully!");
            mav.setViewName("successAdmin");

            log.info("New delete user "+ userDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.warn("Error occured during delete user "+ userDto.toString());
        }

        return mav;
    }
    /**
     * Metoda responsabila cu redirectionarea cartii pe care dorim sa o editam
     * @param bookDto - cartea pe care dorim sa o editam
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/toUpdateBook")
    public ModelAndView processToUpdateBook(BookDto bookDto){
        ModelAndView mav = new ModelAndView();
        mav.addObject("bookDto", bookDto);
        mav.setViewName("updateBook");
        return mav;
    }
    /**
     * Metoda responsabila cu editarea unei carti
     * @param bookDto - noua carte
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/updateBook")
    public ModelAndView processUpdateBook(BookDto bookDto){
        ModelAndView mav = new ModelAndView();
        try{
            bookService.updateBook(bookDto);
            mav.addObject("message", "Book updated successfully!");
            mav.setViewName("successAdmin");

            log.info("New update book "+ bookDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.warn("Error occured during update book "+ bookDto.toString());
        }

        return mav;
    }

    /**
     * Metoda responsabila cu stergerea unei carti
     * @param bookDto - cartea pe care dorim sa o stergem
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/deleteBook")
    public ModelAndView processDeleteBook(BookDto bookDto){
        ModelAndView mav = new ModelAndView();
        try{
            bookService.deleteBook(bookDto);
            mav.addObject("message", "Book deleted successfully!");
            mav.setViewName("successAdmin");

            log.info("New delete book "+ bookDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.warn("Error occured during delete book "+ bookDto.toString());
        }

        return mav;
    }
    /**
     * Metoda responsabila cu stergerea tuturor promotiilor
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/deletePromotion")
    public ModelAndView processDeletePromotions(){
        ModelAndView mav = new ModelAndView();
        try{
            bookService.deletePromotions();
            mav.addObject("message", "Promotion deleted successfully!");
            mav.setViewName("successAdmin");

            log.info("All promotions deleted ");
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.warn("Error occured during delete promotions ");
        }

        return mav;
    }

    @PostMapping("/generateInventory")
    public ModelAndView processGenerateInventory(Document document){
        ModelAndView mav = new ModelAndView();
        try{

            System.out.println("Am gasit "+ document.getType());
            List<Book> books = bookService.findAllBooks();

            books.removeIf( p -> p.getDeleted().equals("yes"));

            Context context;

            if(document.getType().equals("txt")){
                context = new Context(new GenerateTXT());
                context.executeStrategy(books);
            }
            else if(document.getType().equals("pdf")){
                context = new Context(new GeneratePDF());
                context.executeStrategy(books);
            }


            mav.addObject("message", "Inventory generated successfully!");
            mav.setViewName("successAdmin");

            log.info("Inventory generated ");
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorAdmin");

            log.warn("Error occured during generate inventory ");
        }

        return mav;
    }





}
