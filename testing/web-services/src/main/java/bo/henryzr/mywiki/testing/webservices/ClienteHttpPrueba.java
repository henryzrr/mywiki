package bo.henryzr.mywiki.testing.webservices;

import bo.henryzr.mywiki.testing.webservices.dto.ResponseData;
import bo.henryzr.mywiki.testing.webservices.exceptions.ClientSideException;
import bo.henryzr.mywiki.testing.webservices.exceptions.ServerSideException;
import bo.henryzr.mywiki.testing.webservices.exceptions.UnexpectedResponseException;
import bo.henryzr.mywiki.testing.webservices.exceptions.WsClientException;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ClienteHttpPrueba {

    public String getTokenClientCredentials(@NotNull String uri, String client, String pass)  {
        String auth = client + ":" + pass;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);

        List<NameValuePair> nameValuePairList = new ArrayList<>();
        nameValuePairList.add(new BasicNameValuePair("grant_type", "client_credentials"));
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, StandardCharsets.UTF_8);
        formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");

        ResponseData responseData = null;
        try {
            HttpPost post = new HttpPost(uri);
            post.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
            post.setEntity(formEntity);

            responseData = execute(post);

        } catch (Exception e) {
            throw new WsClientException(e);
        }

        int statusResponse = responseData.getStatusResponse();
        String reasonPhrase = responseData.getReasonPhrase();

        if(isAcceptedResponse(statusResponse)){
            return responseData.getResult();
        }else {
            if(isClientSideError(statusResponse)){
                throw  new ClientSideException(statusResponse,reasonPhrase);
            }else if(isServerSideError(statusResponse)){
                throw new ServerSideException(statusResponse,reasonPhrase);
            }else{
                throw new UnexpectedResponseException(statusResponse,reasonPhrase);
            }
        }
    }

    private ResponseData execute(HttpUriRequest request) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(request);

        int statusResponse = response.getStatusLine().getStatusCode();
        String reasonPhrase = response.getStatusLine().getReasonPhrase();
        String result = EntityUtils.toString(response.getEntity());

        ResponseData responseData = new ResponseData(statusResponse,reasonPhrase,result);

        httpClient.close();
        response.close();
        return responseData;
    }

    private boolean isAcceptedResponse(int statusResponse) {
        return statusResponse==HttpStatus.SC_OK;
    }

    private boolean isCreatedResponse(int statusResponse) {
        return statusResponse== HttpStatus.SC_CREATED;
    }

    private boolean isClientSideError(int statusResponse) {
        return (statusResponse>=400 && statusResponse<=499);
    }

    private boolean isServerSideError(int statusResponse) {
        return (statusResponse>=500 && statusResponse<=599);
    }



}
