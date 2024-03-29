package app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "hobbyuser")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "persisted_at")
    private LocalDateTime timeStamp;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number")
    private int phoneNumber;

    @ManyToOne
    private ZipCode zipCode;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "floor")
    private String floor;

    @Column(name = "house_number")
    private int houseNumber;

    @ToString.Exclude
    @ManyToMany(mappedBy = "users")
    private Set<Hobby> hobbies = new HashSet<>();

    public User(String name, int phoneNumber, ZipCode zipCode, String streetName, String floor, int houseNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.streetName = streetName;
        this.floor = floor;
        this.houseNumber = houseNumber;
    }

    public User(int id, String name, int phoneNumber, ZipCode zipCode, String streetName, String floor, int houseNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.streetName = streetName;
        this.floor = floor;
        this.houseNumber = houseNumber;
    }

    @PrePersist
    public void timeStamp () throws RuntimeException {

        LocalDateTime localDateTime = java.time.LocalDateTime.now();

        this.timeStamp = localDateTime;
        this.updatedAt = localDateTime;

    }

    @PreUpdate
    public void preUpdate() throws RuntimeException {
        LocalDateTime localDateTime = java.time.LocalDateTime.now();

        this.updatedAt = localDateTime;
    }

    public void addHobby(Hobby hobby){
        if (hobby != null) {
            hobby.addUser(this);
        }
        hobbies.add(hobby);
    }

    @Override
    public boolean equals(Object arg0) {
        if(arg0 == this) {
            return true;
        }
        if(!(arg0 instanceof User)){
            return false;
        }
        User other = (User)arg0;
        if(other.getId() == this.id){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phoneNumber, zipCode, streetName, floor, houseNumber, hobbies);
    }
}