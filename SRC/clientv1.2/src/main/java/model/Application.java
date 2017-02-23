package model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Quang Minh on 5/13/2016.
 */
public class Application {
    private String name;
    private String description;
    private String scope;
    private String redirect_uri;
    private String client_id;
    private String client_secret;
    private Map<String,String> application_details;

    public Application() {
        application_details=new HashMap<String, String>();
        client_id=RandomGenerator.generateRandomString();;
        client_secret=RandomGenerator.generateRandomString();;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public Map<String, String> getApplication_details() {
        return application_details;
    }

    public void setApplication_details(Map<String, String> application_details) {
        this.application_details = application_details;
    }
}
