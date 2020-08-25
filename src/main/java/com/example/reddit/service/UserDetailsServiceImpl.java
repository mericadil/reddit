package com.example.reddit.service;

import com.example.reddit.entity.User;
import com.example.reddit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if( optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new org.springframework.security
                    .core.userdetails.User(user.getUsername(), user.getPassword(),
                    user.isEnabled(), true, true,
                    true, getAuthorities("USER"));
        }
        else
            throw new UsernameNotFoundException("User with " + username + "does not exist!");
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
