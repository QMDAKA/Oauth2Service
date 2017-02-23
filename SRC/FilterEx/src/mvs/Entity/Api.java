package mvs.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quang Minh on 5/16/2016.
 */
public class Api{
    private String url;
    private List<String> scopes;

    public Api() {
        scopes=new ArrayList<String>();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }
}
