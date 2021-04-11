package ro.sd.a2.service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.UserDto;
import ro.sd.a2.entity.User;
import ro.sd.a2.factory.UserFactory;
import ro.sd.a2.mappers.Mapper;
import ro.sd.a2.service.repository.UserRepository;
import ro.sd.a2.validators.UserValidators;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {

        return new ArrayList<>();
    }

    public void saveUser(User user){
        UserValidators.validateInsertUser(user);
        userRepository.save(user);

        log.info("Successfully created user " + user.toString());
    }

    public UserDto findByEmailAndPassword(String email, String password){
        UserValidators.validateFindUserByEmailAndPassword(email, password);
        User user = userRepository.findByEmailAndPassword(email, password);
        UserDto userDto = Mapper.userMapping(user);

        return userDto;
    }

}
