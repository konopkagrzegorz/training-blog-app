package com.training.trainingblogapp.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "posts")
@EqualsAndHashCode(exclude = {"comments", "user"})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String heading;

    @Lob
    private String text;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime date;

    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(
            name = "posts_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
