package mvs;

import mvs.Entity.AppConfig;
import mvs.Entity.Token;
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
import java.util.Arrays;
import java.util.List;

/**
 * Created by Quang Minh on 6/3/2016.
 */
public class TokenValidate {
    public static String urlRequestToOauthServer = AppConfig.url_server+"/oauth20/tokens/";
    public static Token ValidateTokenToOauthServer(String tokenFromRequest) {
        Token token = new Token();
        HttpClient client =  new DefaultHttpClient();
        String url_valdidate_token = urlRequestToOauthServer + "validate?token=" + tokenFromRequest;
        HttpGet getToken = new HttpGet(url_valdidate_token);
        try {
            HttpResponse response = client.execute(getToken);
            if (response.getStatusLine().getStatusCode() <= 300 && response.getStatusLine().getStatusCode() >= 200) {
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");
                JSONObject json = new JSONObject(responseString);
                token.setToken(json.getString("token"));
                token.setUserId(json.getString("userId"));
                token.setCreatedCache((long) System.currentTimeMillis());
                token.setCreated(json.getLong("created"));
                token.setExpiresIn(Integer.parseInt(json.getString("expiresIn")));
                token.setRefeshToken(json.getString("refreshToken"));
                token.setScope(json.getString("scope"));
                token.setType(json.getString("type"));
            } else {
                token = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }

    public static int checkTimeToken(Token token) {
        long currentTime = System.currentTimeMillis();
        if (currentTime > token.getCreated() + token.getExpiresIn() * 1000)
            return -1;
        else if (currentTime > token.getCreatedCache() + 300000)
            return 0;
        return 1;
    }

    public static int checkScopeOfToken(Token token, String url) {
        List<String> scopesOfUrl = AppConfig.getScopes(url);
        if(scopesOfUrl==null)
            return 2;
        else {
            List<String> scopesOfToken = Arrays.asList(token.getScope().split(" "));
            int count = 0;
            for (String i : scopesOfToken) {
                for (String j : scopesOfUrl) {
                    if (i.compareTo(j) == 0) {
                        count++;
                    }
                }
            }
            if (count == scopesOfUrl.size()) {
                return 1;
            } else {
                return 0;
            }
        }
    }



}
