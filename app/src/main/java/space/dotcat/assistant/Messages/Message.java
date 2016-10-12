package space.dotcat.assistant.Messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("event")
    @Expose
    private String event;

    @SerializedName("body")
    @Expose
    private Body body;


    public Message(Body body) {

        this.type = "user_request";

        this.source = "android";

        this.event = "action_requested";

        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
