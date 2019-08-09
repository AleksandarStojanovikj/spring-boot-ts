package com.endava.twittersimulation.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Username cannot be blank.")
    private String username;

    private String password;

    @Column(unique = true)
    @Email(message = "Must have a valid email format.")
    private String email;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<Tweet> tweets;

    public User() {
    }

    public User(String username, String password, String email, List<Tweet> tweets) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.tweets = tweets;
    }
}
