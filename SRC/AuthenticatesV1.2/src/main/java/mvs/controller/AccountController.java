package mvs.controller;

import mvs.configuration.AppConfig;
import mvs.model.Account;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mvs.repository.AccountRepository;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Quang Minh on 5/10/2016.
 */
@RestController
public class AccountController {
    @Autowired
    AccountRepository accountRepository;

    @RequestMapping(path = "/accounts/authenticate", method = RequestMethod.GET)
    ResponseEntity<?> check(
            @RequestParam String username,
            @RequestParam String password

            ) {

        List<Account> accountList = accountRepository.findAll();
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getUsername().compareTo(username) == 0 && accountList.get(i).getPassword().compareTo(password) == 0) {
                Long user_id=accountList.get(i).getId();
                return ResponseEntity.ok(user_id);
            }

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(-1);

    }

    @RequestMapping(path = "/getCode", method = RequestMethod.GET)
    ResponseEntity<?> getCode(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String client_id,
            @RequestParam String redirect_uri,
            @RequestParam String scope
    ) {
        long user_id = -1;
        List<Account> accountList = accountRepository.findAll();
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getUsername().compareTo(username) == 0 && accountList.get(i).getPassword().compareTo(password) == 0) {
              user_id=accountList.get(i).getId();
            }

        }
        if(user_id==-1){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(-1);
        }
        else {
            String code = new String();
            String error=new String();
            HttpClient client = HttpClientBuilder.create().build();
            try {
                String url_grant_codes = AppConfig.getOauthzationUrl()+"/oauth20/auth-codes" + "?" + "client_id=" + client_id + "&user_id=" + user_id + "&redirect_uri=" + redirect_uri + "&response_type=code&scope=" + URLEncoder.encode(scope, "UTF-8");
                HttpGet getCode = new HttpGet(url_grant_codes);

                HttpResponse response = client.execute(getCode);

                if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300) {
                    HttpEntity entity = response.getEntity();
                    code = EntityUtils.toString(entity, "UTF-8");
                    //code = java.net.URLDecoder.decode(code, "UTF-8");
                    JSONObject jsonObject=new JSONObject(code);
                    String redirect_url=jsonObject.getString("redirect_uri");
                    return ResponseEntity.ok(redirect_url);
                }

                //code cho token
                System.out.println(code);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid auth_code");

    }

    @RequestMapping(path = "/accounts/test",method = RequestMethod.GET)
    ResponseEntity<?> test(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid auth_code");


    }
}
