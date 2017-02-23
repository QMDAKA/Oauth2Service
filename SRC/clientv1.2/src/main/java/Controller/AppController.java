package Controller;

import Config.URL;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import model.Application;
import org.apache.http.HttpResponse;

/**
 * Created by Quang Minh on 5/20/2016.
 */
public class AppController {
    public static Application RegisterApp(){
        Gson gson=new Gson();
        Application app=new Application();
        app.setScope("read_profile read_list_friend");
        app.setName("AppTest");
        app.setDescription("this app for me");
        app.setRedirect_uri("http://10.46.16.93:8080");
        HttpRequest request = HttpRequest.post(URL.url_post_application_id)
                .accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .acceptEncoding("gzip,deflate")
                .contentType("application/json");
        String jsonInString=gson.toJson(app);
        String response = request.send(jsonInString).body();
        System.out.println(response);
        return app;
    }
    public static void MakeAppId(String appID){
        HttpRequest request = HttpRequest.put(URL.url_post_application_id + "/" +appID)
                .contentType("application/json");
        String response = request.send("{\"status\":1}").body();
        System.out.println(response);

    }
}
