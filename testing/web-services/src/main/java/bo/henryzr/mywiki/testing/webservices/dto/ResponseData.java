package bo.henryzr.mywiki.testing.webservices.dto;

public class ResponseData {
    private int statusResponse;
    private String reasonPhrase;
    private String result;

    public ResponseData(int statusResponse, String reasonPhrase, String result) {
        this.statusResponse = statusResponse;
        this.reasonPhrase = reasonPhrase;
        this.result = result;
    }

    public int getStatusResponse() {
        return statusResponse;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public String getResult() {
        return result;
    }
}
