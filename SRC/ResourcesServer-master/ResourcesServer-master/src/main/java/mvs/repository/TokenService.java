package mvs.repository;

import com.google.gson.Gson;
import mvs.TokenRepositoryInterface;
import mvs.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Quang Minh on 6/4/2016.
 */

@Service
public class TokenService implements TokenRepositoryInterface {
    @Autowired
    TokenRepository tokenRepository;
    Gson gson=new Gson();
    @Override
    public void deleteToken(String s) {
       Token token=gson.fromJson(s, Token.class);
        tokenRepository.delete(token);
    }

    @Override
    public void saveToken(String s) {
        Token token=gson.fromJson(s, Token.class);
        tokenRepository.save(token);
    }

    @Override
    public String findToken(String s) {
        Token t= tokenRepository.findByToken(s);
        return gson.toJson(tokenRepository.findByToken(s));
    }
}
