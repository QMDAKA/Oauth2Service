package mvs;

import com.google.gson.Gson;
import mvs.Entity.Response;
import mvs.Entity.Token;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Quang Minh on 6/6/2016.
 */
public class Oauth2Filter {
    public static Response checkToken(TokenRepositoryInterface tokenService, ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = ((HttpServletRequest) request).getRequestURI();
        String method= ((HttpServletRequest) request).getMethod();
        url=method+" "+url;
        String tokenFromRequest = ((HttpServletRequest) request).getHeader("token");
        Gson gson = new Gson();
        url = splitUrltoPath(url);
        Response response = new Response();
        if (tokenService == null) {
            response = checkTokenWithoutService(url, tokenFromRequest);
        } else {
            response = checkTokenWithService(tokenService, url, tokenFromRequest);
        }
        return response;


    }


    static String splitUrltoPath(String url) {
        String pattern = "^(http|https):\\/\\/([^\\/]*)";
        Pattern r = Pattern.compile(pattern);

        String path = new String();
        Matcher m = r.matcher(url);
        int count = 0;
        int a = 0;
        while (m.find()) {
            count++;
            a = m.end();
        }
        path = url.substring(a);

        return path;

    }

    public static Response checkTokenWithService(TokenRepositoryInterface tokenService, String url, String tokenFromRequest) {
        Gson gson = new Gson();
        Response response = new Response();
        Token token = null;
        token = gson.fromJson(tokenService.findToken(tokenFromRequest), Token.class);
        if (token == null) {
            //truong hop token ko co trong db
            //token = TokenValidate.ValidateTokenToOauthServer(tokenFromRequest);//lay token tu oauth server
            token = TokenValidate.ValidateTokenToOauthServer(tokenFromRequest);
            if (token == null) {
                response.setStatusCode(401);
                response.setBody("khong ton tai token");
                return response;
            } else {
                tokenService.saveToken(gson.toJson(token));
            }
        } else {
            //khi token da ton tai trong db.Kiem tra con thoi gian hay chua
            int check = TokenValidate.checkTimeToken(token);
            if (check == -1) {
                //token da vuot thoi gian nen xoa
                tokenService.deleteToken(gson.toJson(token));
                response.setStatusCode(404);
                response.setBody("token vuot thoi gian");
                return response;
            } else if (check == 0) {
                //token da vuot thoi gian cache nen xin cap phat lai
                tokenService.deleteToken(gson.toJson(token));
                token = TokenValidate.ValidateTokenToOauthServer(tokenFromRequest);
                tokenService.saveToken(gson.toJson(token));

            }

        }
        //check scope cho token
        int checkScopeAndUri = TokenValidate.checkScopeOfToken(token, url);
        if (checkScopeAndUri == 0) {
            response.setStatusCode(404);
            response.setBody("token khong co quyen truy cap");
            return response;
        } else if (checkScopeAndUri == 2) {
            response.setStatusCode(405);
            response.setBody("khong ton tai url");
            return response;
        }
        response.setBody(token);
        response.setStatusCode(200);
        return response;
    }

    public static Response checkTokenWithoutService(String url, String tokenFromRequest) {
        Response response = new Response();
        Gson gson = new Gson();
        Token token = new Token();
        //truong hop token ko co trong db
        //token = TokenValidate.ValidateTokenToOauthServer(tokenFromRequest);//lay token tu oauth server
        token = TokenValidate.ValidateTokenToOauthServer(tokenFromRequest);
        if (token == null) {
            response.setStatusCode(401);
            response.setBody("khong ton tai token");
            return response;
        }

        int checkScopeAndUri = TokenValidate.checkScopeOfToken(token, url);
        if (checkScopeAndUri == 0) {
            response.setStatusCode(404);
            response.setBody("token khong co quyen truy cap");
            return response;
        } else if (checkScopeAndUri == 2) {
            response.setStatusCode(405);
            response.setBody("khong ton tai url");
            return response;
        }
        response.setBody(token);
        response.setStatusCode(200);
        return response;

    }
}