package ro.sd.a2.validators;

import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;
import ro.sd.a2.entity.Order;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.messages.ErrorMessages;

public class OrderValidators {

    public static void validateOrder(Order order){
        if(StringUtils.isEmpty(order.getId())){
            throw new InvalidParameterException(ErrorMessages.INVALID_ID);
        }

        if(order.getData() == null || order.getShipper() == null || order.getOwner() == null ||
                order.getAddress() == null || StringUtils.isEmpty(order.getStatus()) || CollectionUtils.isEmpty(order.getItems()) ||
                (!order.getStatus().equals("In progress") && !order.getStatus().equals("Confirmed")&& !order.getStatus().equals("Shipped")
                        && !order.getStatus().equals("Delivered"))){
            throw new InvalidParameterException(ErrorMessages.INVALID_ORDER);
        }


    }
}
