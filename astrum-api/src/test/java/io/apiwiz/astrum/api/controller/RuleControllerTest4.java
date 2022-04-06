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
class RuleControllerTest4 {

	@Mock
	private RuleService ruleService;

	@InjectMocks
	private RuleController ruleController;
		
		@Test
		void testDeleteRules() {
			String ruleId=new String();
			ResponseEntity<HttpStatus> var=ruleController.deleteRules(ruleId);
			
			assertEquals(202, var.getStatusCodeValue());
		}
		@Test
		void updateCalled() {
			String ruleId=new String();
			ruleController.deleteRules(ruleId);
			
			Mockito.verify(ruleService,  times(1)).delete(Mockito.any());
		}

}
