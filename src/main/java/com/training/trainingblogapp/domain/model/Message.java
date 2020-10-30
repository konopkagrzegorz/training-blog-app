package com.training.trainingblogapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
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

    public boolean getStatus() {
        return this.status;
    }
}
