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


    /**
     * Metoda responsabila cu logarea administratului
     * @param username - username-ul administratorului
     * @param password - parola administratorului
     * @return - functia returneaza obiectul Administrator gasit conform credentialelor primite ca parametru
     */
    public Administrator loginAdmin(String username, String password) {
        log.info("Admin login attempt");

        AdminValidators.validateAdminByUsernameAndPassword(username, password);
        Administrator administrator = adminRepository.findByUsernameAndPassword(username, password);

        return administrator;
    }
}
