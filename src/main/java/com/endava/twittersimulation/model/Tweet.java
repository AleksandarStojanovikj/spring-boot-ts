package com.endava.twittersimulation.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tweets")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Content cannot be empty.")
    private String content;

    private LocalDateTime dateOfCreation;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Long userId;

    public Tweet() {
    }

    public Tweet(String content, LocalDateTime dateOfCreation, Long userId) {
        this.content = content;
        this.dateOfCreation = dateOfCreation;
        this.userId = userId;
    }
}
