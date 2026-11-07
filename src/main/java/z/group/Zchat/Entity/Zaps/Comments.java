package z.group.Zchat.Entity.Zaps;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Comments {
    @Id
            @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String commenter;
    String comment;

    Date date;
    int likes;
    int dislikes;

    @ManyToOne
            @JoinColumn(name = "zap_id")
            @JsonBackReference
    Zap zap;

    public Comments(Zap zap, int id, String commenter, String comment, Date date) {
        this.zap = zap;
        this.id = id;
        this.commenter = commenter;
        this.comment = comment;
        this.date = date;
        this.likes = 0;
        this.dislikes = 0;
    }

    public Comments(){}

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public Zap getZap() {
        return zap;
    }

    public void setZap(Zap zap) {
        this.zap = zap;
    }
}
