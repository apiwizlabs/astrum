package io.apiwiz.astrum.cli.cmd;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.apiwiz.astrum.core.impl.ValidateSchema;
import io.apiwiz.astrum.core.util.SwaggerLinterUtil;
import io.apiwiz.astrum.rule.model.Rule;
import io.swagger.util.Json;

@RunWith(MockitoJUnitRunner.class)
public class LinterCommandTest {
	@Mock
	SwaggerLinterUtil swaggerlinter;
	@InjectMocks
	private LinterCommand linterCommand;

	@Test
	public void checkReturnValueWhenSkipLintingIsTrue() throws Exception {

		LinterCommand lint = new LinterCommand();
		String path = "src/test/resources/test.json";
		File file = new File(path);
		lint.setSwaggerFile(file);
		lint.setSkipLinting(true);
		assertEquals(Integer.valueOf(0), lint.call());

	}


	@Test 
	public void checkSwaggerLintingWhenRuleFileIsNotPassedinCMD() throws Exception{
		//null
		//Input OAS 2.0
		LinterCommand lint = new LinterCommand();
		lint.setRulesFile(null);
		String path = "src/test/resources/test.json";
		File file = new File(path);
		lint.setSwaggerFile(file);

		assertEquals(Integer.valueOf(0),lint.call());
		
		
	}
	
	@Test
	public void checkSwaggerLintingWhenRuleFilesIsNotPassedinCMD_OAS3() throws Exception {
		//null
		//Input OAS 3.0
		LinterCommand lint = new LinterCommand();
		lint.setRulesFile(null);
		String path = "src/test/resources/test1.json";
		File file = new File(path);
		lint.setSwaggerFile(file);
		String path1 = "src/test/resources/output.json";
		File file1 = new File(path1);
		lint.setOutputFile(file1);
		assertEquals(Integer.valueOf(0),lint.call());
		
	}
	
	
	
	@Test
	public void testWriteFormattedSwaggerStr() throws IOException {
		LinterCommand lint = new LinterCommand();
		String swaggerFile = "{String}";
		String path = "src/test/resources/test.json";
		File file = new File(path);
		lint.setSwaggerFile(file);
		file.getName();

		lint.writeFormattedSwaggerStr(swaggerFile);
	}

	@Test
	public void testReadFromFile() throws IOException {
		LinterCommand searchPhrase = new LinterCommand();
		String resourceName = "new.txt";

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(resourceName).getFile());

		String expected = "Created";
		searchPhrase.readFromFile(resourceName);
		BufferedReader br = new BufferedReader(
				new InputStreamReader(getClass().getClassLoader().getResourceAsStream(resourceName)));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		sb.append(line);

		assertEquals(expected, sb.toString());

	}

}
