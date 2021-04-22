package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.LoginDto;
import ro.sd.a2.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/indexUser")
    public ModelAndView mainMenuUser(LoginDto currentUser){
        log.info("Called /indexUser page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("currentUser", currentUser);
        mav.setViewName("indexUser");
        return mav;
    }
}
