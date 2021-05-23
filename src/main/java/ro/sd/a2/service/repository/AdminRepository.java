package ro.sd.a2.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Administrator;
import ro.sd.a2.entity.User;


@Repository
public interface AdminRepository extends JpaRepository<Administrator, String> {

    Administrator findByUsernameAndPassword(String username, String password);

    Administrator findByUsername(String username);
}
