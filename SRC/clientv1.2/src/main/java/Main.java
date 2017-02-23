import Controller.AppController;
import Controller.RequestController;
import Controller.TokenController;
import com.google.gson.Gson;
import model.Application;
import model.Token;
import java.io.IOException;
/**
 * Created by Quang Minh on 5/11/2016.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Gson gson=new Gson();
        // tao scope
        //tao application id
        Application app = AppController.RegisterApp();
        System.out.println(gson.toJson(app));
        //tao application
        AppController.MakeAppId(app.getClient_id());
        //xin cap phat token theo Resource owner password credentials grant
        //make codes
        String code=new String();
        code= TokenController.codeByGrantTypeCodeFlow(app,"1");
        //make token
       // Token token=TokenController.tokenByGrantCodeFlow(app,"minh","123456",code);
        Token token=TokenController.tokenByGrantCodePassword(app,"minh","123456");
        System.out.println(gson.toJson(token));
        //request len resource server
        RequestController.RequestToRS(token);
    }



}
