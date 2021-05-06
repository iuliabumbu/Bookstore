package ro.sd.a2.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Residence;
import ro.sd.a2.entity.User;

@Repository
public interface ResidenceRepository extends JpaRepository<Residence, String> {

    Residence findByOwner(User owner);
}
