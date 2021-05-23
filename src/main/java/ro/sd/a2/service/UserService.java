package ro.sd.a2.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.LoginDto;
import ro.sd.a2.dto.UserDto;
import ro.sd.a2.entity.User;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.mappers.Mapper;
import ro.sd.a2.messages.ErrorMessages;
import ro.sd.a2.service.repository.UserRepository;
import ro.sd.a2.validators.UserValidators;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService{

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private  UserRepository userRepository;

    /**
     * Metoda responsabila cu inserarea in baza de date a unui nou utilizator
     * @param userDto - utilizatorul pe care dorim sa il introducem
     */
    public void saveUser(UserDto userDto){
        log.info("Insert user attempt");

        User user = Mapper.UserDtoMapping(userDto);
        UserValidators.validateInsertUser(user);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);

        log.info("Successfully created user " + user.toString());
    }

    /**
     * Metoda responsabila cu gasirea tuturor utilizatorilor din baza de date
     * @return - returneaza o lista ce contine toti utilizatorii gasiti
     */
    public List<User> findAllUsers(){
        log.info("Find all users attempt");

        List<User> users = userRepository.findAll();

        return users;
    }

    /**
     * Metoda responsabila cu logarea utilizatorului
     * @param email - email-ul utilizatorului
     * @param password - parola utilizatorului
     * @return - functia returneaza obiectul LoginDto gasit conform credentialelor primite ca parametru
     */
    public LoginDto findByEmailAndPassword(String email, String password){
        log.info("Find user by email and password attempt " + email + " " + password);

        UserValidators.validateFindUserByEmailAndPassword(email, password);
        User user = userRepository.findByEmailAndPasswordAndDeleted(email, password, "no");

        LoginDto loginDto = Mapper.loginMapping(user);

        return loginDto;
    }

    /**
     * Metoda responsabila cu actualizarea datelor unui utilizator
     * @param userDto - utilizatorul pe care dorim sa il actualizam
     * @throws InvalidParameterException - daca utilizatorul pe care vrem sa il actualizam nu exista in baza de date
     */
    public void updateUser(UserDto userDto){
        log.info("Update user attempt");

        Optional<User> user = findUserById(userDto.getId());

        if( user.isPresent()) {
            user.get().setName(userDto.getName());
            user.get().setSurname(userDto.getSurname());
            user.get().setEmail(userDto.getEmail());
            user.get().setUsername(userDto.getUsername());
            user.get().setPhoneNumber(userDto.getPhoneNumber());
            if(!user.get().getPassword().equals(userDto.getPassword())){
                user.get().setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            }


            System.out.println("books1996: ");
            System.out.println(bCryptPasswordEncoder.encode("books1996"));

            UserValidators.validateInsertUser(user.get());
            userRepository.save(user.get());

            log.info("Successfully updated user " + user.toString());
        }
        else{
            throw new InvalidParameterException(ErrorMessages.INVALID_FIND);
        }

    }

    /**
     * Metoda responsabila cu stergerea unui utilizator (stergere marcata prin setarea "yes" a campului deleted, nu stergere fizica)
     * @param userDto - utilizatorul pe care dorim sa il stergem
     * @throws InvalidParameterException - daca utilizatorul pe care vrem sa il stergem nu exista in baza de date
     */
    public void deleteUser(UserDto userDto){
        log.info("Delete user attempt");

        Optional<User> user = findUserById(userDto.getId());

        if( user.isPresent()) {
            user.get().setDeleted("yes");

            UserValidators.validateInsertUser(user.get());
            userRepository.save(user.get());

            log.info("Successfully Deleted user " + user.toString());
        }
        else{
            throw new InvalidParameterException(ErrorMessages.INVALID_FIND);
        }

    }
    /**
     * Metoda responsabila cu gasirea unui utilizator din baza de date
     * @param id - id-ul utilizatorului pe care dorim sa il gasim
     * @return - returneaza utilizatorul gasit
     */
    public Optional<User> findUserById(String id){
        log.info("Find user by id attempt " + id);

        UserValidators.validateFindUserById(id);
        Optional<User> user = userRepository.findById(id);

        return user;
    }

    public UserDto findByEmail(String email){
        log.info("Find user by email attempt " + email);

        UserValidators.validateFindUserByEmail(email);
        User user = userRepository.findByEmailAndDeleted(email, "no");

        UserDto userDto = Mapper.userMapping(user);

        return userDto;
    }

    public UserDto findByUsername(String username){
        log.info("Find user by username attempt " + username);

        UserValidators.validateFindUserByUsername(username);
        User user = userRepository.findByUsername(username);

        UserDto userDto = Mapper.userMapping(user);

        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    /**
     * Please check the User entity @toString method
     */
    public String getSessionUserUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
