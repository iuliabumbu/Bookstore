package ro.sd.a2.validators;

import org.thymeleaf.util.StringUtils;
import ro.sd.a2.entity.Address;
import ro.sd.a2.entity.Residence;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.messages.ErrorMessages;

public class ResidenceValidators {


    public static void validateResidence(Residence residence){
        if(StringUtils.isEmpty(residence.getId())){
            throw new InvalidParameterException(ErrorMessages.INVALID_ID);
        }

        if(StringUtils.isEmpty(residence.getOwner().getId())){
            throw new InvalidParameterException(ErrorMessages.INVALID_OWNER);
        }

        for(Address address : residence.getAddresses()){
            if(StringUtils.isEmpty(address.getApartment()) || StringUtils.isEmpty(address.getNumber())
            || StringUtils.isEmpty(address.getStreet()) || StringUtils.isEmpty(address.getCity())){
                throw new InvalidParameterException(ErrorMessages.INVALID_ADDRESS);
            }
        }
       
    }
}
