package app.DTO;

import app.model.Hobby;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class NumberOfUsersPerHobbyDTO {

    Hobby hobby;
    long numberOfUsers;

    public NumberOfUsersPerHobbyDTO(Hobby hobby, long numberOfUsers) {
        this.hobby = hobby;
        this.numberOfUsers = numberOfUsers;
    }
}
