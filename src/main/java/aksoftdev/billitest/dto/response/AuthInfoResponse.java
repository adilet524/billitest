package aksoftdev.billitest.dto.response;

import aksoftdev.billitest.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthInfoResponse {

    private String email;

    private String token;

    private Role role;
}
