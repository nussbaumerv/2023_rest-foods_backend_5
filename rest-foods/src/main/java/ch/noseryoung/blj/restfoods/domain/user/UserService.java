package ch.noseryoung.blj.restfoods.domain.user;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Log4j2
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
        log.info("A User has tried to login in the Server");
        return userRepository.findByUserEmail(useremail).map(UserDetailsImpl::new).orElseThrow(() -> new UsernameNotFoundException(useremail));
    }

    public record UserDetailsImpl(User user) implements UserDetails  {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return user.getRole().getAuthorities().stream()
                    .map(a -> new SimpleGrantedAuthority(a.getAuthorityName()))
                    .toList();
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getUserEmail();
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

        @Override
        public boolean equals(Object obj) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public String toString() {
            return null;
        }
    }
}
