
package io.apiwiz.astrum.api.controller;

import io.apiwiz.astrum.api.service.RuleService;
import io.apiwiz.astrum.rule.model.AssertionRule;
import io.apiwiz.astrum.rule.model.Rule;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RuleController.class)
@AutoConfigureRestDocs
public class RuleControllerTest {

//    @Autowired
//    private WebApplicationContext context;

    @MockBean
    RuleService ruleService;

    @Autowired
    private MockMvc mockMvc;


    @org.junit.Rule
    public JUnitRestDocumentation documentation = new JUnitRestDocumentation();

    @Test
    public void getPersonByIdShouldReturnOk() throws Exception {

        when(ruleService.getRuleById(Mockito.anyString())).thenReturn(new Rule());


        String url = "/v1/astrum/rule/1";
        this.mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(document("rules/get-by-id"));
    }

}