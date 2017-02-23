package Controller;

import Config.URL;
import com.google.gson.Gson;
import model.Application;
import model.Token;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Quang Minh on 5/20/2016.
 */
public class TokenController {
    static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

        @Override
        public String handleResponse(
                final HttpResponse response) throws ClientProtocolException, IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }

    };
    public static String codeByGrantTypeCodeFlow(Application app,String id){
        String code=new String();
        HttpClient client = HttpClientBuilder.create().build();
        try {
            String url_grant_codes= URL.url_grant_codes+"?"+"client_id="+app.getClient_id()+"&user_id="+id+"&redirect_uri="+app.getRedirect_uri()+"&response_type=code&scope="+ URLEncoder.encode(app.getScope(), "UTF-8");
            HttpGet getCode=new HttpGet(url_grant_codes);
            String response1=client.execute(getCode,responseHandler);
            JSONObject json  = new JSONObject(response1);
            String paramCode=json.getString("redirect_uri");
            int index=paramCode.indexOf("?");
            String redirectUrl=paramCode.substring(0,index);
            paramCode=paramCode.substring(index+1);
            HashMap<String,String> m=getQueryMap(paramCode);
            //code cho token
            code=m.get("code");
            code=java.net.URLDecoder.decode(code, "UTF-8");
            System.out.println(code);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
        return code;
    }
    public static HashMap<String, String> getQueryMap(String query)
    {
        String[] params = query.split("&");
        HashMap<String, String> map = new HashMap<String, String>();
        for (String param : params)
        {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }

    public static Token tokenByGrantCodeFlow(Application app,String username,String password,String code){
        Token token=new Token();
        Gson gson=new Gson();
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost postToken=new HttpPost(URL.url_grant_token);
        postToken.addHeader("Content-Type","application/x-www-form-urlencoded");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

        urlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        urlParameters.add(new BasicNameValuePair("code", code));
        urlParameters.add(new BasicNameValuePair("redirect_uri",app.getRedirect_uri() ));
        urlParameters.add(new BasicNameValuePair("client_id", app.getClient_id()));
        urlParameters.add(new BasicNameValuePair("client_secret", app.getClient_secret()));
        try {
            postToken.setEntity(new UrlEncodedFormEntity(urlParameters));
            String response=client.execute(postToken,responseHandler);
            token=gson.fromJson(response,Token.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return token;
    }

    public static Token tokenByGrantCodePassword(Application app ,String username,String password){
        Token token=new Token();
        Gson gson=new Gson();
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost postToken=new HttpPost(URL.url_grant_token);
        postToken.addHeader("Content-Type","application/x-www-form-urlencoded");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

        urlParameters.add(new BasicNameValuePair("grant_type", "password"));
        urlParameters.add(new BasicNameValuePair("client_id", app.getClient_id()));
        urlParameters.add(new BasicNameValuePair("client_secret", app.getClient_secret()));
        urlParameters.add(new BasicNameValuePair("username", username));
        urlParameters.add(new BasicNameValuePair("password", password));
        urlParameters.add(new BasicNameValuePair("scope", app.getScope()));

        try {
            postToken.setEntity(new UrlEncodedFormEntity(urlParameters));
            String response=client.execute(postToken,responseHandler);
            token=gson.fromJson(response,Token.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return token;
    }
    public static Token tokenByGrantRefeshToken(Application app ,Token oldToken){
        Token token=new Token();
        Gson gson=new Gson();
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost postToken=new HttpPost(URL.url_grant_token);
        postToken.addHeader("Content-Type","application/x-www-form-urlencoded");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

        urlParameters.add(new BasicNameValuePair("grant_type", "refresh_token"));
        urlParameters.add(new BasicNameValuePair("client_id", app.getClient_id()));
        urlParameters.add(new BasicNameValuePair("client_secret", app.getClient_secret()));
        urlParameters.add(new BasicNameValuePair("refresh_token", oldToken.getRefresh_token()));
        urlParameters.add(new BasicNameValuePair("scope", app.getScope()));

        try {

            postToken.setEntity(new UrlEncodedFormEntity(urlParameters));
            HttpResponse response=client.execute(postToken);
            if(response.getStatusLine().getStatusCode()>=200&&response.getStatusLine().getStatusCode()<300){
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");
                token=gson.fromJson(responseString,Token.class);

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return token;
    }





}
