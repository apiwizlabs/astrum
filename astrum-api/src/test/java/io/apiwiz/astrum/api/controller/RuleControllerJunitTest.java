package io.apiwiz.astrum.api.controller;

import io.apiwiz.astrum.api.service.RuleService;
import io.apiwiz.astrum.rule.model.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;


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
    
	@Test
	public void testCreateRules() {
		Rule rule=new Rule();
		ResponseEntity<HttpStatus> var=ruleController.createRules(rule);
		
		assertEquals(201, var.getStatusCodeValue());
	}
	@Test
	public void saveCalled() {
		Rule rule=new Rule();
		ruleController.createRules(rule);
		
		Mockito.verify(ruleService,  times(1)).save(Mockito.any());
	}
	@Test
	public void testUpdateRules() {
		Rule rule=new Rule();
		ResponseEntity<HttpStatus> var=ruleController.updateRules(rule);
		
		assertEquals(202, var.getStatusCodeValue());
	}
	@Test
	public void updateCalled() {
		Rule rule=new Rule();
		ruleController.updateRules(rule);
		
		Mockito.verify(ruleService,  times(1)).update(Mockito.any());
	}
	@Test
	public void testDeleteRules() {
		String ruleId=new String();
		ResponseEntity<HttpStatus> var=ruleController.deleteRules(ruleId);
		
		assertEquals(202, var.getStatusCodeValue());
	}
	
	@Test
	public void deleteCalled() {
		String ruleId=new String();
		ruleController.deleteRules(ruleId);
		
		Mockito.verify(ruleService,  times(1)).delete(Mockito.any());
	}
	
	@Test
	public void testGetAllRules() {
		ResponseEntity<List<Rule>> var=ruleController.getAllRules();
		
		assertEquals(200, var.getStatusCodeValue());
	}
	@Test
	public void testLint() {
		MultipartFile  multipartFile = null;
		ResponseEntity<?> var=ruleController.lint(multipartFile);
		
		assertEquals(200, var.getStatusCodeValue());
	}
	@Test
	public void testFormat() {
		MultipartFile  multipartFile = mock(MultipartFile.class);
		int val;
		ResponseEntity<?> var=ruleController.formatSwaggerFile(multipartFile);
		
		assertEquals(200, var.getStatusCodeValue());
	}


}
