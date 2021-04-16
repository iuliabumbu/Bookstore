package ro.sd.a2.service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.LoginDto;
import ro.sd.a2.dto.UserDto;
import ro.sd.a2.entity.Genre;
import ro.sd.a2.entity.Shipper;
import ro.sd.a2.entity.User;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.factory.UserFactory;
import ro.sd.a2.mappers.Mapper;
import ro.sd.a2.messages.ErrorMessages;
import ro.sd.a2.service.repository.UserRepository;
import ro.sd.a2.validators.GenreValidators;
import ro.sd.a2.validators.UserValidators;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private  UserRepository userRepository;


    public List<User> getAllUsers() {

        return new ArrayList<>();
    }

    public void saveUser(UserDto userDto){
        User user = Mapper.UserDtoMapping(userDto);
        UserValidators.validateInsertUser(user);
        userRepository.save(user);

        log.info("Successfully created user " + user.toString());
    }

    public List<User> findAllUsers(){
        List<User> users = userRepository.findAll();

        return users;
    }

    public LoginDto findByEmailAndPassword(String email, String password){
        UserValidators.validateFindUserByEmailAndPassword(email, password);
        User user = userRepository.findByEmailAndPasswordAndDeleted(email, password, "no");

        LoginDto loginDto = Mapper.loginMapping(user);

        return loginDto;
    }

    public void updateUser(UserDto userDto){
        Optional<User> user = findUserById(userDto.getId());

        if( user.isPresent()) {
            user.get().setName(userDto.getName());
            user.get().setSurname(userDto.getSurname());
            user.get().setEmail(userDto.getEmail());
            user.get().setPassword(userDto.getPassword());
            user.get().setPhoneNumber(userDto.getPhoneNumber());

            UserValidators.validateInsertUser(user.get());
            userRepository.save(user.get());

            log.info("Successfully updated user " + user.toString());
        }
        else{
            throw new InvalidParameterException(ErrorMessages.INVALID_FIND);
        }

    }

    public void deleteUser(UserDto userDto){
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

    public Optional<User> findUserById(String id){
        UserValidators.validateFindUserById(id);
        Optional<User> user = userRepository.findById(id);

        return user;
    }

}
