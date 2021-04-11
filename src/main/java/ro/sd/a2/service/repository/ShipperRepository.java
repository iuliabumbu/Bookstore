package ro.sd.a2.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Book;
import ro.sd.a2.entity.Shipper;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, String> {
}
