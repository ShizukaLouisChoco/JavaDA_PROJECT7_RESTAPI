package com.nnk.springboot.service.Impl;

import com.nnk.springboot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .map(user -> new User(
                        user.getUsername(),
                        user.getPassword(),
                        userOrAdmin(user.getRole())

                ))
                .orElseThrow(()-> new UsernameNotFoundException("Username not found"));
    }

    public List<GrantedAuthority> userOrAdmin(String role){
        if(role == "ADMIN"){
                    return    AuthorityUtils.createAuthorityList("ADMIN");
        }
          return  AuthorityUtils.createAuthorityList("USER");
    }
}
