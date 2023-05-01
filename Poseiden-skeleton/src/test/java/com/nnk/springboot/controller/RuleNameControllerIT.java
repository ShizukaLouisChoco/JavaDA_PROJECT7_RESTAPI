package com.nnk.springboot.controller;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RuleNameControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping list")
    public void getMappingHomeTest() throws Exception {
        //GIVEN
        final String url = "/ruleName/list";

        //WHEN
        final var response = mockMvc.perform(get(url))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNameList"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping add")
    public void getMappingAddTest() throws Exception {
        //GIVEN
        final String url = "/ruleName/add";

        //WHEN
        final var response = mockMvc.perform(get(url))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeExists("ruleName"));
    }

    @WithMockUser(username = "username")
    @DisplayName("ruleName/add can add ")
    @Test
    public void postMappingAddTest() throws Exception {
        //GIVEN
        final String url = "/ruleName/validate";
        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("name", "name")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sql")
                .param("sqlPart", "sqlpart"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @WithMockUser(username = "username")
    @DisplayName("ruleName/validate with validation error")
    @Test
    public void testBidListFromWithValidationError() throws Exception {
        //GIVEN
        final String url = "/ruleName/validate";

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("name", " ")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sql")
                .param("sqlPart", "sqlpart"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping update")
    public void getMappingUpdateTest() throws Exception {
        //GIVEN
        final RuleName existingRuleName = createRuleNameOnDatabase();
        final Integer id = existingRuleName.getId();
        final String url = "/ruleName/update/" + id;

        //WHEN
        final var response = mockMvc.perform(get(url)
                .with(csrf())
                .param("name", "user")
                .param("description", "type")
                .param("json", "type")
                .param("template", "type")
                .param("sqlStr", "type")
                .param("sqlPart", "type"));


        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"));
    }

    @WithMockUser(username = "username")
    @DisplayName("ruleName/update can update ")
    @Test
    public void postMappingUpdateTest() throws Exception {
        //GIVEN an existing bidList
        final RuleName existingRuleName = createRuleNameOnDatabase();
        final Integer id = existingRuleName.getId();
        final String url = "/ruleName/update/" + id;

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("name", "name")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sql")
                .param("sqlPart", "sqlPart"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @WithMockUser(username = "username")
    @DisplayName("/ruleName/update with validation error")
    @Test
    public void testRuleNameUpdateWithValidationError() throws Exception {
        //GIVEN an existing bidList
        final RuleName existingRuleName = createRuleNameOnDatabase();
        final Integer id = existingRuleName.getId();
        final String url = "/ruleName/update/" + id;

        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("name", "")
                .param("description", "")
                .param("json", "")
                .param("template", "type")
                .param("sql", "type")
                .param("sqlPart", "type"));


        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    @WithMockUser(username = "username")
    @DisplayName("ruleName/update with exception ")
    @Test
    public void testRuleNameUpdateWithException() throws Exception {
        //GIVEN
        final String url = "/ruleName/update/200";
        // WHEN
        final var response = mockMvc.perform(post(url)
                .with(csrf())
                .param("name", "name")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sql")
                .param("sqlPart", "sqlPart"));

        // THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(model().attributeExists("errorMsg"));
    }

    @WithMockUser(username = "username")
    @Test
    @DisplayName("Test GetMapping delete")
    public void getMappingDeleteTest() throws Exception {
        //GIVEN
        final RuleName existingRuleName = createRuleNameOnDatabase();
        final Integer id = existingRuleName.getId();
        final String url = "/ruleName/delete/" + id;

        //WHEN
        final var response = mockMvc.perform(get(url)
                        .with(csrf())
                        .param("id",id.toString()))
                .andDo(MockMvcResultHandlers.print());


        //THEN
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    private RuleName createRuleNameOnDatabase(){
        return  ruleNameRepository.save(new RuleName ("name", "description","json","template","sql","sqlpart"));
    }

}
