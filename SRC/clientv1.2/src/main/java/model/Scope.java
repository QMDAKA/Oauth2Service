package model;

/**
 * Created by Quang Minh on 5/11/2016.
 */
public class Scope {
    private String scope;
    private String description;
    private String cc_expires_in;
    private String pass_expires_in;
    private String refresh_expires_in;

    public Scope(String scope, String description, String cc_expires_in, String pass_expires_in, String refresh_expires_in) {
        this.scope = scope;
        this.description = description;
        this.cc_expires_in = cc_expires_in;
        this.pass_expires_in = pass_expires_in;
        this.refresh_expires_in = refresh_expires_in;
    }

    public Scope() {
    }

    public String getScope() {

        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCc_expires_in() {
        return cc_expires_in;
    }

    public void setCc_expires_in(String cc_expires_in) {
        this.cc_expires_in = cc_expires_in;
    }

    public String getPass_expires_in() {
        return pass_expires_in;
    }

    public void setPass_expires_in(String pass_expires_in) {
        this.pass_expires_in = pass_expires_in;
    }

    public String getRefresh_expires_in() {
        return refresh_expires_in;
    }

    public void setRefresh_expires_in(String refresh_expires_in) {
        this.refresh_expires_in = refresh_expires_in;
    }
}
