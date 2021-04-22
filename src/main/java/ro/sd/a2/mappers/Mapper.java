package ro.sd.a2.mappers;

import ro.sd.a2.dto.*;
import ro.sd.a2.entity.Book;
import ro.sd.a2.entity.Genre;
import ro.sd.a2.entity.Shipper;
import ro.sd.a2.entity.User;
import ro.sd.a2.factory.BookFactory;
import ro.sd.a2.factory.GenreFactory;
import ro.sd.a2.factory.ShipperFactory;
import ro.sd.a2.factory.UserFactory;
import ro.sd.a2.validators.BookValidators;

public class Mapper {

    public static LoginDto loginMapping(User user){

        return  user == null ? null : LoginDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password((user.getPassword()))
                .build();
    }

    public static UserDto userMapping(User user){

        return user == null ? null : UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password((user.getPassword()))
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public static GenreDto genreMapping(Genre genre){

        return genre == null ? null : GenreDto.builder()
                .id(genre.getId())
                .type(genre.getType())
                .build();
    }

    public static BookDto bookMapping(Book book){

        return book == null ? null : BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .description(book.getDescription())
                .genre(book.getGenre())
                .image(book.getImage())
                .build();
    }

    public static ShipperDto shipperMapping(Shipper shipper){

        return shipper == null ? null : ShipperDto.builder()
                .id(shipper.getId())
                .name(shipper.getName())
                .cost(shipper.getCost())
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
