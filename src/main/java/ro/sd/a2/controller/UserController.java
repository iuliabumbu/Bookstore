package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.config.RabbitSender;
import ro.sd.a2.dto.*;
import ro.sd.a2.entity.Address;
import ro.sd.a2.entity.Book;
import ro.sd.a2.entity.Order;
import ro.sd.a2.entity.User;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.mappers.Mapper;
import ro.sd.a2.messages.ErrorMessages;
import ro.sd.a2.service.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"currUser", "cart"})
public class UserController {
    @Autowired
    private RabbitSender rabbitMQSender;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ResidenceService residenceService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShipperService shipperService;


    @GetMapping("/indexUser")
    public ModelAndView mainMenuUser(){
        log.info("Called /indexUser page");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("indexUser");
        return mav;
    }

    @GetMapping("/viewAccount")
    public ModelAndView viewUserAccount(@ModelAttribute("currUser") String currentUser){
        log.info("Called /viewAccount page");
        ModelAndView mav = new ModelAndView();
        Optional<User> user = userService.findUserById(currentUser);

        if( user.isPresent()) {
            UserDto userDto = Mapper.userMapping(user.get());
            mav.addObject("userDto", userDto);
        }
        else{
            throw new InvalidParameterException(ErrorMessages.INVALID_FIND);
        }

        mav.setViewName("editAccount");
        return mav;
    }

