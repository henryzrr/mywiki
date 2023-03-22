package bo.henryzr.mywiki.testing.webservices.exceptions;

public class ServerSideException extends WsClientException{

    public ServerSideException(String message) {
        super(message);
    }

    public ServerSideException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerSideException(Throwable cause) {
        super(cause);
    }

    public ServerSideException(int statusCode, String message) {
        super(statusCode, message);
    }
}
