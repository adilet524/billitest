package aksoftdev.billitest.service;

import aksoftdev.billitest.config.security.jwt.JwtUtils;
import aksoftdev.billitest.dto.request.AuthInfoRequest;
import aksoftdev.billitest.dto.request.ClientRegisterRequest;
import aksoftdev.billitest.dto.response.AuthInfoResponse;
import aksoftdev.billitest.dto.response.ClientRegisterResponse;
import aksoftdev.billitest.entities.AuthInfo;
import aksoftdev.billitest.entities.Client;
import aksoftdev.billitest.entities.enums.Role;
import aksoftdev.billitest.exceptions.BadCredentialsException;
import aksoftdev.billitest.exceptions.BadRequestException;
import aksoftdev.billitest.repository.AuthInfoRepository;
import aksoftdev.billitest.repository.ClientRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthInfoService {

    private final AuthInfoRepository authInfoRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder;


    public AuthInfoResponse login(AuthInfoRequest authInfoRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authInfoRequest.getEmail(),
                        authInfoRequest.getPassword()));

        AuthInfo authInfo = authInfoRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadCredentialsException("Password or email not found!"));

        if (authInfoRequest.getPassword().isBlank()) {
            throw new BadRequestException("Write password!");
        }

        if (!passwordEncoder.matches(authInfoRequest.getPassword(), authInfo.getPassword())) {
            throw new BadCredentialsException("Password or email not found!");
        }

        String token = jwtUtils.generateToken(authInfo.getEmail());
        return new AuthInfoResponse(authInfo.getUsername(), token, authInfo.getRole());
    }

    public ClientRegisterResponse register(ClientRegisterRequest clientRegisterRequest) {

        if (clientRegisterRequest.getPassword().isBlank()) {
            throw new BadRequestException("Password cannot be empty!");
        }

        if (authInfoRepository.existsAuthInfoByEmail(clientRegisterRequest.getEmail())) {
            throw new BadRequestException("This email: " +
                    clientRegisterRequest.getEmail() + " is not empty!");
        }

        clientRegisterRequest.setPassword(passwordEncoder.encode(clientRegisterRequest.getPassword()));

        Client client = new Client(clientRegisterRequest);

        Client saveClient = clientRepository.save(client);

        String token = jwtUtils.generateToken(saveClient.getAuthInfo().getEmail());

        return new ClientRegisterResponse(
                saveClient.getFirstName(),
                saveClient.getLastName(),
                saveClient.getAuthInfo().getEmail(),
                token,
                saveClient.getAuthInfo().getRole());
    }

    public AuthInfoResponse authWithGoogleAccount(String tokenId) throws FirebaseAuthException {

        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);

        Client client;

        if (!authInfoRepository.existsAuthInfoByEmail(firebaseToken.getEmail())) {

            Client newClient = new Client();

            String[] name = firebaseToken.getName().split(" ");

            newClient.setFirstName(name[0]);

            newClient.setLastName(name[1]);

            newClient.setAuthInfo(new AuthInfo(firebaseToken.getEmail(), firebaseToken.getEmail(), Role.CLIENT));

            clientRepository.save(newClient);
        }

        client = clientRepository.findClientByAuthInfoEmail(firebaseToken.getEmail());

        return new AuthInfoResponse(client.getAuthInfo().getEmail(),
                jwtUtils.generateToken(client.getAuthInfo().getEmail()),
                client.getAuthInfo().getRole());
    }
}
