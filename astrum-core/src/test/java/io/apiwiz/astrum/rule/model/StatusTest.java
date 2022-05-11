package io.apiwiz.astrum.rule.model;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StatusTest {

	@Test
	public void test() {
		Status status=Status.ACTIVE;
		assertEquals(Status.valueOf("ACTIVE"),status);
	}

	@Test
	public void testRule()
	{
		Rule rule=new Rule();
		rule.setId("2");
		rule.getId();
		assertEquals(rule.getId(),"2");
				
	}
	
	@Test
	public void testRegexRule()
	{
		RegexRule rule=new RegexRule();
		rule.setOperation(null);
		rule.getOperation();

				
	}
	@Test
	public void testId()
	{
		Rule rule=new Rule();
		rule.setCts(2222);

				
	}
}
