package com.training.trainingblogapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users", schema = "public")
@EqualsAndHashCode(exclude = {"posts", "comments"})
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TEXT")
    private String username;

    @Column(name = "first_name",columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name",columnDefinition = "TEXT")
    private String lastName;

    @Column(columnDefinition = "TEXT")
    private String password;
    private String email;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new HashSet<>();
}
