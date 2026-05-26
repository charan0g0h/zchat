package z.group.Zchat.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int acc_id;
    @Column(
            unique = true
    )
    public String username;
    public String password;
    public String fullname;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    @Column(
            unique = true
    )
    public String email;
    public Date creationdate;

    public Account(){}

    public int getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(int acc_id) {
        this.acc_id = acc_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Account(int acc_id, String username, String fullname, String email,String password) {
        this.acc_id = acc_id;
        this.password = password;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.creationdate = new Date();
    }
}
