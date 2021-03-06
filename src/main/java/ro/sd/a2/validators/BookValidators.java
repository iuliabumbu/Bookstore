package ro.sd.a2.validators;

import org.thymeleaf.util.StringUtils;
import ro.sd.a2.entity.Book;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.messages.ErrorMessages;

public class BookValidators {

    public static void validateBook(Book book){

            if(StringUtils.isEmpty(book.getAuthor()) || StringUtils.isEmpty(book.getTitle()) || StringUtils.isEmpty(book.getDescription())
                 || StringUtils.isEmpty(book.getImage())) {
                throw new InvalidParameterException(ErrorMessages.INVALID_BOOK);
            }

            if(book.getPrice() < 0 || book.getPromotionPrice() < -1) {
                throw new InvalidParameterException(ErrorMessages.INVALID_COST);
            }

            if(book.getPrice() < book.getPromotionPrice() ) {
                throw new InvalidParameterException(ErrorMessages.INVALID_BOOK_PROMOTION);
            }
    }

    public static void validateBookId(String id){
        if(StringUtils.isEmpty(id)){
            throw new InvalidParameterException(ErrorMessages.INVALID_ID);
        }
    }
}
