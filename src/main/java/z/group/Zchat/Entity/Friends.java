package z.group.Zchat.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    int followerId;
    int followingId;

    public Friends(){}

    public Friends(int id, int followingId, int followerId) {
        this.id = id;
        this.followingId = followingId;
        this.followerId = followerId;
    }

    public Friends(int followerId ,int followingId){
        this.followerId = followerId;
        this.followingId = followingId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getFollowingId() {
        return followingId;
    }

    public void setFollowingId(int followingId) {
        this.followingId = followingId;
    }
}
