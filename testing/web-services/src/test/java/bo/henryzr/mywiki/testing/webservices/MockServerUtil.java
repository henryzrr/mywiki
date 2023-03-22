package bo.henryzr.mywiki.testing.webservices;

import org.apache.http.HttpHeaders;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Body;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.NottableString;
import org.mockserver.model.ParameterBody;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.Parameter.param;

public class MockServerUtil {

    public static void accessTokenRequestHttpStatus200(MockServerClient clientAndServer, String uri,
                                                       String clientCredentialsUser, String clientCredentialsPass){
        accessTokenRequest(
                clientAndServer, uri,
                clientCredentialsUser, clientCredentialsPass,
                accesTokenResponse200(),
                grantTypeBody());
    }

    public static void accessTokenRequest(MockServerClient clientAndServer,
                                          String uriWS,
                                          String clientCrentialsUser,
                                          String clientCrentialsPass,
                                          HttpResponse httpResponse,
                                          Body body){
        URI uri = URI.create(uriWS);
        String auth = clientCrentialsUser + ":" + clientCrentialsPass;

        String credentials =  "Basic "+ (Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8)));

        NottableString authorizationKeyHeader = NottableString.string(HttpHeaders.AUTHORIZATION);
        NottableString authorizationValueHeader = NottableString.string(credentials);

        clientAndServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath(uri.getPath())
                                .withHeader(authorizationKeyHeader,authorizationValueHeader)
                                .withBody(body)
                )
                .respond(httpResponse);

    }

    public static HttpResponse accesTokenResponse200(){
        return response()
                .withStatusCode(200)
                .withBody(Properties.TOKEN_CLIENT_CREDENTIALS_RESULT);
    }

    public static Body grantTypeBody(){
        return new ParameterBody(
                param("grant_type","client_credentials"));
    }
}
