package reqres.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterBodyModel {
    private String email, password;
}
