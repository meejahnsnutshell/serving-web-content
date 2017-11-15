package hello.model;

import javax.persistence.*;
import java.util.Set;

// Hibernate tags - build a java persistent table using tags, mapped to our user/role db
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="role_id")
    private int role_id;
    @Column(name="role")
    private String role;

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
