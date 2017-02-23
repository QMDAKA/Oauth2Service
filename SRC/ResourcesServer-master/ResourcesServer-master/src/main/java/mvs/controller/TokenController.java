package mvs.controller;

import mvs.model.Account;
import mvs.model.Token;
import mvs.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Quang Minh on 5/14/2016.
 */
@RestController
public class TokenController {
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    private HttpServletRequest request;


    @RequestMapping(path = "/tokens/testapi1", method = RequestMethod.GET)

    ResponseEntity<?> testApi1(

    ) {
        String userId= (String) request.getAttribute("true_user_id");
        Account a=Request.getProfileUser(userId);
        return ResponseEntity.ok(a);
    }


    @RequestMapping(path = "/tokens/testapi2", method = RequestMethod.GET)
    ResponseEntity<?> testApi2(

    ) {

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(path = "/tokens/findToken", method = RequestMethod.GET)
    ResponseEntity<?> findToken(
            @RequestParam String tokenFromRequest
    )
    {
        Token token=tokenRepository.findByToken(tokenFromRequest);
        if(token==null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(token);
    }
    @RequestMapping(path = "/tokens/saveToken", method = RequestMethod.POST)
    ResponseEntity<?> saveToken(
            @RequestBody Token token
    )
    {
        Token f=tokenRepository.findByToken(token.getToken());
        if(f!=null){
            tokenRepository.delete(f);
        }
        tokenRepository.save(token);
        return  ResponseEntity.status(HttpStatus.OK).body(null);
    }
    @RequestMapping(path = "/tokens/deleteToken", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteToken(
            @RequestBody Token token
    )
    {
        tokenRepository.delete(token);
        return  ResponseEntity.status(HttpStatus.OK).body(null);
    }


}
