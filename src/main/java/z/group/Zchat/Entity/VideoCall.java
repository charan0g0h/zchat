package z.group.Zchat.Entity;

import java.util.Date;

public class VideoCall {
    private String callerId;
    private String fromUsername;
    private String toUsername;
    private Date callDate;
    private String mode;


    public VideoCall(String fromUsername, String toUsername,String mode, String callerId, Date callDate ) {
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
        this.callerId = callerId;
        this.callDate = callDate;
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getCallerId() {
        return callerId;
    }

    public void setCallerId(String callerId) {
        this.callerId = callerId;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }
}
