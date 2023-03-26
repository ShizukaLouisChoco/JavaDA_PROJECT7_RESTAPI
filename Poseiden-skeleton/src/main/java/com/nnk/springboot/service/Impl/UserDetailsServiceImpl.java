package com.nnk.springboot.service.Impl;

import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.ConnectedUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, ConnectedUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .map(user -> new User(
                        user.getUsername(),
                        user.getPassword(),
                        AuthorityUtils.createAuthorityList("USER")
                ))
                .orElseThrow(()-> new UsernameNotFoundException("Usename not found"));
    }

    @Transactional
    @Override
    public com.nnk.springboot.domain.User getConnectedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getName() != null){
            return userRepository.findByUsername(authentication.getName())
                    .orElseThrow(()-> new UsernameNotFoundException("Usename not found"));
        }

        throw new AuthenticationCredentialsNotFoundException("User not connected");
    }
}
