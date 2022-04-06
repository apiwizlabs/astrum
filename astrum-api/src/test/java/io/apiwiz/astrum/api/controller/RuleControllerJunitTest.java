package io.apiwiz.astrum.api.controller;

import io.apiwiz.astrum.api.service.RuleService;
import io.apiwiz.astrum.rule.model.Rule;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class RuleControllerJunitTest {

    @Mock
    private RuleService ruleService;


    @InjectMocks
    private RuleController ruleController;

    @Test
    public void checkCreateRules() {

        Rule rule = mock(Rule.class);

        ResponseEntity<HttpStatus> response = ruleController.createRules(rule);
        assertEquals(201, response.getStatusCodeValue());

    }

    @Test
    public void checkCreateRules_IsSaveCalled() {

        Rule rule = mock(Rule.class);

        ruleController.createRules(rule);

        Mockito.verify(ruleService, times(1)).save(Mockito.any());


    }

}
