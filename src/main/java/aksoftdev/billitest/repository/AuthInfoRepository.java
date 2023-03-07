package aksoftdev.billitest.repository;

import aksoftdev.billitest.entities.AuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthInfoRepository extends JpaRepository<AuthInfo, Long> {

    Optional<AuthInfo> findByEmail(String email);

    boolean existsAuthInfoByEmail(String email);
}
