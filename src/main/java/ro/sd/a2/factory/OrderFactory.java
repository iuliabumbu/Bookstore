package ro.sd.a2.factory;

import ro.sd.a2.entity.Order;
import ro.sd.a2.entity.User;

import java.util.Date;
import java.util.UUID;

public class OrderFactory {

    public static Order generateOrder(){
        Order order = new Order();
        order.setDeleted("no");
        order.setId(UUID.randomUUID().toString());
        order.setData(new Date());
        order.setStatus("Confirmed");
        return order;
    }
}
