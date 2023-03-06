package aksoftdev.billitest.api;

import aksoftdev.billitest.service.AuthInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Authentication API", description = "The authentication API (for authentication)")
public class AuthInfoController {

    private final AuthInfoService authInfoService;


}
