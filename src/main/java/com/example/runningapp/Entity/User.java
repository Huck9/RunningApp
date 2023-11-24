package com.example.runningapp.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    private boolean active;

    private String roles = "";

    @OneToMany(mappedBy = "user")
    private List<Training> trainings = new ArrayList<>();

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return List.of(this.roles.split(","));
        } else return List.of();
    }
}
