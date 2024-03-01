package app.DTO;


import app.model.Hobby;
import app.model.ZipCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class UserDTO {
    private String name;
    private int phoneNumber;
    private ZipCode zipCode;
    private String streetName;
    private String floor;
    private int houseNumber;

    private List<Hobby> hobbies = new ArrayList<>();


    public UserDTO(String name, int phoneNumber, ZipCode zipCode, String streetName, String floor, int houseNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.streetName = streetName;
        this.floor = floor;
        this.houseNumber = houseNumber;

    }

}