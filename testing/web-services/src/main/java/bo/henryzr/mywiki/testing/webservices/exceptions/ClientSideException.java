package bo.henryzr.mywiki.testing.webservices.exceptions;

public class ClientSideException extends WsClientException{

    public ClientSideException(String message) {
        super(message);
    }

    public ClientSideException(int statusCode, String message){
        super("Error with status code: "+statusCode + " and message: "+message);
    }

    public ClientSideException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientSideException(Throwable cause) {
        super(cause);
    }


}
