package mvs.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Quang Minh on 5/16/2016.
 */
public class AppConfig {
    public static String url_server;

    public static String getUrl_server() {
        return url_server;
    }

    public static void setUrl_server(String url_server) {
        AppConfig.url_server = url_server;
    }

    public static HashMap<String, List<String>> appConfig = new HashMap<String, List<String>>();

    public AppConfig() {
    }

    public static HashMap<String, List<String>> getAppConfig() {
        return appConfig;
    }

    public static void setAppConfig(HashMap<String, List<String>> appConfig) {
       AppConfig.appConfig = appConfig;
    }
    public static List<String> getScopes(String path) {
        String _path = path.replaceAll("\\s{1,}", " ").trim();
        for (Map.Entry<String, List<String>> api : appConfig.entrySet()) {
            if (_path.matches(api.getKey()))
                return api.getValue();
        }
        return null;
    }
}
