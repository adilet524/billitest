package aksoftdev.billitest.repository;

import aksoftdev.billitest.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
