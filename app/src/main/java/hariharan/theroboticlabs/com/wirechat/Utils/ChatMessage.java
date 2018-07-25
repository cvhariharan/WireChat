package hariharan.theroboticlabs.com.wirechat.Utils;

/**
 * Created by hariharan on 7/24/18.
 */

public class ChatMessage {
    private String message;
    private String from;
    private String to;

    public ChatMessage(String message, String from, String to) {
        this.message = message;
        this.from = from;
        this.to = to;
    }

    public ChatMessage() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
