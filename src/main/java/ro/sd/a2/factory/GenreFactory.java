package ro.sd.a2.factory;

import ro.sd.a2.entity.Genre;


import java.util.UUID;

public class GenreFactory {

    public static Genre generateGenre(){
        Genre genre = new Genre();
        genre.setDeleted("no");
        genre.setId(UUID.randomUUID().toString());
        return genre;
    }
}
