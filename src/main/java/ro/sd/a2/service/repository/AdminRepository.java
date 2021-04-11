package ro.sd.a2.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Administrator;


@Repository
public interface AdminRepository extends JpaRepository<Administrator, String> {

    Administrator findByUsernameAndPassword(String username, String password);
}
