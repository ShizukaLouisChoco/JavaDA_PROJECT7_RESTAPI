package com.nnk.springboot.controller;

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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ErrorControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(username = "username")
    @Test
    @DisplayName("/error displays page 403")
    public void testError403() throws Exception {
        //GIVEN
        final String url = "/error";

        //WHEN
        final var response = mockMvc.perform(get(url)
                        .with(csrf())
                .requestAttr("javax.servlet.error.status_code", 403))
                .andDo(MockMvcResultHandlers.print());
        //THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errorStatus"))
                .andExpect(view().name("error"));
    }
}
