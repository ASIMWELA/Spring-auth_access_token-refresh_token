package com.jwt.refresh.token.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jwt.refresh.token.role.Role;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Column(name="first_name", nullable = false, length = 90)
    String firstName;
    @Column(name = "last_name", nullable = false, length = 90)
    String lastName;
    @Column(name="user_name", nullable = false, length = 90)
    String userName;
    @Column(name="password", nullable = false, length = 200)
    @JsonIgnore
    String password;

    @Column(name="is_account_enabled")
    boolean enabled;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(
            name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    List<Role> roles = new ArrayList<>();

    public User(String firstName, String lastName, String userName, String password, boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
    }
}
