package mvs;

import mvs.Entity.Api;
import mvs.Entity.AppConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Quang Minh on 6/3/2016.
 */
public class LoadConfig {
    public static void loadApi(String path){
        JSONParser parser = new JSONParser();
        Object obj;
        try {
            obj = parser.parse(new FileReader(path));
            JSONObject jsonObject =  (JSONObject) obj;
            AppConfig.setUrl_server((String) ((JSONObject) obj).get("url_server"));
            JSONArray api = (JSONArray) jsonObject.get("api");
            HashMap<String,List<String >> listMap=new HashMap<String, List<String>>();
            for(int i=0;i<api.size();i++){
                JSONObject tmp=(JSONObject)api.get(i);
                Api api1=new Api();
                String url= (String)tmp.get("method")+" "+(String)tmp.get("url");
                api1.setUrl(apiToRegex(url));
                JSONArray scopes=(JSONArray)tmp.get("scopes");
                List<String> list=new ArrayList<String>();
                for (Object item: scopes){
                    list.add(item.toString());
                }
                api1.setScopes(list);
                listMap.put(api1.getUrl(),api1.getScopes());
            }
            AppConfig.appConfig=listMap;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public static void loadApi(FileReader fileReader){
        JSONParser parser = new JSONParser();
        Object obj;
        try {
            obj = parser.parse(fileReader);
            JSONObject jsonObject =  (JSONObject) obj;
            AppConfig.setUrl_server((String) ((JSONObject) obj).get("url_server"));
            JSONArray api = (JSONArray) jsonObject.get("api");
            HashMap<String,List<String >> listMap=new HashMap<String, List<String>>();
            for(int i=0;i<api.size();i++){
                JSONObject tmp=(JSONObject)api.get(i);
                Api api1=new Api();
                String url= (String)tmp.get("method")+" "+(String)tmp.get("url");
                api1.setUrl(apiToRegex(url));
                JSONArray scopes=(JSONArray)tmp.get("scopes");
                List<String> list=new ArrayList<String>();
                for (Object item: scopes){
                    list.add(item.toString());
                }
                api1.setScopes(list);
                listMap.put(api1.getUrl(),api1.getScopes());
            }
            AppConfig.appConfig=listMap;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public static String apiToRegex(String api) {
        return "^" + api.replaceAll("\\s{1,}", " ").trim().replaceAll("/", "\\\\/").replaceAll("\\{([^\\/]*)\\}", "([^\\\\/]?)") + "$";
    }
}
