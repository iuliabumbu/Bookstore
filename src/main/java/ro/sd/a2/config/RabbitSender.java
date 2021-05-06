package ro.sd.a2.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RabbitSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${queue.rabbitmq.exchange}")
    private String exchange;

    @Value("${queue.rabbitmq.routingkey}")
    private String routingkey;

    public void send(String payload) {
        rabbitTemplate.convertAndSend(exchange, routingkey, payload);
        System.out.println("Send msg = " + payload);
    }
}