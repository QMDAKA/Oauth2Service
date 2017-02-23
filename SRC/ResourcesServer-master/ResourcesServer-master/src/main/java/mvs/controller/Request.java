package mvs.controller;

import mvs.model.Account;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Quang Minh on 5/28/2016.
 */
public class Request {
    public static String url_user = "http://192.168.15.250:9858/accounts/";

    public static Account getProfileUser(String userId) {
        Account object = new Account();
        HttpClient client =new DefaultHttpClient();
        HttpGet getToken = new HttpGet(url_user+userId);
        try {
            HttpResponse response = client.execute(getToken);
            if (response.getStatusLine().getStatusCode() < 300 && response.getStatusLine().getStatusCode() >= 200) {
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");
                JSONObject json = new JSONObject(responseString);
                object.setNickName(json.getString("nickName"));
                object.setDes(json.getString("des"));
                object.setSex(json.getString("sex"));
                object.setBirth(json.getString("birth"));

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
