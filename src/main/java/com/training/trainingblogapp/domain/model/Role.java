package com.training.trainingblogapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy="role", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<User> users;

    public Role(String role_user) {
        this.name = role_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addUser(User user) {
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        this.users.add(user);
    }
}
