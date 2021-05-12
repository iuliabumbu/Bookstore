package ro.sd.a2.validators;

import org.thymeleaf.util.StringUtils;
import ro.sd.a2.entity.User;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.messages.ErrorMessages;

public class UserValidators {
    public static void validateFindUserById(String id){
        validateGenericId(id);
    }

    public static void validateInsertUser(User user){

        validateGenericId(user.getId());

        validateGenericEmailAndPassword(user.getEmail(), user.getPassword());

        if(StringUtils.isEmpty(user.getName())){
            throw new InvalidParameterException(ErrorMessages.INVALID_NAME);
        }

        if(StringUtils.isEmpty(user.getSurname())){
            throw new InvalidParameterException(ErrorMessages.INVALID_SURNAME);
        }

        if(StringUtils.isEmpty(user.getPhoneNumber())|| user.getPhoneNumber().length() != 10
                || !user.getPhoneNumber().matches("[0-9]+")){
            throw new InvalidParameterException(ErrorMessages.INVALID_PHONE_NUMBER);
        }

    }
    public static void validateFindUserByEmailAndPassword(String email, String password){
        validateGenericEmailAndPassword(email, password);
    }

    private static void validateGenericId(String id){
        if(StringUtils.isEmpty(id)){
            throw new InvalidParameterException(ErrorMessages.INVALID_ID);
        }
    }

    private static void validateGenericEmailAndPassword(String email, String password){
        if(StringUtils.isEmpty(email) || (!email.contains("@yahoo") && !email.contains("@gmail"))){
            throw new InvalidParameterException(ErrorMessages.INVALID_EMAIL);
        }

        if(StringUtils.isEmpty(password)|| password.length() < 6){
            throw new InvalidParameterException(ErrorMessages.INVALID_PASSWORD);
        }
    }

    public static void validateFindUserByEmail(String email){
        if(StringUtils.isEmpty(email) || (!email.contains("@yahoo") && !email.contains("@gmail"))){
            throw new InvalidParameterException(ErrorMessages.INVALID_EMAIL);
        }

    }
}
