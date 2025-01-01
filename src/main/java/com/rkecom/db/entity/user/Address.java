package com.rkecom.db.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column (nullable = false, length = 50)
    private String street;

    @Column (name = "BUILDING_NAME", nullable = false, length = 50)
    private String buildingName;

    @Column (name = "CITY", nullable = false, length = 50)
    private String city;

    @Column (name = "STATE", nullable = false, length = 50)
    private String state;

    @Column (name = "PINCODE", nullable = false, length = 10)
    private String pincode;

    @Column (name = "COUNTRY", nullable = false, length = 50)
    private String country;


}
