package z.group.Zchat.Configurations;

public class Status {
    int code;
    String description;

    public int getCode() {
        return code;
    }
    public Status(){}

    public Status(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
