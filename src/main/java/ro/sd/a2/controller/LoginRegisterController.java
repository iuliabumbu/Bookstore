package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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

    private final UserService userService;

    @GetMapping("/profile")
    public ModelAndView showProfile() {
        //validation if needed
        //shall we log a little?
        ModelAndView mav = new ModelAndView();
        User user = new User("Bubu");

        mav.addObject("userObj", user);
        mav.addObject("numeStudent", user.getName());
        // adaugi x obiecte
        mav.setViewName("profile");
        //log the final outcome: Success y?
        return mav;
    }

    @GetMapping("/index")
    public ModelAndView mainMenu(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/register")
    public ModelAndView register(User user){
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", user);
        mav.setViewName("register");
        return mav;
    }

    @GetMapping("/login")
    public ModelAndView login(UserDto userDto){
        ModelAndView mav = new ModelAndView();
        mav.addObject("userDto", userDto);
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
    public ModelAndView processRegister(User user){
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

            mav.setViewName("index");

            log.info("New registration "+newUser.toString());
        }
        catch (Exception e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("errorRegister");
        }

        return mav;
    }

    @PostMapping("/login")
    public ModelAndView processLogin(UserDto userDto){
        ModelAndView mav = new ModelAndView();

        log.info("New login "+ userDto.toString());
        try{
            UserValidators.validateFindUserByEmailAndPassword(userDto.getEmail(), userDto.getPassword());

            UserDto currentUser = userService.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
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
