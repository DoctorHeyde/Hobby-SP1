package app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
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
}