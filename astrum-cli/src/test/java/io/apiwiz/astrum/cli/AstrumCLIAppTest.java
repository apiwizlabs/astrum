package io.apiwiz.astrum.cli;

import static org.junit.Assert.*;

import org.junit.Test;

public class AstrumCLIAppTest {

	@Test
	public void testMain() {
		AstrumCLIApp app=new AstrumCLIApp();
		app.main(null);
	}

}
