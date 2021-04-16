package ro.sd.a2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.Administrator;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.messages.ErrorMessages;
import ro.sd.a2.service.repository.AdminRepository;
import ro.sd.a2.validators.AdminValidators;

@Service
public class AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private AdminRepository adminRepository;


    public Administrator loginAdmin(String username, String password) {
        AdminValidators.validateAdminByUsernameAndPassword(username, password);
        Administrator administrator = adminRepository.findByUsernameAndPassword(username, password);

        if(administrator == null){
            throw new InvalidParameterException(ErrorMessages.INVALID_LOGIN_ADMIN);
        }

        log.info("Successfully login " + administrator.getUsername());

        return administrator;
    }
}
