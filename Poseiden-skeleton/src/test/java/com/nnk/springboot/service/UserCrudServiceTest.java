package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.Impl.UserCrudServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserCrudServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserCrudServiceImpl userCrudService;

    @Test
    @DisplayName("tradeCrudService calls tradeRepository create method")
    public void given_whenCreateUser_thenReturnUser() {
        //GIVEN
        User user = new User("fullname","username3","USER","password");
        //WHEN
        userCrudService.create(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("$2a$10$Nf4NPdHPIB2vfdt7WBb4/OrNj5BSHq.S6yfE7IuUmVjGzeFY.v/xG ");
        //THEN
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue())
                .isNotNull()
                .satisfies(arg -> {
                    assertThat(arg.getId()).isEqualTo(user.getId());
                });

    }

    @Test
    @DisplayName("tradeCrudService calls tradeRepository create method")
    public void given_whenCreateUserAdmin_thenReturnUserAdmin() {
        //GIVEN
        User user = new User("fullname","username3","ADMIN","password");
        //WHEN
        userCrudService.create(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("$2a$10$Nf4NPdHPIB2vfdt7WBb4/OrNj5BSHq.S6yfE7IuUmVjGzeFY.v/xG ");
        //THEN
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue())
                .isNotNull()
                .satisfies(arg -> {
                    assertThat(arg.getId()).isEqualTo(user.getId());
                });

    }
}
