package mvs;

import org.junit.Test;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Oauth2FilterTest {

    public class MockTokenService implements TokenRepositoryInterface{
        String tokenFromRequest;
        String tokenObject;

        public String getTokenObject() {
            return tokenObject;
        }

        public void setTokenObject(String tokenObject) {
            this.tokenObject = tokenObject;
        }

        public String getTokenFromRequest() {
            return tokenFromRequest;
        }

        public void setTokenFromRequest(String tokenFromRequest) {
            this.tokenFromRequest = tokenFromRequest;
        }

        List<String> tokens = new ArrayList<String>();
        @Override
        public void deleteToken(String token) {

        }

        @Override
        public void saveToken(String token) {
            tokens.add(token);
        }

        @Override
        public String findToken(String tokenFromRequest) {
            for(String token: tokens)
                if(token == tokenFromRequest){

                    return tokenObject;

                }
            return null;
        }
    }
    String tokenFromRequest="44f0ac27c5d8fe350f7c7d12af781646fdb4d866abce85c4a040f0b6cd40acaf";
    @Test
    public void testCheckToken()throws Exception{
        LoadConfig.loadApi("D:\\ProjectWS\\FilterEx\\src\\config.json");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setUrl("/tokens/testapi1");
        request.setHeader(tokenFromRequest);
        MockTokenService tokenService = new MockTokenService();
        Oauth2Filter.checkToken(null,request);
    }
    @Test
    public void testCheckTokenTestWithService() throws Exception {
        MockTokenService tokenService = new MockTokenService();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setUrl("http://localhost:12323/àdf");
        LoadConfig.loadApi("D:\\ProjectWS\\FilterEx\\src\\config.json");
        Oauth2Filter.checkTokenWithService(tokenService,"/tokens/testapi1",tokenFromRequest);
    }

    @Test
    public void testCheckScopeTokenWithService()throws Exception{
        LoadConfig.loadApi("D:\\ProjectWS\\FilterEx\\src\\config.json");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setUrl("/account/1/friend/2");
        request.setHeader(tokenFromRequest);
        MockTokenService tokenService = new MockTokenService();
        tokenService.setTokenFromRequest(tokenFromRequest);
        tokenService.setTokenObject("{\"access_token\":\"44f0ac27c5d8fe350f7c7d12af781646fdb4d866abce85c4a040f0b6cd40acaf\",\"created\":1465440174305,\"userId\":1,\"expiresIn\":\"36000\",\"scope\":\"read_profile read_list_friend\",\"createdCache\":\"1465440174305\"}\n");
        tokenService.tokens.add(tokenFromRequest);

        Oauth2Filter.checkToken(tokenService,request);
    }

}