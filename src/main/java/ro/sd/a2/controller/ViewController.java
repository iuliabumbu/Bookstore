package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.service.UserService;

@RestController
@RequiredArgsConstructor
public class ViewController {

    private static final Logger log = LoggerFactory.getLogger(ViewController.class);

    @GetMapping("/indexView")
    public ModelAndView mainMenuUser(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("indexView");
        return mav;
    }
}
