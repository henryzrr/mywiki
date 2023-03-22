package bo.henryzr.mywiki.testing.webservices.exceptions;

public class UnexpectedResponseException extends WsClientException{

    public UnexpectedResponseException(String message) {
        super(message);
    }

    public UnexpectedResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedResponseException(Throwable cause) {
        super(cause);
    }

    public UnexpectedResponseException(int statusCode, String message) {
        super(statusCode, message);
    }
}
