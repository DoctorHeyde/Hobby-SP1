package app.DTO;

import app.model.Hobby;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class NumberOfPersonsPerHobbyDTO {

    Hobby hobby;
    long numberOfPersons;

    public NumberOfPersonsPerHobbyDTO(Hobby hobby, long numberOfPersons) {
        this.hobby = hobby;
        this.numberOfPersons = numberOfPersons;
    }
}
