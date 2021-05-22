package ro.sd.a2.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.config.RabbitSender;
import ro.sd.a2.dto.LoginDto;
import ro.sd.a2.dto.MessageDto;
import ro.sd.a2.dto.UserDto;
import ro.sd.a2.entity.Book;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.messages.ErrorMessages;
import ro.sd.a2.service.UserService;
import ro.sd.a2.validators.UserValidators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Data
@SessionAttributes({"currUser", "cart"})
public class LoginRegisterController {
    private final String authorizationToken = "1bf38fac-897e-40bd-8f3c-38bf247544f93196e235-ec58-43b1-b5b3-e9637822a1b5";

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(LoginRegisterController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public ModelAndView mainMenu(){
        log.info("Called /index page");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/register")
    public ModelAndView register(UserDto userDto){
        log.info("Called /register page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("userDto", userDto);
        mav.setViewName("register");
        return mav;
    }

    @GetMapping("/login")
    public ModelAndView login(LoginDto loginDto){
        log.info("Called /login page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("loginDto", loginDto);
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("/errorLogin")
    public ModelAndView errorLogin(String error){
        log.info("Called /errorLogin page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", error);
        mav.setViewName("errorLogin");
        return mav;
    }

    @GetMapping("/successRegister")
    public ModelAndView successRegister(String message){
        log.info("Called /successRegister page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", message);
        mav.setViewName("successRegister");
        return mav;
    }

    @GetMapping("/errorRegister")
    public ModelAndView errorRegister(String error){
        log.info("Called /errorRegister page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", error);
        mav.setViewName("errorRegister");
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logoutUser(@ModelAttribute("currUser") String currentUser, @ModelAttribute("cart") List<Book> cart){
        log.info("Called /logout page");
        System.out.println("Am ajuns in logout");
        ModelAndView mav = new ModelAndView();
        currentUser = "guest";
        mav.addObject("currUser", currentUser);
        cart.clear();
        mav.addObject("cart", cart);
        mav.setViewName("index");

        return mav;
    }


    /**
     * Metoda responsabila cu inregistrarea unui nou utilizator
     * @param userDto - obiectul contine datele personale ale noului utilizator
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/register")
    public ModelAndView processRegister(UserDto userDto){
        ModelAndView mav = new ModelAndView();

        try{
            UserDto user = userService.findByEmail(userDto.getEmail());
            if(user != null){
                throw new InvalidParameterException(ErrorMessages.INVALID_REGISTER_EMAIL);
            }

            userService.saveUser(userDto);

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("token", authorizationToken);
            // build the request
            HttpEntity<UserDto> request = new HttpEntity<>(userDto, headers);
            // send POST request
            ResponseEntity<MessageDto> res = restTemplate.exchange("http://localhost:7700/create",
                    HttpMethod.POST, request, MessageDto.class);

            mav.addObject("message",  res.getBody().getMessage());
            mav.setViewName("successRegister");

            log.info("Registration Email: "+ res.toString());

            log.info("New registration "+ userDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorRegister");
            System.out.println(e.getMessage());
            log.warn("Error occured during  register "+ userDto.toString());
        }

        return mav;
    }

    /**
     * Metoda responsabila cu logarea unui utilizator
     * @param loginDto - obiectul contine credentialele utilizatorui
     * @throws InvalidParameterException - daca credentialele nu sunt corecte
     * @return - obiectul ModelAndView care ne permite sa trimitem informatiile solicitate de Spring MVC
     */
    @PostMapping("/login")
    public ModelAndView processLogin(LoginDto loginDto){
        ModelAndView mav = new ModelAndView();

        try{
            UserValidators.validateFindUserByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());

            LoginDto currentUser = userService.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
            if(currentUser == null){
                throw new InvalidParameterException(ErrorMessages.INVALID_LOGIN_USER);
            }
            mav.addObject("currUser", currentUser.getId());
            List<Book> books = new ArrayList<Book>();
            mav.addObject("cart", books);

            mav.setViewName("indexUser");

            log.info("New login "+ currentUser.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorLogin");

            log.warn("Error occured during login "+ loginDto.toString());
        }

        return mav;
    }


}
