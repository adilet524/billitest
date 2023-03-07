package aksoftdev.billitest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Valid
public class ClientRegisterRequest {

    @NotNull(message = "First name should be not null")
    @Size(min = 2,max = 30)
    private String firstName;

    @NotNull(message = "Last name should be not null")
    @Size(min = 2,max = 30)
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email should not be empty")
    private String email;

    @NotBlank
//    @PasswordValid
    private String password;
}
