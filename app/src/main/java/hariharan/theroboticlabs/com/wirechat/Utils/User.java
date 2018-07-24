package hariharan.theroboticlabs.com.wirechat.Utils;

/**
 * Created by hariharan on 7/23/18.
 */

public class User {
    private String name;
    private String uid;

    public User() {

    }

    public User(String name, String uid) {
        this.name = name;
        this.uid = uid;
    }

    public User(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
