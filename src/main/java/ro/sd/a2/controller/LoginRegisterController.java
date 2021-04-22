package ro.sd.a2.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.LoginDto;
import ro.sd.a2.dto.UserDto;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.messages.ErrorMessages;
import ro.sd.a2.service.UserService;
import ro.sd.a2.validators.UserValidators;


@Controller
@RequiredArgsConstructor
@Data
public class LoginRegisterController {

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

    @GetMapping("/errorRegister")
    public ModelAndView errorRegister(String error){
        log.info("Called /errorRegister page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", error);
        mav.setViewName("errorRegister");
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
            userService.saveUser(userDto);

            mav.setViewName("index");

            log.info("New registration "+ userDto.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorRegister");
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
            mav.addObject("currentUser", currentUser);

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
