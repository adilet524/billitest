package aksoftdev.billitest.config.security;

import aksoftdev.billitest.exceptions.NotFoundException;
import aksoftdev.billitest.repository.AuthInfoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthInfoRepository authInfoRepository;

    public UserDetailsServiceImpl(AuthInfoRepository authInfoRepository) {
        this.authInfoRepository = authInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return authInfoRepository.findByEmail(username).orElseThrow(
                () -> new NotFoundException(String.format("Password or email not found!")));
    }
}