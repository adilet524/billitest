package aksoftdev.billitest.entities;

import aksoftdev.billitest.dto.request.ClientRegisterRequest;
import aksoftdev.billitest.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(generator = "client_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "client_generator", sequenceName = "client_id_sequence", allocationSize = 1, initialValue = 2)
    private Long id;

    private String firstName;

    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    private AuthInfo authInfo;

    public Client(ClientRegisterRequest clientRegisterRequest) {
        this.firstName = clientRegisterRequest.getFirstName();
        this.lastName = clientRegisterRequest.getLastName();
        AuthInfo authInfo1 = new AuthInfo();
        authInfo1.setEmail(clientRegisterRequest.getEmail());
        authInfo1.setPassword(clientRegisterRequest.getPassword());
        authInfo1.setRole(Role.CLIENT);
        this.authInfo = authInfo1;
    }

    public Client(String firstName, String lastName, AuthInfo authInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.authInfo = authInfo;
    }
}
