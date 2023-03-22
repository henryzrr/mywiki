package bo.henryzr.mywiki.testing.webservices;


import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class ClienteHttpPruebaTest {

    private ClientAndServer clientAndServer;
    public static Gson gson;

    @BeforeAll
    public static void setUp(){
        gson = new Gson();
    }

    @BeforeEach
    public void beforeEach(){
        URI uri = URI.create(Properties.URI_GET_TOKEN_CLIENT_CREDENTIALS);
        clientAndServer = ClientAndServer.startClientAndServer(uri.getPort());
    }

    @AfterEach
    public void afterEach(){
        if(clientAndServer.isRunning()){
            clientAndServer.stop();
        }
    }

    @Test
    void when_wsAuthenticationResponseHttpStatus_is200_then_getToken() {
        MockServerUtil.accessTokenRequestHttpStatus200(clientAndServer,
                Properties.URI_GET_TOKEN_CLIENT_CREDENTIALS,
                Properties.USER_CLIENT_CREDENTIALS,
                Properties.PASSWORD_CLIENT_CREDENTIALS);

        ClienteHttpPrueba clienteHttpPrueba = new ClienteHttpPrueba();

        String result = clienteHttpPrueba.getTokenClientCredentials(
                Properties.URI_GET_TOKEN_CLIENT_CREDENTIALS,
                Properties.USER_CLIENT_CREDENTIALS,
                Properties.PASSWORD_CLIENT_CREDENTIALS);


        assertNotNull(result);
        assertNotEquals("",result);
        assertEquals(Properties.TOKEN_CLIENT_CREDENTIALS_RESULT,result);
        System.out.println(result);
    }

}