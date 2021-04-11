package ro.sd.a2.messages;

public class ErrorMessages {

    public static final String INVALID_ID = "Id can not be NULL";
    public static final String INVALID_EMAIL = "Invalid email, please try again. It must contain @gmail/@yahoo";
    public static final String INVALID_USERNAME = "Invalid admin username, please try again";
    public static final String INVALID_PASSWORD = "Invalid password, please try again (must have at least 6 characters)";
    public static final String INVALID_NAME = "You must provide your name";
    public static final String INVALID_SURNAME = "You must provide your surname";
    public static final String INVALID_DOCUMENT = "Your document must have a type";
    public static final String INVALID_OWNER = "Owner id must be specified";
    public static final String INVALID_ADDRESS = "All address fields must be specified correctly";
    public static final String INVALID_REQUEST = "All request fields must be specified correctly";
    public static final String INVALID_REQUEST_INFO = "You must enter some informations";
    public static final String INVALID_REQUEST_STATUS = "Status is not one of: Waiting/Accepted/Rejected";
    public static final String INVALID_PHONE_NUMBER = "Your phone number is incorrect (must have 10 digits)";
    public static final String INVALID_LOGIN_ADMIN = "Administrator username or password was incorrect, please try again";
    public static final String INVALID_LOGIN_USER= "Your email or password was incorrect, please try again";
    public static final String INVALID_FIND = "No data was found, please try again! Id may be incorrect";
}