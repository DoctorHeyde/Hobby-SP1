package app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number")
    private int phoneNumber;

    @OneToMany
    private ZipCode zipCode;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "floor")
    private String floor;

    @Column(name = "house_number")
    private int houseNumber;

    @ManyToMany
    private static Set<Hobby> hobbies = new HashSet<>();

    public User(String name, int phoneNumber, ZipCode zipCode, String streetName, String floor, int houseNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.streetName = streetName;
        this.floor = floor;
        this.houseNumber = houseNumber;
    }

}