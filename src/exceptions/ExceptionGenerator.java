package exceptions;

/**
 *
 * @author Yeray Sampedro
 */
public class ExceptionGenerator {

    public static String exceptionGenerator(int code) {
        String msg = new String();
        switch (code) {
            case 400:
                msg = "The format of the message is incorrect, try writing it according to the indications";
                break;
            case 401:
                msg = "We could not confirm who you are. It could be because your information is wrong";
                break;
            case 403:
                msg = "You have no access to this information, contact to your supervisor in case of a mistake";
                break;
            case 404:
                msg = "There is no information to display, try changing it a little bit";
                break;
            case 409:
                msg = "This information does already exist in the database, try with another login or email";
                break;
            case 500:
                msg = "There is a problem with the server, try again in a few minutes";
                break;
            default:
                msg = "There was an unexpected error, contact your administrator immediately";
                break;
        }
        return msg;
    }
}
