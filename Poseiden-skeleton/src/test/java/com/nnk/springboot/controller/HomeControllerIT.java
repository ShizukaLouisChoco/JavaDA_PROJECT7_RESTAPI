package com.nnk.springboot.controller;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.var;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class HomeControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @WithMockUser(username = "username", authorities = "USER")
    @Test
    @DisplayName("Test GetMapping home")
    public void homeTest() throws Exception {
        //GIVEN
        final String url = "/";

        //WHEN
        final var response = mockMvc.perform(get(url));


        //THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @WithMockUser(username = "username", authorities = "ADMIN")
    @Test
    @DisplayName("Test GetMapping home")
    public void homeTestAdmin() throws Exception {
        //GIVEN
        final String url = "/";

        //WHEN
        final var response = mockMvc.perform(get(url))
                .andDo(MockMvcResultHandlers.print());

        //THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/home"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping admin home")
    public void adminHomeTest() throws Exception {
        //GIVEN

        final String url = "/admin/home";

        //WHEN
        final var response = mockMvc.perform(get(url))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }
    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping login")
    public void loginTest() throws Exception {
        //GIVEN
        final String url = "/login";

        //WHEN
        final var response = mockMvc.perform(get(url))
                .andDo(MockMvcResultHandlers.print());

        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    private User createUserOnDatabase(){
        return  userRepository.save(new User("fullname", "username", "ADMIN","password"));
    }


}
