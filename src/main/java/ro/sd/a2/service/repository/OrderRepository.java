package ro.sd.a2.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Order;
import ro.sd.a2.entity.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByOwner(User owner);
}
