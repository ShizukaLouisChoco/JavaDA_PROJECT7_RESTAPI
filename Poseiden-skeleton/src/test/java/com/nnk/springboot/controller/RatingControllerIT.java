package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
public class RatingControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping list")
    public void getMappingHomeTest() throws Exception {
        //GIVEN
        final String url = "/rating/list";

        //WHEN
        final var response = mockMvc.perform(get(url))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratingList"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping add")
    public void getMappingAddTest() throws Exception {
        //GIVEN
        final String url = "/rating/add";

        //WHEN
        final var response = mockMvc.perform(get(url))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("rating"));
    }

    @WithMockUser(username = "username")
    @DisplayName("rating/add can add ")
    @Test
    public void postMappingAddTest() throws Exception {
        //GIVEN
        final String url = "/rating/validate";
        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("moodysRating", "moodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating","fitchRating")
                .param("orderNumber","1"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @WithMockUser(username = "username")
    @DisplayName("rating/validate with validation error")
    @Test
    public void testBidListFromWithValidationError() throws Exception {
        //GIVEN
        final String url = "/rating/validate";

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("moodysRating", " ")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber","1"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping update")
    public void getMappingUpdateTest() throws Exception {
        //GIVEN
        final Rating existingRating = createRatingOnDatabase();
        final Integer id = existingRating.getId();
        final String url = "/rating/update/" + id;

        //WHEN
        final var response = mockMvc.perform(get(url)
                .with(csrf())
                .param("moodysRating","moodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber","1"));


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"));
    }

    @WithMockUser(username = "username")
    @DisplayName("bidList/update can update ")
    @Test
    public void postMappingUpdateTest() throws Exception {
        //GIVEN an existing bidList
        final Rating existingRating = createRatingOnDatabase();
        final Integer id = existingRating.getId();
        final String url = "/rating/update/" + id;

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("moodysRating", "moodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber","1"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @WithMockUser(username = "username")
    @DisplayName("/rating/update with validation error")
    @Test
    public void testRatingUpdateWithValidationError() throws Exception {
        //GIVEN an existing bidList
        final Rating existingRating = createRatingOnDatabase();
        final Integer id = existingRating.getId();
        final String url = "/rating/update/" + id;

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("moodysRating", " ")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "1"));


        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @WithMockUser(username = "username")
    @DisplayName("rating/update with exception ")
    @Test
    public void testRatingUpdateWithException() throws Exception {
        //GIVEN
        final String url = "/rating/update/100";
        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("moodysRating", "moodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber","1"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping delete")
    public void getMappingDeleteTest() throws Exception {
        //GIVEN
        final Rating existingRating = createRatingOnDatabase();
        final Integer id = existingRating.getId();
        final String url = "/rating/delete/" + id;

        //WHEN
        final var response = mockMvc.perform(get(url)
                        .with(csrf())
                        .param("id",id.toString()))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    private Rating createRatingOnDatabase(){
        return  ratingRepository.save(new Rating ("moodysRating", "sandPRating", "fitchRating",1));
    }

}
