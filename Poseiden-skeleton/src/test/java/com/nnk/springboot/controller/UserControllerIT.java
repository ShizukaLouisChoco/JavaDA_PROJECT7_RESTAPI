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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping list")
    public void getMappingHomeTest() throws Exception {
        //GIVEN
        final String url = "/user/list";

        //WHEN
        final var response = mockMvc.perform(get(url))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("userList"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping add")
    public void getMappingAddTest() throws Exception {
        //GIVEN
        final String url = "/user/add";

        //WHEN
        final var response = mockMvc.perform(get(url))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("user"));
    }

    @WithMockUser(username = "username")
    @DisplayName("user/add can add ")
    @Test
    public void postMappingAddTest() throws Exception {
        //GIVEN
        final String url = "/user/validate";
        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("fullname", "fullname")
                .param("username", "username")
                .param("password", "Password1!")
                .param("role","role"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @WithMockUser(username = "username")
    @DisplayName("user/validate with validation error")
    @Test
    public void testUserFromWithValidationError() throws Exception {
        //GIVEN
        final String url = "/user/validate";

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("fullname", "")
                .param("username", "username")
                .param("password", "Password1!")
                .param("role","role"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }
    @WithMockUser(username = "username")
    @DisplayName("user/validate with validation error")
    @Test
    public void testUserFromWithException() throws Exception {
        //GIVEN
        final User existingUser = createUserOnDatabase();
        final String url = "/user/validate";

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("fullname", "fullname")
                .param("username", "username")
                .param("password", "Password1!")
                .param("role","role"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("errorMsg"));
    }
    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping update")
    public void getMappingUpdateTest() throws Exception {
        //GIVEN
        final User existingUser = createUserOnDatabase();
        final Integer id = existingUser.getId();
        final String url = "/user/update/" + id;

        //WHEN
        final var response = mockMvc.perform(get(url)
                .with(csrf())
                .param("id","1")
                .param("fullname", "user")
                .param("username", "type")
                .param("role","10"));


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"));
    }

    @WithMockUser(username = "username")
    @DisplayName("user/update can update ")
    @Test
    public void postMappingUpdateTest() throws Exception {
        //GIVEN an existing bidList
        final User existingUser = createUserOnDatabase();
        final Integer id = existingUser.getId();
        final String url = "/user/update/" + id;

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("fullname", "fullname")
                .param("username", "username")
                .param("password", "Password1!")
                .param("role","role"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @WithMockUser(username = "username")
    @DisplayName("/user/update with validation error")
    @Test
    public void testBidListUpdateWithValidationError() throws Exception {
        //GIVEN an existing bidList
        final User existingUser = createUserOnDatabase();
        final Integer id = existingUser.getId();
        final String url = "/user/update/" + id;

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("fullname", "")
                .param("username", "username")
                .param("password", "Password1!")
                .param("role","role"));


        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"));
    }

    @WithMockUser(username = "username")
    @DisplayName("user/update with exception ")
    @Test
    public void testUserUpdateWithException() throws Exception {
        //GIVEN
        final User existingUser = createUserOnDatabase();
        final Integer id = existingUser.getId();
        final String url = "/user/update/" + id;
        userRepository.save(new User ("fullname", "username2", "role","Password1!"));

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("fullname", "fullname")
                .param("username", "username2")
                .param("password", "Password1!")
                .param("role","role"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("errorMsg"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping delete")
    public void getMappingDeleteTest() throws Exception {
        //GIVEN
        final User existingUser = createUserOnDatabase();
        final Integer id = existingUser.getId();
        final String url = "/user/delete/" + id;

        //WHEN
        final var response = mockMvc.perform(get(url)
                        .with(csrf())
                        .param("id",id.toString()))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    private User createUserOnDatabase(){
        return  userRepository.save(new User ("fullname", "username", "role","Password1!"));
    }

}
