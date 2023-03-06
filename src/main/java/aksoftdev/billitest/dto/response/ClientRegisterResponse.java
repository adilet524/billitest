package aksoftdev.billitest.dto.response;

import aksoftdev.billitest.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientRegisterResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String token;

    private Role role;
}
