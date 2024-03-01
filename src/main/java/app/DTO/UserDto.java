package app.DTO;

import app.model.Hobby;
import app.model.ZipCode;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link app.model.User}
 */
@ToString
@Value
public class UserDto implements Serializable {
    String name;
    int phoneNumber;
    ZipCode zipCode;
    String streetName;
    String floor;
    int houseNumber;
    Hobby hobby;


    public UserDto(String name, int phoneNumber, ZipCode zipCode, String streetName, String floor, int houseNumber, Hobby hobby){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.streetName = streetName;
        this.floor = floor;
        this.houseNumber = houseNumber;
        this.hobby = hobby;
    }
}

//Make a constructor without Set<Hobby> and then add Set<Hobby> with a setter to the set and add it to the constructor