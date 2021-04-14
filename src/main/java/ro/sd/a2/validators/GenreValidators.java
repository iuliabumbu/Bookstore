package ro.sd.a2.validators;

import org.thymeleaf.util.StringUtils;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.messages.ErrorMessages;

public class GenreValidators {

    public static void validateGenreType(String type){
        if(StringUtils.isEmpty(type)){
            throw new InvalidParameterException(ErrorMessages.INVALID_TYPE);
        }
    }
}
