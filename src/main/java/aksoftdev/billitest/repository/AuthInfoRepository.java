package aksoftdev.billitest.repository;

import aksoftdev.billitest.entities.AuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthInfoRepository extends JpaRepository<AuthInfo, Long> {
}
