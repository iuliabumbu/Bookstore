package ro.sd.a2.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByEmailAndPasswordAndDeleted(String email, String password, String deleted);

    User findByEmailAndDeleted(String email, String deleted);

    User findByUsername(String username);

}
