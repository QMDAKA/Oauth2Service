package mvs.model;

/**
 * Created by Quang Minh on 5/20/2016.
 */
public class Error
{
    String message;
    Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
