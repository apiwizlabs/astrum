package io.apiwiz.astrum.api.repo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;


@RunWith(MockitoJUnitRunner.class)
public class SwaggerLinterRulesMongoRepoTest {
	@Mock
	private MongoTemplate mongoTemplate;
	@InjectMocks
	private 	SwaggerLinterRulesMongoRepo mongoRepo;

	@Test
	public void testGetRegexRules() {
		String version="2.0";
		mongoRepo.getRegexRules(version);
		Mockito.verify(mongoTemplate, times(1)).find(Mockito.any(), Mockito.any());
	}

	@Test
	public void testGetOASAssertionRule() {
		mongoRepo.getOASAssertionRule("2.0");
		Mockito.verify(mongoTemplate, times(1)).find(Mockito.any(), Mockito.any());
	}

}
