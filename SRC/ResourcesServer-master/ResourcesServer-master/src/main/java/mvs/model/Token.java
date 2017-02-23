package mvs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Quang Minh on 5/18/2016.
 */
@Entity
public class Token {
    @Id
    @GeneratedValue
    Long id;
    String token;
    String refeshToken;
    String type;
    String scope;
    int expiresIn;
    long created;
    long createdCache;
    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }


    public long getCreatedCache() {
        return createdCache;
    }

    public void setCreatedCache(long createdCache) {
        this.createdCache = createdCache;
    }

    public Token() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefeshToken() {
        return refeshToken;
    }

    public void setRefeshToken(String refeshToken) {
        this.refeshToken = refeshToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
