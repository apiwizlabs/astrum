package io.apiwiz.astrum.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.apiwiz.astrum.api.service.RuleService;
import io.apiwiz.astrum.rule.model.Rule;
@ExtendWith(MockitoExtension.class)

class RuleControllerTest2 {
	
@Mock
private RuleService ruleService;

@InjectMocks
private RuleController ruleController;
	
	@Test
	void testCreateRules() {
		Rule rule=new Rule();
		ResponseEntity<HttpStatus> var=ruleController.createRules(rule);
		
		assertEquals(201, var.getStatusCodeValue());
	}
	@Test
	void saveCalled() {
		Rule rule=new Rule();
		ruleController.createRules(rule);
		
		Mockito.verify(ruleService,  times(1)).save(Mockito.any());
	}
}

