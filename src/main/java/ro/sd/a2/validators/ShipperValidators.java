package ro.sd.a2.validators;

import org.thymeleaf.util.StringUtils;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.messages.ErrorMessages;

public class ShipperValidators {

    public static void validateShipper(String name, float cost){
        if(StringUtils.isEmpty(name)){
            throw new InvalidParameterException(ErrorMessages.INVALID_SHIPPER_NAME);
        }
        if(cost < 0){
            throw new InvalidParameterException(ErrorMessages.INVALID_COST);
        }
    }
}
