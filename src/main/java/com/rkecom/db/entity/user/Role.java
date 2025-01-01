package com.rkecom.db.entity.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ROLE")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name ="ROLE_ID")
    @Setter( AccessLevel.NONE)
    private Integer roleId;

    @Column(name = "ROLE_NAME", unique = true, nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RoleType roleName;

}