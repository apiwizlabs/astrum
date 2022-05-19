package io.apiwiz.astrum.api.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import io.apiwiz.astrum.api.controller.RuleController;
import io.apiwiz.astrum.api.validator.RequestNotValidException;
import io.apiwiz.astrum.rule.model.Rule;


@RunWith(MockitoJUnitRunner.class)
public class RuleServiceTest {

	@Mock
	private MongoTemplate mongoTemplate;

	@InjectMocks
	private RuleService ruleService;

	@Test(expected=RequestNotValidException.class)
	public void testSaveIf() {
		Rule rule = new Rule();
		rule.setOasVersion("2.0");

		Mockito.when((mongoTemplate).findOne(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(null);
		ruleService.save(rule);
		Mockito.verify(mongoTemplate, times(1)).save(Mockito.eq(rule), Mockito.eq("Swagger.LinterRules"));
		Mockito.when((mongoTemplate).findOne(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(rule);
		ruleService.save(rule);
		Mockito.when((mongoTemplate).findOne(Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(RequestNotValidException.class);
			
	}
	
	@Test(expected=RequestNotValidException.class)
	public void testSaveElse() {
		Rule rule = mock(Rule.class);
		ruleService.save(rule);



	}
	@Test
	public void testUpdate() {
		Rule rule = new Rule();
		rule.setOasVersion("2.0");

		Mockito.when((mongoTemplate).findById(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(rule);
		ruleService.update(rule);
		Mockito.verify(mongoTemplate, times(1)).save(Mockito.eq(rule), Mockito.eq("Swagger.LinterRules"));
		
		

	}
	@Test
	public void testDelete() {
		Rule rule = new Rule();
		String rule1=new String("1");
		rule.setOasVersion("2.0");

		Mockito.when((mongoTemplate).findById(Mockito.any(),Mockito.any())).thenReturn(rule);
		ruleService.delete(rule1);
		Mockito.when((mongoTemplate).findById(Mockito.any(),Mockito.any())).thenReturn(null);
		ruleService.delete(rule1);
		//Mockito.verify(mongoTemplate, times(1)).remove(Mockito.eq(rule1),Mockito.eq("Swagger.LinterRules"));

	}
	
	@Test
	public void testGetRuleById() {
		Rule rule = new Rule();
		String rule1=new String("1");
		rule.setOasVersion("2.0");

		Mockito.when((mongoTemplate).findById(Mockito.any(),Mockito.any())).thenReturn(rule);
		ruleService.getRuleById(rule1);
		Mockito.when((mongoTemplate).findById(Mockito.any(),Mockito.any())).thenReturn(null);
		ruleService.getRuleById(rule1);
	}
	@Test
	public void testGetAllRules() {
		ruleService.getAllRules();
		Mockito.verify(mongoTemplate, times(1)).findAll(Mockito.any(), Mockito.any());

	}
//	@Test(expected=IOException.class)
//	public void testFormat() {
//		MultipartFile file=null;
//		doThrow(IOException.class).when(ruleService).format(file);
//		assertThrows(RequestNotValidException.class,Mockito.any());
//	}
	
	@Test(expected=RequestNotValidException.class)
	public void testIsValid() {
		Rule rule = new Rule();
		rule.setOasVersion("2.0");
		String oasVersion="4.0";
		ruleService.isValid(oasVersion);
	
		
		

	}
	
//	@Test(expected=RequestNotValidException.class)
//	public void testFormat() {
//		MultipartFile file=mock(MultipartFile.class);
//		String by=file.getBytes();
//		ruleService.format(file);
//	    }
	}

	
		
		

	

