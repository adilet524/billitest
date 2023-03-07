package aksoftdev.billitest.api;

import aksoftdev.billitest.dto.request.AuthInfoRequest;
import aksoftdev.billitest.dto.request.ClientRegisterRequest;
import aksoftdev.billitest.dto.response.AuthInfoResponse;
import aksoftdev.billitest.dto.response.ClientRegisterResponse;
import aksoftdev.billitest.service.AuthInfoService;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Data
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Authentication API", description = "The authentication API (for authentication)")
public class AuthInfoController {

    private final AuthInfoService authInfoService;

    @PostMapping("/login")
    public AuthInfoResponse login(@RequestBody AuthInfoRequest authInfoRequest) {
        return authInfoService.login(authInfoRequest);
    }

    @Operation(summary = "Registration", description = "The endpoint for register user")
    @PostMapping("/register")
    public ClientRegisterResponse register(@RequestBody @Valid ClientRegisterRequest clientRegisterRequest) {
        return authInfoService.register(clientRegisterRequest);
    }

    @Operation(summary = "Authentication with google",description = "Authentication via Google using Firebase")
    @PostMapping("/authenticate/google")
    public AuthInfoResponse authWithGoogleAccount(String tokenId) throws FirebaseAuthException {
        return authInfoService.authWithGoogleAccount(tokenId);
    }
}
