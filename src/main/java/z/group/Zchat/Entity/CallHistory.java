package z.group.Zchat.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class CallHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String fromUsername;
    private String toUsername;
    private String status;
    private Date date;
    private String fromEmail;
    private String toEmail;
    public CallHistory(){}

    public CallHistory(int id,String fromUsername, String toUsername, String status, Date date,String fromEmail,String toEmail) {
        this.id = id;
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
        this.status = status;
        this.date = date;
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
