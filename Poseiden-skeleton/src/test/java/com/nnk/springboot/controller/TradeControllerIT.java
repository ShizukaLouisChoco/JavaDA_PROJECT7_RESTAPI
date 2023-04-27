package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeRepository tradeRepository;

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping list")
    public void getMappingHomeTest() throws Exception {
        //GIVEN
        final String url = "/trade/list";

        //WHEN
        final var response = mockMvc.perform(get(url))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("tradeList"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping add")
    public void getMappingAddTest() throws Exception {
        //GIVEN
        final String url = "/trade/add";

        //WHEN
        final var response = mockMvc.perform(get(url)
                .with(csrf()))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("trade"));
    }

    @WithMockUser(username = "username")
    @DisplayName("trade/add can add ")
    @Test
    public void postMappingAddTest() throws Exception {
        //GIVEN
        final String url = "/trade/validate";
        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("account", "account")
                .param("type", "type")
                .param("buyQuantity","10"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @WithMockUser(username = "username")
    @DisplayName("trade/validate with validation error")
    @Test
    public void testTradeFromWithValidationError() throws Exception {
        //GIVEN
        final String url = "/trade/validate";

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("account", " ")
                .param("type", "type")
                .param("buyQuantity","1"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping update")
    public void getMappingUpdateTest() throws Exception {
        //GIVEN
        final Trade existingTrade = createTradeOnDatabase();
        final Integer id = existingTrade.getId();
        final String url = "/trade/update/" + id;

        //WHEN
        final var response = mockMvc.perform(get(url)
                .with(csrf())
                .param("id","1")
                .param("account", "account")
                .param("type", "type")
                .param("bidQuantity","10"));


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"));
    }

    @WithMockUser(username = "username")
    @DisplayName("trade/update can update ")
    @Test
    public void postMappingUpdateTest() throws Exception {
        //GIVEN
        final Trade existingTrade = createTradeOnDatabase();
        final Integer id = existingTrade.getId();
        final String url = "/trade/update/" + id;

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("account", "updated account")
                .param("type", "type")
                .param("buyQuantity","10"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @WithMockUser(username = "username")
    @DisplayName("/trade/update with validation error")
    @Test
    public void testBidListUpdateWithValidationError() throws Exception {
        //GIVEN an existing bidList
        final Trade existingTrade = createTradeOnDatabase();
        final Integer id = existingTrade.getId();
        final String url = "/trade/update/"+id;

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("account", "")
                .param("type", "type")
                .param("buyQuantity", "1"));


        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"));
    }

    @WithMockUser(username = "username")
    @DisplayName("trade/update with exception ")
    @Test
    public void testTradeUpdateWithException() throws Exception {
        //GIVEN
        final String url = "/trade/update/100";
        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("account", "updated account")
                .param("type", "type")
                .param("buyQuantity","10"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping delete")
    public void getMappingDeleteTest() throws Exception {
        //GIVEN
        final Trade existingTrade = createTradeOnDatabase();
        final Integer id = existingTrade.getId();
        final String url = "/trade/delete/" + id;

        //WHEN
        final var response = mockMvc.perform(get(url)
                        .with(csrf())
                        .param("id",id.toString()))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    private Trade createTradeOnDatabase(){
        return  tradeRepository.save(new Trade ("account", "type", 1.));
    }

}
