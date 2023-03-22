package bo.henryzr.mywiki.testing.webservices.exceptions;

public class WsClientException extends RuntimeException{

    public WsClientException(String message) {
        super(message);
    }

    public WsClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public WsClientException(Throwable cause) {
        super(cause);
    }

    protected WsClientException(int statusCode, String message){
        super("Error with status code: "+statusCode + " and message: "+message);
    }
}
