package Controller;

import Config.URL;
import model.Token;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Quang Minh on 5/20/2016.
 */
public class RequestController {
    public static void RequestToRS(Token token){
        HttpClient client = HttpClientBuilder.create().build();

        HttpGet getResource=new HttpGet(URL.url_requset_RS);
        getResource.addHeader("Content-Type","application/x-www-form-urlencoded");
        getResource.addHeader("token",token.getAccess_token());
        HttpResponse responseOfResourceServer = null;
        try {
            responseOfResourceServer = client.execute(getResource);
            HttpEntity entity = responseOfResourceServer.getEntity();
            String response = EntityUtils.toString(entity, "UTF-8");
            System.out.println(responseOfResourceServer.getStatusLine().getStatusCode());
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
