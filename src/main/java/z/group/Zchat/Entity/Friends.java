package z.group.Zchat.Entity;

import jakarta.persistence.*;
import z.group.Zchat.Records.FriendRequestStatus;

@Entity
public class Friends {
    String username;
    String friendUsername;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Enumerated(EnumType.STRING)
    FriendRequestStatus status;

    public Friends(){}

    public Friends(String username, String friendUsername, int id, FriendRequestStatus status) {
        this.username = username;
        this.friendUsername = friendUsername;
        this.id = id;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFriend_username() {
        return friendUsername;
    }

    public void setFriend_username(String friend_username) {
        this.friendUsername = friend_username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }
}
