package com.nnk.springboot.controller;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurveControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping list")
    public void getMappingHomeTest() throws Exception {
        //GIVEN
        final String url = "/curvePoint/list";

        //WHEN
        final var response = mockMvc.perform(get(url))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePointList"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping add")
    public void getMappingAddTest() throws Exception {
        //GIVEN
        final String url = "/curvePoint/add";

        //WHEN
        final var response = mockMvc.perform(get(url))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @WithMockUser(username = "username")
    @DisplayName("curvePoint/add can add ")
    @Test
    public void postMappingAddTest() throws Exception {
        //GIVEN
        final String url = "/curvePoint/validate";
        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("curveId", "1")
                .param("term", "1")
                .param("value","1"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @WithMockUser(username = "username")
    @DisplayName("curvePoint/validate with validation error")
    @Test
    public void testBidListFromWithValidationError() throws Exception {
        //GIVEN
        final String url = "/curvePoint/validate";

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("curveId", " ")
                .param("term", "1")
                .param("value","1"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping update")
    public void getMappingUpdateTest() throws Exception {
        //GIVEN
        final CurvePoint existingCurvePoint = createCurvePointOnDatabase();
        final Integer id = existingCurvePoint.getId();
        final String url = "/curvePoint/update/" + id;

        //WHEN
        final var response = mockMvc.perform(get(url)
                .with(csrf())
                .param("curveId", "1")
                .param("term", "1")
                .param("value","1"));


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curve"));
    }

    @WithMockUser(username = "username")
    @DisplayName("curvePoint/update can update ")
    @Test
    public void postMappingUpdateTest() throws Exception {
        //GIVEN an existing bidList
        final CurvePoint existingCurvePoint = createCurvePointOnDatabase();
        final Integer id = existingCurvePoint.getId();
        final String url = "/curvePoint/update/" + id;

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("curveId", "1")
                .param("term", "1")
                .param("value","1"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @WithMockUser(username = "username")
    @DisplayName("/curvePoint/update with validation error")
    @Test
    public void testCurvePointUpdateWithValidationError() throws Exception {
        //GIVEN an existing bidList
        final CurvePoint existingCurvePoint = createCurvePointOnDatabase();
        final Integer id = existingCurvePoint.getId();
        final String url = "/curvePoint/update/" + id;

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("curveId", "")
                .param("term", "1")
                .param("value", "1"));


        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

    @WithMockUser(username = "username")
    @DisplayName("curvePoint/update with exception ")
    @Test
    public void testCurvePointUpdateWithException() throws Exception {
        //GIVEN
        final String url = "/curvePoint/update/200";
        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("curveId", "1")
                .param("term", "1")
                .param("value","1"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping delete")
    public void getMappingDeleteTest() throws Exception {
        //GIVEN
        final CurvePoint existingCurvePoint = createCurvePointOnDatabase();
        final Integer id = existingCurvePoint.getId();
        final String url = "/curvePoint/delete/" + id;

        //WHEN
        final var response = mockMvc.perform(get(url)
                        .with(csrf())
                        .param("id",id.toString()))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    private CurvePoint createCurvePointOnDatabase(){
        return  curvePointRepository.save(new CurvePoint(1,1.,1.));
    }

}
