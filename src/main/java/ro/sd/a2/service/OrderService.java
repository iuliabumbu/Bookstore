package ro.sd.a2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.BookDto;
import ro.sd.a2.dto.GenreDto;
import ro.sd.a2.dto.OrderDto;
import ro.sd.a2.entity.*;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.mappers.Mapper;
import ro.sd.a2.messages.ErrorMessages;
import ro.sd.a2.service.repository.OrderRepository;
import ro.sd.a2.service.repository.UserRepository;
import ro.sd.a2.validators.BookValidators;
import ro.sd.a2.validators.OrderValidators;
import ro.sd.a2.validators.ShipperValidators;
import ro.sd.a2.validators.UserValidators;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    public void saveOrder(OrderDto orderDto, User user){
        log.info("Insert order attempt");

        Order order = Mapper.OrderDtoMapping(orderDto);
        System.out.println("Order " + order.toString());
        OrderValidators.validateOrder(order);
        orderRepository.save(order);

        log.info("Successfully created order " + order.toString());
    }

    public List<Order> findAllByUser(User user){
        UserValidators.validateInsertUser(user);
        return orderRepository.findByOwner(user);

    }

    public Optional<Order> findOrderById(String id){
        log.info("Find Order by id attempt " + id);

        OrderValidators.validateOrderId(id);
        Optional<Order> order = orderRepository.findById(id);

        return order;
    }

    public List<Order> findAllOrders(){
        log.info("Find all orders attempt");

        List<Order> orders = orderRepository.findAll();

        return orders;
    }

    public void changeStatus(String id, String status) {
        log.info("Change order status attempt");
        Optional<Order> order = findOrderById(id);
        order.get().setStatus(status);

        OrderValidators.validateOrder(order.get());

        orderRepository.save(order.get());
    }

    public void deleteOrder(OrderDto orderDto){
        log.info("Delete genre attempt");

        Optional<Order> order = findOrderById(orderDto.getId());

        if( order.isPresent()){
            order.get().setDeleted("yes");
            OrderValidators.validateOrder(order.get());

            orderRepository.save(order.get());

            log.info("Successfully deleted order " + order.toString());
        }
        else{
            throw new InvalidParameterException(ErrorMessages.INVALID_FIND);
        }

    }
}
