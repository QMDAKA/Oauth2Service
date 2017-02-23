package mvs;

import mvs.Entity.Token;

/**
 * Created by Quang Minh on 6/4/2016.
 */
public interface TokenRepositoryInterface {
    void deleteToken(String token);
    void saveToken(String token);
    String findToken(String tokenFromRequest);
}