    @GetMapping("/editUser")
    public ModelAndView updateShipper(UserDto userDto){
        log.info("Called /updateShipper page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("userDto", userDto);
        mav.setViewName("editUser");
        return mav;
    }


    @GetMapping("/errorUser")
    public ModelAndView errorUser(String error){
        log.info("Called /errorUser page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", error);
        mav.setViewName("errorUser");
        return mav;
    }

    @GetMapping("/successUser")
    public ModelAndView successUser(String message){
        log.info("Called /successUser page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", message);
        mav.setViewName("successUser");
        return mav;
    }

    @GetMapping("/addAddress")
    public ModelAndView addAddress(AddressDto addressDto){
        log.info("Called /addAddress page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("addressDto", addressDto);
        mav.setViewName("addAddress");
        return mav;
    }

    @GetMapping("/deleteAddress")
    public ModelAndView deleteAddress(AddressDto addressDto){
        log.info("Called /deleteAddress page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("addressDto", addressDto);
        mav.setViewName("deleteAddress");
        return mav;
    }

    @GetMapping("/viewUserOrder")
    public ModelAndView viewUserOrder(@ModelAttribute("currUser") String currentUser){
        log.info("Called /viewUserOrder page");
        ModelAndView mav = new ModelAndView();

        Optional<User> user = userService.findUserById(currentUser);
        List<Order> orders = orderService.findAllByUser(user.get());
        orders.removeIf(p -> p.getDeleted().equals("yes"));
        mav.addObject("orders", orders);
        mav.setViewName("viewUserOrder");
        return mav;
    }

    @GetMapping("/viewOrderItems")
    public ModelAndView viewOrderItems(String id){
        log.info("Called /viewOrderItems page");
        ModelAndView mav = new ModelAndView();

        System.out.println("Am primit id "+ id);

        Optional<Order> order = orderService.findOrderById(id);

        mav.addObject("books", order.get().getItems());
        mav.setViewName("viewOrderItems");
        return mav;
    }

    @GetMapping("/toShoppingCart")
    public ModelAndView shoppingCart(Book book){
        log.info("Called /toShoppingCart page");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("toShoppingCart");
        return mav;
    }

    @GetMapping("/shoppingCart")
    public ModelAndView shoppingCart(@ModelAttribute("cart") List<Book> cart){
        log.info("Called /shoppingCart page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("cart", cart);
        mav.setViewName("shoppingCart");
        return mav;
    }

    @GetMapping("/deleteFromCart")
    public ModelAndView deleteFromCart(BookDto bookDto){
        log.info("Called /shoppingCart page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("bookDto", bookDto);
        mav.setViewName("deleteFromCart");
        return mav;
    }

    @GetMapping("/deleteOrder")
    public ModelAndView deleteOrder(OrderDto orderDto){
        log.info("Called /deleteOrder page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("order", orderDto);
        mav.setViewName("deleteOrder");
        return mav;
    }

    @GetMapping("/processOrder")
    public ModelAndView processOrder(OrderDto orderDto, @ModelAttribute("currUser") String currentUser, @ModelAttribute("cart") List<Book> cart){
        log.info("Called /processOrder page");
        ModelAndView mav = new ModelAndView();
        Optional<User> user = userService.findUserById(currentUser);
        float total = 0;

        for(Book book : cart){
            if(book.getPromotionPrice() > 0){
                total += book.getPromotionPrice();
            }
            else{
                total += book.getPrice();
            }
        }
        if(residenceService.findAllByOwner(user.get()) == null){
            mav.addObject("error", "No adresses found, can not process the order!");
            mav.setViewName("errorUser");
            return mav;
        }

        mav.addObject("orderDto", orderDto);
        mav.addObject("shippers", shipperService.findAllShippers());
        mav.addObject("addresses", residenceService.findAllByOwner(user.get()).getAddresses());
        mav.addObject("total", total);
        mav.setViewName("processOrder");
        return mav;
    }

    @PostMapping("/processOrder")
    public ModelAndView addOrder(OrderDto orderDto, @ModelAttribute("currUser") String currentUser, @ModelAttribute("cart") List<Book> cart){
        log.info("Called /processOrder page");
        ModelAndView mav = new ModelAndView();

        Optional<User> user = userService.findUserById(currentUser);

        orderDto.setOwner(user.get());
        orderDto.setItems(cart);


        float total = 0;

        for(Book book : cart){
            if(book.getPromotionPrice() > 0){
                total += book.getPromotionPrice();
            }
            else{
                total += book.getPrice();
            }
        }

        total += orderDto.getShipper().getCost();

        orderDto.setTotalCost(total);

        try{

            orderService.saveOrder(orderDto, user.get());

            mav.addObject("message", "New order was added successfully!");
            mav.setViewName("successUser");

            cart.clear();

            mav.addObject("cart", cart);

            log.info("New add order "+orderDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorUser");

            log.warn("Error occured during add order "+ orderDto.toString());
        }

        return mav;
    }

    @PostMapping("/toShoppingCart")
    public ModelAndView shoppingCart(Book book, @ModelAttribute("cart") List<Book> cart){
        log.info("Called /shoppingCart page");
        ModelAndView mav = new ModelAndView();
        cart.add(book);
        mav.setViewName("showBooks");
        mav.addObject("cart", cart);
        return mav;
    }

    @PostMapping("/deleteFromCart")
    public ModelAndView deleteBookFromCart(BookDto bookDto, @ModelAttribute("cart") List<Book> cart){
        log.info("Called /deleteFromCart page");
        ModelAndView mav = new ModelAndView();
        cart.removeIf(p -> p.getId().equals(bookDto.getId()));
        mav.addObject("cart", cart);
        mav.setViewName("shoppingCart");
        return mav;
    }


    @PostMapping("/viewAccount")
    public ModelAndView processUpdateUser(UserDto userDto){
        ModelAndView mav = new ModelAndView();
        try{
            userService.updateUser(userDto);

            rabbitMQSender.send(userDto);

            mav.addObject("message", "Your account has been updated successfully!");
            mav.setViewName("successUser");

            log.info("New edit account "+ userDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorUser");

            log.warn("Error occured during edit account "+ userDto.toString());
        }

        return mav;
    }

    @PostMapping("/addAddress")
    public ModelAndView processAddAddress(AddressDto addressDto, @ModelAttribute("currUser") String currentUser){
        ModelAndView mav = new ModelAndView();

        try{
            Optional<User> user = userService.findUserById(currentUser);

            if( user.isPresent()) {
                UserDto userDto = Mapper.userMapping(user.get());
                mav.addObject("userDto", userDto);
            }
            else{
                throw new InvalidParameterException(ErrorMessages.INVALID_FIND);
            }

            residenceService.saveResidence(addressDto, user.get());

            mav.addObject("message", "New address was added successfully!");
            mav.setViewName("successUser");

            log.info("New add address "+addressDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorUser");

            log.warn("Error occured during add address "+ addressDto.toString());
        }

        return mav;
    }

    @GetMapping("/viewAddress")
    public ModelAndView viewAddress(@ModelAttribute("currUser") String currentUser){
        log.info("Called /viewAddress page");
        ModelAndView mav = new ModelAndView();

        Optional<User> user = userService.findUserById(currentUser);

        if( user.isPresent()) {
            UserDto userDto = Mapper.userMapping(user.get());
        }
        else{
            throw new InvalidParameterException(ErrorMessages.INVALID_FIND);
        }

        if(residenceService.findAllByOwner(user.get()) == null){
            mav.addObject("error", "No residence found, first add an address!");
            mav.setViewName("errorUser");
            return mav;
        }

        List<Address> addresses = residenceService.findAllByOwner(user.get()).getAddresses();
        if (CollectionUtils.isEmpty(addresses)) {
            log.warn("No addresses were found!");
        } else {
            log.info("Successfully found all addresses!");
        }
        addresses.removeIf(p ->  p.getDeleted().equals("yes"));
        mav.addObject("addresses", addresses);
        mav.setViewName("viewAddress");
        return mav;
    }

    @PostMapping("/deleteAddress")
    public ModelAndView processDeleteAddress(AddressDto addressDto, @ModelAttribute("currUser") String currentUser){
        ModelAndView mav = new ModelAndView();
        try{

            Optional<User> user = userService.findUserById(currentUser);

            if( user.isPresent()) {
                UserDto userDto = Mapper.userMapping(user.get());
            }
            else{
                throw new InvalidParameterException(ErrorMessages.INVALID_FIND);
            }

           residenceService.deleteAddress(addressDto, user.get());

            mav.addObject("message", "Address deleted successfully!");
            mav.setViewName("successUser");

            log.info("New delete address"+ addressDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorUser");

            log.warn("Error occured during delete address "+ addressDto.toString());
        }

        return mav;
    }

    @PostMapping("/deleteOrder")
    public ModelAndView processDeleteOrder(OrderDto orderDto){
        ModelAndView mav = new ModelAndView();
        try{
            if(!orderDto.getStatus().equals("In progress")){
                throw new InvalidParameterException(ErrorMessages.INVALID_DELETE_ORDER);
            }

            orderService.deleteOrder(orderDto);
            mav.addObject("message", "Order deleted successfully!");
            mav.setViewName("successUser");

            log.info("New delete order "+ orderDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorUser");

            log.warn("Error occured during delete order "+ orderDto.toString());
        }

        return mav;
    }
}
