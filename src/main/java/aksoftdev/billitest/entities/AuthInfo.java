package aksoftdev.billitest.entities;

import aksoftdev.billitest.entities.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "auth_info")
@Data
@NoArgsConstructor
public class AuthInfo implements UserDetails {

    @Id
    @GeneratedValue(generator = "auth_info_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "auth_info_generator", sequenceName = "auth_info_id_sequence", allocationSize = 1, initialValue = 3)
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public AuthInfo(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
