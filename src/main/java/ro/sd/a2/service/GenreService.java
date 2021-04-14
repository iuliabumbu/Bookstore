package ro.sd.a2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.sd.a2.entity.Genre;
import ro.sd.a2.entity.User;
import ro.sd.a2.service.repository.GenreRepository;
import ro.sd.a2.validators.GenreValidators;
import ro.sd.a2.validators.UserValidators;

import java.util.List;

@Service
public class GenreService {

    private static final Logger log = LoggerFactory.getLogger(GenreService.class);

    @Autowired
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository){

        this.genreRepository = genreRepository;
    }

    public void saveGenre(Genre genre){
        GenreValidators.validateGenreType(genre.getType());
        genreRepository.save(genre);

        log.info("Successfully created genre " + genre.toString());
    }

    public List<Genre> findAllGenres(){
        List<Genre> genres = genreRepository.findAll();

        return genres;
    }

    public String findGenreId(String type){
        List<Genre> genres = genreRepository.findAll();

        genres.removeIf(p -> !p.getType().equals(type));

        return genres.get(0).getId();
    }



}
