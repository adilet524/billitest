package aksoftdev.billitest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthInfoRequest {

    private String email;

    private String password;
}
