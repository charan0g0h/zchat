package z.group.Zchat.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    String fromUsername;
    String toUsername;
    Date sentDate;
    @Column(
            length = 255
    )
    String message;
    public Message(){}

    public Message(String fromUsername, Integer id, String toUsername, Date sentDate, String message) {
        this.fromUsername = fromUsername;
        this.id = id;
        this.toUsername = toUsername;
        this.sentDate = sentDate;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }
}
