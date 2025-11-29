package com.blog.blog_app.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    // Each user can have multiple roles
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
