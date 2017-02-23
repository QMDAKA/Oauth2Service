package mvs.configuration;

/**
 * Created by Quang Minh on 6/9/2016.
 */
public class AppConfig {
    public static String oauthzationUrl;

    public static String getOauthzationUrl() {
        return oauthzationUrl;
    }

    public static void setOauthzationUrl(String oauthzationUrl) {
        AppConfig.oauthzationUrl = oauthzationUrl;
    }
}
