package com.rkecom.db.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name="address_id")
    private Long addressId;

    @Column (nullable = false, length = 50)
    private String street;

    @Column (name = "building", nullable = false, length = 50)
    private String building;

    @Column (name = "city", nullable = false, length = 50)
    private String city;

    @Column (name = "state", nullable = false, length = 50)
    private String state;

    @Column (name = "pincode", nullable = false, length = 10)
    private String pincode;

    @Column (name = "country", nullable = false, length = 50)
    private String country;

    @ManyToOne
    @JoinColumn ( name = "user_id", nullable = false)
    private User user;


}
