package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.LoginDto;
import ro.sd.a2.dto.UserDto;
import ro.sd.a2.entity.User;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.factory.UserFactory;
import ro.sd.a2.messages.ErrorMessages;
import ro.sd.a2.service.UserService;
import ro.sd.a2.validators.UserValidators;


@RestController
@RequiredArgsConstructor
public class LoginRegisterController {

    private static final Logger log = LoggerFactory.getLogger(LoginRegisterController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public ModelAndView mainMenu(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/register")
    public ModelAndView register(UserDto userDto){
        ModelAndView mav = new ModelAndView();
        mav.addObject("userDto", userDto);
        mav.setViewName("register");
        return mav;
    }

    @GetMapping("/login")
    public ModelAndView login(LoginDto loginDto){
        ModelAndView mav = new ModelAndView();
        mav.addObject("loginDto", loginDto);
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("/errorLogin")
    public ModelAndView errorLogin(String error){
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", error);
        mav.setViewName("errorLogin");
        return mav;
    }

    @GetMapping("/errorRegister")
    public ModelAndView errorRegister(String error){
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", error);
        mav.setViewName("errorRegister");
        return mav;
    }

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
        }

        return mav;
    }

    @PostMapping("/login")
    public ModelAndView processLogin(LoginDto loginDto){
        ModelAndView mav = new ModelAndView();

        log.info("New login "+ loginDto.toString());
        try{
            UserValidators.validateFindUserByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());

            LoginDto currentUser = userService.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
            if(currentUser == null){
                throw new InvalidParameterException(ErrorMessages.INVALID_LOGIN_USER);
            }

            mav.setViewName("indexUser");
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorLogin");
        }

        return mav;
    }


}
