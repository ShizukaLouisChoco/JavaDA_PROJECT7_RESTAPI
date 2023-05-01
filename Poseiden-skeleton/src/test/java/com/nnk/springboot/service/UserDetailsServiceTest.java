package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.Impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserDetailsServiceTest {

    @Mock
    UserRepository userRepository;

    private UserDetailsServiceImpl userDetailsService;


    @BeforeEach
    public void setup() {
        this.userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    @DisplayName("loadUserByUsername if UserAccountExists ShouldReturnUserDetails")
    public void testLoadUserByUsername() {
        // GIVEN
        User user = new User("fullname","username","USER","password");

        // WHEN
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        // THEN
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        assertThat(userDetails.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());
        assertThat(userDetails.getAuthorities()).hasSize(1);
    }

    @Test
    @DisplayName("loadUserByUsername if UserAccountExists ShouldReturnUserDetails")
    public void testLoadAdminByUsername() {
        // GIVEN
        User user = new User("fullname","username","ADMIN","password");

        // WHEN
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        // THEN
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        assertThat(userDetails.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());
        assertThat(userDetails.getAuthorities()).hasSize(1);
    }

    @Test
    @DisplayName("When a not found user tried to connection, then throw UserAccountNotFoundException")
    public void testLoadUserByUsernameException() {
        //WHEN
        when(userRepository.findByUsername("username1")).thenReturn(java.util.Optional.empty());
        //THEN
        assertThrows(UsernameNotFoundException.class , () -> userDetailsService.loadUserByUsername("notExisting@example.com"));
    }

}
