package ro.sd.a2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.sd.a2.dto.GenreDto;
import ro.sd.a2.entity.Genre;
import ro.sd.a2.entity.User;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.mappers.Mapper;
import ro.sd.a2.messages.ErrorMessages;
import ro.sd.a2.service.repository.GenreRepository;
import ro.sd.a2.validators.GenreValidators;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private static final Logger log = LoggerFactory.getLogger(GenreService.class);

    @Autowired
    private GenreRepository genreRepository;


    public void saveGenre(GenreDto genreDto){
        Genre genre = Mapper.GenreDtoMapping(genreDto);

        GenreValidators.validateGenreType(genre.getType());
        genreRepository.save(genre);

        log.info("Successfully created genre " + genre.toString());
    }

    public void updateGenre(GenreDto genreDto){
        Optional<Genre> genre = findGenreById(genreDto.getId());

        if( genre.isPresent()){
            genre.get().setType(genreDto.getType());

            GenreValidators.validateGenreType(genre.get().getType());
            genreRepository.save(genre.get());

            log.info("Successfully updated genre " + genre.toString());
        }
        else{
            throw new InvalidParameterException(ErrorMessages.INVALID_FIND);
        }

    }

    public void deleteGenre(GenreDto genreDto){
        Optional<Genre> genre = findGenreById(genreDto.getId());

        if( genre.isPresent()){
            genre.get().setDeleted("yes");

            genreRepository.save(genre.get());

            log.info("Successfully deleted genre " + genre.toString());
        }
        else{
            throw new InvalidParameterException(ErrorMessages.INVALID_FIND);
        }

    }

    public Optional<Genre> findGenreById(String id){
        GenreValidators.validateGenreId(id);
        Optional<Genre> genre = genreRepository.findById(id);

        return genre;
    }


    public List<Genre> findAllGenres(){
        List<Genre> genres = genreRepository.findAll();

        return genres;
    }




}
