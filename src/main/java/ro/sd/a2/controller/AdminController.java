package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.UserDto;
import ro.sd.a2.entity.Administrator;
import ro.sd.a2.entity.User;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.messages.ErrorMessages;
import ro.sd.a2.service.AdminService;
import ro.sd.a2.validators.AdminValidators;
import ro.sd.a2.validators.UserValidators;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private final AdminService adminService;


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




}
