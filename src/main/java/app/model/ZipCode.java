package app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "zipcode")
@NoArgsConstructor
public class ZipCode {

    @Id
    private int zip;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "municipality_name")
    private String municipalityName;

    @OneToMany
    private Set<User> users;

    public ZipCode(int zip, String cityName, String regionName, String municipalityName) {
        this.zip = zip;
        this.cityName = cityName;
        this.regionName = regionName;
        this.municipalityName = municipalityName;
        users = new HashSet<>();
    }

    public void addUser(User user){
        users.add(user);
    }
}