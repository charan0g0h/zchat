package z.group.Zchat.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Request {
    @Id
    int id;
    int from;
    int to;
    public Request(){}

    public Request(int from, int id, int to) {
        this.from = from;
        this.id = id;
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
