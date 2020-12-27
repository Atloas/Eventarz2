package com.agh.eventarz2;

import com.agh.eventarz2.model.User;
import com.agh.eventarz2.repositories.UserRepository;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class handles User data retrieval from the database for the purpose of logging in.
 */
@Service
public class EventarzUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Queries the database for a User with the specified username.
     *
     * @param username Username to check.
     * @return An Spring UserDetails object with the Users data.
     * @throws UsernameNotFoundException When the username doesn't match any User.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for username " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(), true, true,
                true, true,
                getAuthorities(user.getRoles())
        );
    }

    /**
     * Transforms the roles list from the User object into a list of Spring GrantedAuthority objects.
     *
     * @param roles A list of roles.
     * @return A list of GrantedAuthority objects.
     */
    private static List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
