package io.apiwiz.astrum.core.util;
import org.junit.Test;

import static org.junit.Assert.*;
public class SwaggerLinterUtilTest {

	@Test
	public void getJsonPathFromPointer() {
		String jsonPath = "/info/license";

		String jsonPathFromPointer = SwaggerLinterUtil.getJsonPathFromPointer(jsonPath);
		System.out.println(jsonPathFromPointer);
		assertEquals("$.info.license", jsonPathFromPointer);
	}
}