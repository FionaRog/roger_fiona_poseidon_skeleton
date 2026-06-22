package com.nnk.springboot;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Loads authenticated users and their authorities from the application database.
 */
@Service
public class CustomUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Creates the service used by Spring Security to retrieve application users.
     *
     * @param userRepository repository used to find users by username
     */
    public CustomUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads Spring Security user details from the stored application User.
     *
     * @param username the login username submitted during authentication
     * @return Spring Security user details containing credentials and authorities
     * @throws UsernameNotFoundException when no User exists for the given username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        String role = user.getRole().startsWith("ROLE_") ? user.getRole() : "ROLE_" + user.getRole();
        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(role));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);

    }
}
