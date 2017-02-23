package mvs.Entity;

/**
 * Created by Quang Minh on 6/3/2016.
 */
public class Response {
    int statusCode;
    Object Body;

    public Response() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getBody() {
        return Body;
    }

    public void setBody(Object body) {
        Body = body;
    }
}
