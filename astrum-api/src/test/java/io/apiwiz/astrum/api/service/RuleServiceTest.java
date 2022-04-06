package io.apiwiz.astrum.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import io.apiwiz.astrum.api.controller.RuleController;
import io.apiwiz.astrum.rule.model.Rule;
@ExtendWith(MockitoExtension.class)

class RuleServiceTest {
	
@Mock
private MongoTemplate mongoTemplate;

@InjectMocks
private  RuleService ruleService;

	@Test
	void testSave() {
		Rule rule=new Rule();
		rule.setOasVersion("2.0");
		
		
		Mockito.when((mongoTemplate).findOne(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(null);
		ruleService.save(rule);
		Mockito.verify(mongoTemplate,  times(1)).save(Mockito.eq(rule),Mockito.eq("Swagger.LinterRules"));

		
	
	
	}

}
