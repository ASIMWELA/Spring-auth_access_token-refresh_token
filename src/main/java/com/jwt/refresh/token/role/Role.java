package com.jwt.refresh.token.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name="role")
    ERole roleName;

}
