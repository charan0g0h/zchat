package z.group.Zchat.Entity.Zaps;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class React {
    @Id
            @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    int zapId;
    String username;
    ReactType reactType;

    public React(){}

    public React(int id, int zapId, String username, ReactType reactType) {
        this.id = id;
        this.zapId = zapId;
        this.username = username;
        this.reactType = reactType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getZapId() {
        return zapId;
    }

    public void setZapId(int zapId) {
        this.zapId = zapId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ReactType getReactType() {
        return reactType;
    }

    public void setReactType(ReactType reactType) {
        this.reactType = reactType;
    }
}
