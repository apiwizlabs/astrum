package io.apiwiz.astrum.core.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SchemaValidationErrorTest {

	@Test
	public void testSchemaValidationError() {
		SchemaValidationError schema=new SchemaValidationError();

	}

	@Test
	public void testSetLevel() {
		SchemaValidationError schemaerror=new SchemaValidationError();
	    String level="2";
	    String domain="domain";
	    String keyword="keyword";
	    String message="message";
	    Schema schema = null;
	    Instance instance = null;
	    List<String> required=new ArrayList<String>();
	    required.add("First");
	    required.add("Second");
	    List<String> missing=new ArrayList<String>();
	    missing.add("First");
	    missing.add("Second");
	    schemaerror.setLevel(level);
	   assertEquals(schemaerror.getLevel(), "2");
	   schemaerror.setDomain(domain);
	   assertEquals(schemaerror.getDomain(), "domain");
	   schemaerror.setKeyword(keyword);
	   assertEquals(schemaerror.getKeyword(), "keyword");
	   schemaerror.setMessage(message);
	   assertEquals(schemaerror.getMessage(), "message");
	   schemaerror.setRequired(required);
	   schemaerror.getRequired();
	   assertEquals(required.get(1),"Second");
	   schemaerror.setMissing(required);
	   schemaerror.getMissing();
	   assertEquals(missing.get(1),"Second");
	   schemaerror.setSchema(schema);
	   assertEquals(schemaerror.getSchema(),null);
	   schemaerror.setInstance(instance);
	   assertEquals(schemaerror.getInstance(),null);
	   schemaerror.toString();
	   assertEquals("SchemaValidationError{" + "level='" + level + '\'' + ", domain='" + domain + '\'' + ", keyword='" + keyword + '\'' + ", message='" + message + '\'' + ", schema=" + schema + ", instance=" + instance + ", required=" + required + ", missing=" + missing + '}',schemaerror.toString());
	}



//	@Test
//	public void testToString() {
//		fail("Not yet implemented");
//	}

}
