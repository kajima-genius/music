package com.example.music.backend.verification;

import com.example.music.backend.user.domain.Role;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.repository.UserRepository;
import com.example.music.backend.user.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class MyUserDetailsService implements UserDetailsService {

    private UserService userService;
    private UserRepository userRepository;
    private final List<Role> ROLES = List.of(Role.USER,Role.AUTHOR,Role.ADMIN);

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        boolean enabled = true;
        boolean accountNotExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNotLocked = true;

        try {
            User user = userRepository.findByEmail(email).
                    orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + email));

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword().toLowerCase(Locale.ROOT),
                    user.isEnabled(),
                    accountNotExpired,
                    credentialsNonExpired,
                    accountNotLocked,
                    getGrantedAuthorities(ROLES)
            );
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role : roles)
            authorities.add(new SimpleGrantedAuthority(role.name()));

        return authorities;
    }
}
