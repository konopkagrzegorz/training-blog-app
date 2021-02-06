package com.training.trainingblogapp.domain.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages", schema = "public")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String subject;
    @Column(name = "contact_email")
    private String contactEmail;
    private String text;
    private boolean status;
}
