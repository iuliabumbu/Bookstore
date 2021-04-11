package ro.sd.a2.validators;

import org.thymeleaf.util.StringUtils;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.messages.ErrorMessages;

public class AdminValidators {

    public static void validateAdminByUsernameAndPassword(String username, String password){
        if(StringUtils.isEmpty(username)){
            throw new InvalidParameterException(ErrorMessages.INVALID_USERNAME);
        }

        if(StringUtils.isEmpty(password) || password.length() < 6){
            throw new InvalidParameterException(ErrorMessages.INVALID_PASSWORD);
        }
    }
}
