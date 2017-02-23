package Config;

/**
 * Created by Quang Minh on 5/20/2016.
 */
public class URL {
    public static String url = "http://192.168.15.250:8523/oauth20/";
    public static String url_post_scope = url + "scopes";
    public static String url_post_application_id = url + "applications";
    public static String url_grant_token=url+"tokens";
    public static String url_put_application = url_post_application_id;
    public static String url_grant_codes=url+"auth-codes";
    public static String url_requset_RS="http://localhost:9859/tokens/testapi1";
}
