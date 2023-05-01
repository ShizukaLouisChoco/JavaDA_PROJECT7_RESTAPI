package com.nnk.springboot.controller;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidListControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping list")
    public void getMappingHomeTest() throws Exception {
        //GIVEN
        final String url = "/bidList/list";

        //WHEN
        final var response = mockMvc.perform(get(url)
                 .with(csrf()))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidList"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping add")
    public void getMappingAddTest() throws Exception {
        //GIVEN
        final String url = "/bidList/add";

        //WHEN
        final var response = mockMvc.perform(get(url)
                .with(csrf()))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bidList"));
    }

    @WithMockUser(username = "username")
    @DisplayName("bidList/add can add ")
    @Test
    public void postMappingAddTest() throws Exception {
        //GIVEN
        final String url = "/bidList/validate";
        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("account", "user")
                .param("type", "type")
                .param("bidQuantity","10"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @WithMockUser(username = "username")
    @DisplayName("bidList/validate with validation error")
    @Test
    public void testBidListFromWithValidationError() throws Exception {
        //GIVEN
        final String url = "/bidList/validate";

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("account", " ")
                .param("type", "type")
                .param("bidQuantity","10"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping update")
    public void getMappingUpdateTest() throws Exception {
        //GIVEN
        final BidList existingBidList = createBidListOnDatabase();
        final Integer id = existingBidList.getId();
        final String url = "/bidList/update/" + id;

        //WHEN
        final var response = mockMvc.perform(get(url)
                .with(csrf())
                .param("id","1")
                .param("account", "account")
                .param("type", "type")
                .param("bidQuantity","10"));


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));
    }

    @WithMockUser(username = "username")
    @DisplayName("bidList/update can update ")
    @Test
    public void postMappingUpdateTest() throws Exception {
        //GIVEN an existing bidList
        final BidList existingBidList = createBidListOnDatabase();
        final Integer id = existingBidList.getId();
        final String url = "/bidList/update/" + id;

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("account", "updated account")
                .param("type", "type")
                .param("bidQuantity","10"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @WithMockUser(username = "username")
    @DisplayName("/bidlist/update with validation error")
    @Test
    public void testBidListUpdateWithValidationError() throws Exception {
        //GIVEN an existing bidList
        final BidList existingBidList = createBidListOnDatabase();
        final Integer id = existingBidList.getId();
        final String url = "/bidList/update/" + id;

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("account", "")
                .param("type", "type")
                .param("bidQuantity", "10"));


        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }

    @WithMockUser(username = "username")
    @DisplayName("bidList/update with exception ")
    @Test
    public void testBidListUpdateWithException() throws Exception {
        //GIVEN
        final String url = "/bidList/update/100";
        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("account", "updated account")
                .param("type", "type")
                .param("bidQuantity","10"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping delete")
    public void getMappingDeleteTest() throws Exception {
        //GIVEN
        final BidList existingBidList = createBidListOnDatabase();
        final Integer id = existingBidList.getId();
        final String url = "/bidList/delete/" + id;

        //WHEN
        final var response = mockMvc.perform(get(url)
                        .with(csrf())
                        .param("id",id.toString()))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    private BidList createBidListOnDatabase(){
        return  bidListRepository.save(new BidList ("account", "type", 10.));
    }

}
