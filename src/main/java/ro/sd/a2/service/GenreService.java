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

    /**
     * Metoda responsabila cu inserarea in baza de date a unei noi categorii
     * @param genreDto - categoria pe care dorim sa o introducem
     */
    public void saveGenre(GenreDto genreDto){
        log.info("Insert genre attempt");

        Genre genre = Mapper.GenreDtoMapping(genreDto);

        GenreValidators.validateGenreType(genre.getType());
        genreRepository.save(genre);

        log.info("Successfully created genre " + genre.toString());
    }
    /**
     * Metoda responsabila cu actualizarea datelor unei categorii
     * @param genreDto - categoria pe care dorim sa o actualizam
     * @throws InvalidParameterException - daca categoria pe care vrem sa o actualizam nu exista in baza de date
     */
    public void updateGenre(GenreDto genreDto){
        log.info("Update genre attempt");

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
    /**
     * Metoda responsabila cu stergerea unei categorii (stergere marcata prin setarea "yes" a campului deleted, nu stergere fizica)
     * @param genreDto - categoria pe care dorim sa o stergem
     * @throws InvalidParameterException - daca categoria pe care vrem sa o stergem nu exista in baza de date
     */
    public void deleteGenre(GenreDto genreDto){
        log.info("Delete genre attempt");

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
    /**
     * Metoda responsabila cu gasirea unei categorii din baza de date
     * @param id - id-ul catgoriei pe care dorim sa o gasim
     * @return - returneaza categoria gasita
     */
    public Optional<Genre> findGenreById(String id){
        log.info("Find genre by id attempt " + id);

        GenreValidators.validateGenreId(id);
        Optional<Genre> genre = genreRepository.findById(id);

        return genre;
    }

    /**
     * Metoda responsabila cu gasirea tuturor categoriilor din baza de date
     * @return - returneaza o lista ce contine toate categoriile gasite
     */
    public List<Genre> findAllGenres(){
        log.info("Find all genres attempt");
        List<Genre> genres = genreRepository.findAll();

        return genres;
    }




}
