package ro.sd.a2.mappers;

import ro.sd.a2.dto.*;
import ro.sd.a2.entity.Book;
import ro.sd.a2.entity.Genre;
import ro.sd.a2.entity.Shipper;
import ro.sd.a2.entity.User;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.factory.BookFactory;
import ro.sd.a2.factory.GenreFactory;
import ro.sd.a2.factory.ShipperFactory;
import ro.sd.a2.factory.UserFactory;
import ro.sd.a2.messages.ErrorMessages;
import ro.sd.a2.validators.BookValidators;
import ro.sd.a2.validators.GenreValidators;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static LoginDto loginMapping(User user){

        if(user == null){
            return null;
        }

        return  LoginDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password((user.getPassword()))
                .build();
    }

    public static User UserDtoMapping(UserDto user){

        User newUser = UserFactory.generateUser();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setName(user.getName());
        newUser.setSurname(user.getSurname());
        newUser.setPhoneNumber(user.getPhoneNumber());

        return newUser;
    }

    public static Genre GenreDtoMapping(GenreDto genre){

        Genre newGenre = GenreFactory.generateGenre();
        newGenre.setType(genre.getType());

        return newGenre;
    }

    public static Shipper ShipperDtoMapping(ShipperDto shipper){

        Shipper newShipper = ShipperFactory.generateShipper();
        newShipper.setName(shipper.getName());
        newShipper.setCost(shipper.getCost());

        return newShipper;
    }

    public static Book BookDtoMapping(BookDto book){

        Book newBook = BookFactory.generateBook();
        newBook.setTitle(book.getTitle());
        newBook.setAuthor(book.getAuthor());
        newBook.setDescription(book.getDescription());
        newBook.setImage(book.getImage());
        newBook.setGenre(book.getGenre());
        newBook.setPrice(book.getPrice());
        BookValidators.validateBook(newBook);

        return newBook;
    }

}
