package ro.sd.a2.messages;

public class ErrorMessages {

    public static final String INVALID_ID = "Id can not be NULL";
    public static final String INVALID_EMAIL = "Invalid email, please try again. It must contain @gmail/@yahoo";
    public static final String INVALID_USERNAME = "Invalid username, please try again";
    public static final String INVALID_PASSWORD = "Invalid password, please try again (must have at least 6 characters)";
    public static final String INVALID_NAME = "You must provide your name";
    public static final String INVALID_SURNAME = "You must provide your surname";
    public static final String INVALID_TYPE = "Your genre must have a type";
    public static final String INVALID_GENRE = "This genre already exists";
    public static final String INVALID_DELETE_GENRE = "Main genres can not be deleted!";
    public static final String INVALID_SHIPPER_NAME= "Every shipper must have a name";
    public static final String INVALID_SHIPPER = "This shipper already exists";
    public static final String INVALID_COST = "Cost can not be negative";
    public static final String INVALID_BOOK = "All book fields must be completed";
    public static final String INVALID_DISCOUNT= "Discount must be between 0 and 100 %";
    public static final String INVALID_BOOK_PROMOTION = "Promotion price can not be higher than actual price";
    public static final String INVALID_PHONE_NUMBER = "Your phone number is incorrect (must have 10 digits)";
    public static final String INVALID_REGISTER_EMAIL = "An account with this email already exists!";
    public static final String INVALID_REGISTER_USERNAME = "An account with this username already exists!";
    public static final String INVALID_LOGIN_ADMIN = "Administrator username or password was incorrect, please try again";
    public static final String INVALID_LOGIN_USER= "Your email or password was incorrect, please try again";
    public static final String INVALID_FIND = "No data was found, please try again! Id may be incorrect";
    public static final String INVALID_OWNER= "Residence owner must be provided";
    public static final String INVALID_ADDRESS = "All address fields must be provided";
    public static final String INVALID_ORDER = "All order fields must be completed correctly";
    public static final String INVALID_DELETE_ORDER = "You can not delete an order that has already been confirmed/shipped/delivered!";
}