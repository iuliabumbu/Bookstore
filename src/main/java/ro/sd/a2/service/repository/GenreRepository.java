package ro.sd.a2.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String>{
}
