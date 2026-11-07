package z.group.Zchat.Entity.Zaps;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Zap {
    @Id
            @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    String head;
    String username;
    @Column(columnDefinition = "TEXT")
    String content;
    Date date;
    int likes;
    int dislikes;
    @OneToMany(
            mappedBy = "zap",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    List<Comments> commentsList = new LinkedList<>();

    public Zap(int id, String head, String content) {
        this.id = id;
        this.head = head;
        this.content = content;
        this.date = new Date(System.currentTimeMillis());
        this.likes = 0;
        this.dislikes = 0;
    }

    public Zap(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
