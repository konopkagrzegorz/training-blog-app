package com.training.trainingblogapp.domain.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy="role", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<User> users;

}
