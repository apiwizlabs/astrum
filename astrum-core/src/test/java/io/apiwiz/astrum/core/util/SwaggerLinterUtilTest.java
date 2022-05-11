package io.apiwiz.astrum.core.util;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.apiwiz.astrum.core.impl.CustomParserFactory;
import io.apiwiz.astrum.core.model.LinterResponse;
import io.apiwiz.astrum.core.model.Schema;
import io.apiwiz.astrum.core.model.SchemaValidationError;
import io.apiwiz.astrum.core.model.SwaggerLintingOutput;
import io.apiwiz.astrum.core.model.ValidationResponse;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
public class SwaggerLinterUtilTest {

	@Test
	public void getJsonPathFromPointer() {
		String jsonPath = "/info/license";

		String jsonPathFromPointer = SwaggerLinterUtil.getJsonPathFromPointer(jsonPath);
		System.out.println(jsonPathFromPointer);
		assertEquals("$.info.license", jsonPathFromPointer);
	}
	@Test
	public void testFormatSwaggerStr() throws JsonProcessingException  {
		SwaggerLinterUtil lint=new SwaggerLinterUtil();
		String swaggerFile="{String}";
		String format=lint.formatSwaggerStr(swaggerFile);
	}
	
	@Test
	public void testIsSwaggerVersion2(){
		SwaggerLinterUtil lint=new SwaggerLinterUtil();
		String version="2";
		String version2=null;
		assertEquals(true,lint.isSwaggerVersion2(version));
		assertEquals(false,lint.isSwaggerVersion2(version2));

	}
	
	@Test
	public void testGetVersion(){
		SwaggerLinterUtil lint=new SwaggerLinterUtil();
		JsonNode node=Mockito.mock(JsonNode.class);
		JsonNode version=node.get("openapi");

		lint.getVersion(node);

	}
	
	@Test
	public void testSwaggerLintingOutput() {
		SwaggerLintingOutput swagger=new SwaggerLintingOutput();
	}
	
	
	@Test
	public void testCustomParserFactory() throws IOException,JsonParseException {
		CustomParserFactory factory=new CustomParserFactory();
		Reader r = null;
		factory.createParser(r);
	}
	
	
	
	@Test
	public void testLinterResponse() throws IOException,JsonParseException {
		LinterResponse response=new LinterResponse();
	}
	
	
	
	@Test
	public void testSchema()  {
		Schema schema=new Schema();
		schema.getPointer();
		schema.getLoadingURI();
	}
	
	@Test
	public void testValidateResponse()  {
		ValidationResponse response=new ValidationResponse();
		List<String> message=new ArrayList<>();
		List<SchemaValidationError> validatemessage=new ArrayList<>();
		response.setMessages(message);
		response.setSchemaValidationMessages(validatemessage);
		response.toString();
	
	}
	@Test
	public void testGetVersionExample() throws IOException{
		SwaggerLinterUtil lint=new SwaggerLinterUtil();
		
		String node = "{\r\n" + "  \"openapi\": \"3.0.0\",\r\n" + "  \"info\": {\r\n"
				+ "    \"version\": \"1.0.0\",\r\n" + "    \"title\": \"Swagger Petstore\",\r\n"
				+ "    \"license\": {\r\n" + "      \"name\": \"MIT\"\r\n" + "    }\r\n" + "  },\r\n"
				+ "  \"servers\": [\r\n" + "    {\r\n" + "      \"url\": \"http://petstore.swagger.io/v1\"\r\n"
				+ "    }\r\n" + "  ],\r\n" + "  \"paths\": {\r\n" + "    \"/pets\": {\r\n" + "      \"get\": {\r\n"
				+ "        \"summary\": \"List all pets\",\r\n" + "        \"operationId\": \"listPets\",\r\n"
				+ "        \"tags\": [\r\n" + "          \"pets\"\r\n" + "        ],\r\n"
				+ "        \"parameters\": [\r\n" + "          {\r\n" + "            \"name\": \"limit\",\r\n"
				+ "            \"in\": \"query\",\r\n"
				+ "            \"description\": \"How many items to return at one time (max 100)\",\r\n"
				+ "            \"required\": false,\r\n" + "            \"schema\": {\r\n"
				+ "              \"type\": \"integer\",\r\n" + "              \"format\": \"int32\"\r\n"
				+ "            }\r\n" + "          }\r\n" + "        ],\r\n" + "        \"responses\": {\r\n"
				+ "          \"200\": {\r\n" + "            \"description\": \"A paged array of pets\",\r\n"
				+ "            \"headers\": {\r\n" + "              \"x-next\": {\r\n"
				+ "                \"description\": \"A link to the next page of responses\",\r\n"
				+ "                \"schema\": {\r\n" + "                  \"type\": \"string\"\r\n"
				+ "                }\r\n" + "              }\r\n" + "            },\r\n"
				+ "            \"content\": {\r\n" + "              \"application/json\": {\r\n"
				+ "                \"schema\": {\r\n" + "                  \"$ref\": \"#/components/schemas/Pets\"\r\n"
				+ "                }\r\n" + "              }\r\n" + "            }\r\n" + "          },\r\n"
				+ "          \"default\": {\r\n" + "            \"description\": \"unexpected error\",\r\n"
				+ "            \"content\": {\r\n" + "              \"application/json\": {\r\n"
				+ "                \"schema\": {\r\n" + "                  \"$ref\": \"#/components/schemas/Error\"\r\n"
				+ "                }\r\n" + "              }\r\n" + "            }\r\n" + "          }\r\n"
				+ "        }\r\n" + "      },\r\n" + "      \"post\": {\r\n"
				+ "        \"summary\": \"Create a pet\",\r\n" + "        \"operationId\": \"createPets\",\r\n"
				+ "        \"tags\": [\r\n" + "          \"pets\"\r\n" + "        ],\r\n"
				+ "        \"responses\": {\r\n" + "          \"201\": {\r\n"
				+ "            \"description\": \"Null response\"\r\n" + "          },\r\n"
				+ "          \"default\": {\r\n" + "            \"description\": \"unexpected error\",\r\n"
				+ "            \"content\": {\r\n" + "              \"application/json\": {\r\n"
				+ "                \"schema\": {\r\n" + "                  \"$ref\": \"#/components/schemas/Error\"\r\n"
				+ "                }\r\n" + "              }\r\n" + "            }\r\n" + "          }\r\n"
				+ "        }\r\n" + "      }\r\n" + "    },\r\n" + "    \"/pets/{petId}\": {\r\n"
				+ "      \"get\": {\r\n" + "        \"summary\": \"Info for a specific pet\",\r\n"
				+ "        \"operationId\": \"showPetById\",\r\n" + "        \"tags\": [\r\n" + "          \"pets\"\r\n"
				+ "        ],\r\n" + "        \"parameters\": [\r\n" + "          {\r\n"
				+ "            \"name\": \"petId\",\r\n" + "            \"in\": \"path\",\r\n"
				+ "            \"required\": true,\r\n"
				+ "            \"description\": \"The id of the pet to retrieve\",\r\n"
				+ "            \"schema\": {\r\n" + "              \"type\": \"string\"\r\n" + "            }\r\n"
				+ "          }\r\n" + "        ],\r\n" + "        \"responses\": {\r\n" + "          \"200\": {\r\n"
				+ "            \"description\": \"Expected response to a valid request\",\r\n"
				+ "            \"content\": {\r\n" + "              \"application/json\": {\r\n"
				+ "                \"schema\": {\r\n" + "                  \"$ref\": \"#/components/schemas/Pet\"\r\n"
				+ "                }\r\n" + "              }\r\n" + "            }\r\n" + "          },\r\n"
				+ "          \"default\": {\r\n" + "            \"description\": \"unexpected error\",\r\n"
				+ "            \"content\": {\r\n" + "              \"application/json\": {\r\n"
				+ "                \"schema\": {\r\n" + "                  \"$ref\": \"#/components/schemas/Error\"\r\n"
				+ "                }\r\n" + "              }\r\n" + "            }\r\n" + "          }\r\n"
				+ "        }\r\n" + "      }\r\n" + "    }\r\n" + "  },\r\n" + "  \"components\": {\r\n"
				+ "    \"schemas\": {\r\n" + "      \"Pet\": {\r\n" + "        \"type\": \"object\",\r\n"
				+ "        \"required\": [\r\n" + "          \"id\",\r\n" + "          \"name\"\r\n" + "        ],\r\n"
				+ "        \"properties\": {\r\n" + "          \"id\": {\r\n" + "            \"type\": \"integer\",\r\n"
				+ "            \"format\": \"int64\"\r\n" + "          },\r\n" + "          \"name\": {\r\n"
				+ "            \"type\": \"string\"\r\n" + "          },\r\n" + "          \"tag\": {\r\n"
				+ "            \"type\": \"string\"\r\n" + "          }\r\n" + "        }\r\n" + "      },\r\n"
				+ "      \"Pets\": {\r\n" + "        \"type\": \"array\",\r\n" + "        \"items\": {\r\n"
				+ "          \"$ref\": \"#/components/schemas/Pet\"\r\n" + "        }\r\n" + "      },\r\n"
				+ "      \"Error\": {\r\n" + "        \"type\": \"object\",\r\n" + "        \"required\": [\r\n"
				+ "          \"code\",\r\n" + "          \"message\"\r\n" + "        ],\r\n"
				+ "        \"properties\": {\r\n" + "          \"code\": {\r\n"
				+ "            \"type\": \"integer\",\r\n" + "            \"format\": \"int32\"\r\n"
				+ "          },\r\n" + "          \"message\": {\r\n" + "            \"type\": \"string\"\r\n"
				+ "          }\r\n" + "        }\r\n" + "      }\r\n" + "    }\r\n" + "  }\r\n" + "}";
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(node);

		lint.getVersion(jsonNode);
		JsonNode version= jsonNode.get("openapi");
		assertEquals("\"3.0.0\"",version.toString());
		String oas2 = "{\r\n" + "    \"swagger\": \"2.0\",\r\n" + "    \"info\": {\r\n"
				+ "        \"title\": \"flavor-access-v2.1\",\r\n" + "        \"version\": \"Unknown\"\r\n"
				+ "    },\r\n" + "    \"consumes\": [\r\n" + "        \"application/json\"\r\n" + "    ],\r\n"
				+ "    \"produces\": [\r\n" + "        \"application/json\"\r\n" + "    ],\r\n" + "    \"paths\": {\r\n"
				+ "        \"/v2.1/flavors\": {\r\n" + "            \"post\": {\r\n"
				+ "                \"operationId\": \"extendflavor\",\r\n"
				+ "                \"summary\": \"Add access attribute to flavor create\",\r\n"
				+ "                \"description\": \"Adds access attribute to the flavor create response.\\n\",\r\n"
				+ "                \"produces\": [\r\n" + "                    \"application/json\"\r\n"
				+ "                ],\r\n" + "                \"responses\": {\r\n"
				+ "                    \"200\": {\r\n"
				+ "                        \"description\": \"200 response\",\r\n"
				+ "                        \"examples\": {\r\n"
				+ "                            \"application/json\": \"{\\n    \\\"flavor\\\": {\\n        \\\"flavor-access:is_public\\\": false,\\n        \\\"links\\\": [\\n            {\\n                \\\"href\\\": \\\"http://openstack.example.com/v3/flavors/10\\\",\\n                \\\"rel\\\": \\\"self\\\"\\n            },\\n            {\\n                \\\"href\\\": \\\"http://openstack.example.com/flavors/10\\\",\\n                \\\"rel\\\": \\\"bookmark\\\"\\n            }\\n        ],\\n        \\\"ram\\\": 1024,\\n        \\\"ephemeral\\\": 0,\\n        \\\"disabled\\\": false,\\n        \\\"vcpus\\\": 2,\\n        \\\"swap\\\": 0,\\n        \\\"disk\\\": 10,\\n        \\\"id\\\": \\\"10\\\",\\n        \\\"name\\\": \\\"test_flavor\\\"\\n    }\\n}\"\r\n"
				+ "                        }\r\n" + "                    }\r\n" + "                }\r\n"
				+ "            }\r\n" + "        },\r\n" + "        \"/v2.1/flavors/detail\": {\r\n"
				+ "            \"get\": {\r\n" + "                \"operationId\": \"addaccessresponse\",\r\n"
				+ "                \"summary\": \"Add access attribute to flavor detail\",\r\n"
				+ "                \"description\": \"Extends flavor detail to add access attribute to the response of flavor detail.\\n\",\r\n"
				+ "                \"produces\": [\r\n" + "                    \"application/json\"\r\n"
				+ "                ],\r\n" + "                \"responses\": {\r\n"
				+ "                    \"200\": {\r\n"
				+ "                        \"description\": \"200 response\",\r\n"
				+ "                        \"examples\": {\r\n"
				+ "                            \"application/json\": \"{\\n    \\\"flavors\\\": [\\n        {\\n            \\\"flavor-access:is_public\\\": true,\\n            \\\"links\\\": [\\n                {\\n                    \\\"href\\\": \\\"http://openstack.example.com/v3/flavors/1\\\",\\n                    \\\"rel\\\": \\\"self\\\"\\n                },\\n                {\\n                    \\\"href\\\": \\\"http://openstack.example.com/flavors/1\\\",\\n                    \\\"rel\\\": \\\"bookmark\\\"\\n                }\\n            ],\\n            \\\"ram\\\": 512,\\n            \\\"ephemeral\\\": 0,\\n            \\\"disabled\\\": false,\\n            \\\"vcpus\\\": 1,\\n            \\\"swap\\\": 0,\\n            \\\"disk\\\": 1,\\n            \\\"id\\\": \\\"1\\\",\\n            \\\"name\\\": \\\"m1.tiny\\\"\\n        },\\n        {\\n            \\\"flavor-access:is_public\\\": true,\\n            \\\"links\\\": [\\n                {\\n                    \\\"href\\\": \\\"http://openstack.example.com/v3/flavors/2\\\",\\n                    \\\"rel\\\": \\\"self\\\"\\n                },\\n                {\\n                    \\\"href\\\": \\\"http://openstack.example.com/flavors/2\\\",\\n                    \\\"rel\\\": \\\"bookmark\\\"\\n                }\\n            ],\\n            \\\"ram\\\": 2048,\\n            \\\"ephemeral\\\": 0,\\n            \\\"disabled\\\": false,\\n            \\\"vcpus\\\": 1,\\n            \\\"swap\\\": 0,\\n            \\\"disk\\\": 20,\\n            \\\"id\\\": \\\"2\\\",\\n            \\\"name\\\": \\\"m1.small\\\"\\n        },\\n        {\\n            \\\"flavor-access:is_public\\\": true,\\n            \\\"links\\\": [\\n                {\\n                    \\\"href\\\": \\\"http://openstack.example.com/v3/flavors/3\\\",\\n                    \\\"rel\\\": \\\"self\\\"\\n                },\\n                {\\n                    \\\"href\\\": \\\"http://openstack.example.com/flavors/3\\\",\\n                    \\\"rel\\\": \\\"bookmark\\\"\\n                }\\n            ],\\n            \\\"ram\\\": 4096,\\n            \\\"ephemeral\\\": 0,\\n            \\\"disabled\\\": false,\\n            \\\"vcpus\\\": 2,\\n            \\\"swap\\\": 0,\\n            \\\"disk\\\": 40,\\n            \\\"id\\\": \\\"3\\\",\\n            \\\"name\\\": \\\"m1.medium\\\"\\n        },\\n        {\\n            \\\"flavor-access:is_public\\\": true,\\n            \\\"links\\\": [\\n                {\\n                    \\\"href\\\": \\\"http://openstack.example.com/v3/flavors/4\\\",\\n                    \\\"rel\\\": \\\"self\\\"\\n                },\\n                {\\n                    \\\"href\\\": \\\"http://openstack.example.com/flavors/4\\\",\\n                    \\\"rel\\\": \\\"bookmark\\\"\\n                }\\n            ],\\n            \\\"ram\\\": 8192,\\n            \\\"ephemeral\\\": 0,\\n            \\\"disabled\\\": false,\\n            \\\"vcpus\\\": 4,\\n            \\\"swap\\\": 0,\\n            \\\"disk\\\": 80,\\n            \\\"id\\\": \\\"4\\\",\\n            \\\"name\\\": \\\"m1.large\\\"\\n        },\\n        {\\n            \\\"flavor-access:is_public\\\": true,\\n            \\\"links\\\": [\\n                {\\n                    \\\"href\\\": \\\"http://openstack.example.com/v3/flavors/5\\\",\\n                    \\\"rel\\\": \\\"self\\\"\\n                },\\n                {\\n                    \\\"href\\\": \\\"http://openstack.example.com/flavors/5\\\",\\n                    \\\"rel\\\": \\\"bookmark\\\"\\n                }\\n            ],\\n            \\\"ram\\\": 16384,\\n            \\\"ephemeral\\\": 0,\\n            \\\"disabled\\\": false,\\n            \\\"vcpus\\\": 8,\\n            \\\"swap\\\": 0,\\n            \\\"disk\\\": 160,\\n            \\\"id\\\": \\\"5\\\",\\n            \\\"name\\\": \\\"m1.xlarge\\\"\\n        }\\n    ]\\n}\"\r\n"
				+ "                        }\r\n" + "                    }\r\n" + "                }\r\n"
				+ "            }\r\n" + "        },\r\n" + "        \"/v2.1/flavors/1\": {\r\n"
				+ "            \"get\": {\r\n" + "                \"operationId\": \"extendflavorshow\",\r\n"
				+ "                \"summary\": \"Add access attribute to flavor show\",\r\n"
				+ "                \"description\": \"Extends flavor show to add access attribute to the flavor show response.\\n\",\r\n"
				+ "                \"produces\": [\r\n" + "                    \"application/json\"\r\n"
				+ "                ],\r\n" + "                \"responses\": {\r\n"
				+ "                    \"200\": {\r\n"
				+ "                        \"description\": \"200 response\",\r\n"
				+ "                        \"examples\": {\r\n"
				+ "                            \"application/json\": \"{\\n    \\\"flavor\\\": {\\n        \\\"flavor-access:is_public\\\": true,\\n        \\\"links\\\": [\\n            {\\n                \\\"href\\\": \\\"http://openstack.example.com/v3/flavors/1\\\",\\n                \\\"rel\\\": \\\"self\\\"\\n            },\\n            {\\n                \\\"href\\\": \\\"http://openstack.example.com/flavors/1\\\",\\n                \\\"rel\\\": \\\"bookmark\\\"\\n            }\\n        ],\\n        \\\"ram\\\": 512,\\n        \\\"ephemeral\\\": 0,\\n        \\\"disabled\\\": false,\\n        \\\"vcpus\\\": 1,\\n        \\\"swap\\\": 0,\\n        \\\"disk\\\": 1,\\n        \\\"id\\\": \\\"1\\\",\\n        \\\"name\\\": \\\"m1.tiny\\\"\\n    }\\n}\"\r\n"
				+ "                        }\r\n" + "                    }\r\n" + "                }\r\n"
				+ "            }\r\n" + "        },\r\n" + "        \"/v2.1/flavors/10/action\": {\r\n"
				+ "            \"post\": {\r\n" + "                \"operationId\": \"removeflavoraccess\",\r\n"
				+ "                \"summary\": \"Remove flavor access\",\r\n"
				+ "                \"description\": \"Removes flavor access for tenant.\\n\",\r\n"
				+ "                \"produces\": [\r\n" + "                    \"application/json\"\r\n"
				+ "                ],\r\n" + "                \"responses\": {\r\n"
				+ "                    \"200\": {\r\n"
				+ "                        \"description\": \"200 response\",\r\n"
				+ "                        \"examples\": {\r\n"
				+ "                            \"application/json\": \"{\\n    \\\"flavor_access\\\": [\\n        {\\n            \\\"tenant_id\\\": \\\"openstack\\\",\\n            \\\"flavor_id\\\": \\\"10\\\"\\n        }\\n    ]\\n}\"\r\n"
				+ "                        }\r\n" + "                    }\r\n" + "                }\r\n"
				+ "            }\r\n" + "        },\r\n" + "        \"/v2.1/flavors/10/flavor-access\": {\r\n"
				+ "            \"get\": {\r\n" + "                \"operationId\": \"returnaccesslist\",\r\n"
				+ "                \"summary\": \"Return access list\",\r\n"
				+ "                \"description\": \"Returns access list by flavor id.\\n\",\r\n"
				+ "                \"produces\": [\r\n" + "                    \"application/json\"\r\n"
				+ "                ],\r\n" + "                \"responses\": {\r\n"
				+ "                    \"200\": {\r\n"
				+ "                        \"description\": \"200 response\",\r\n"
				+ "                        \"examples\": {\r\n"
				+ "                            \"application/json\": \"{\\n    \\\"flavor_access\\\": [\\n        {\\n            \\\"tenant_id\\\": \\\"openstack\\\",\\n            \\\"flavor_id\\\": \\\"10\\\"\\n        },\\n        {\\n            \\\"tenant_id\\\": \\\"fake_tenant\\\",\\n            \\\"flavor_id\\\": \\\"10\\\"\\n        }\\n    ]\\n}\"\r\n"
				+ "                        }\r\n" + "                    }\r\n" + "                }\r\n"
				+ "            }\r\n" + "        }\r\n" + "    }\r\n" + "}";
		JsonNode jsonNode1 = objectMapper.readTree(oas2);

		lint.getVersion(jsonNode1);
		JsonNode version1= jsonNode1.get("swagger");
		assertEquals("\"2.0\"",version1.toString());
		String oas1 = "{\r\n" + "  \"apiVersion\": \"1.0.0\",\r\n" + "  \"swaggerVersion\": \"1.2\",\r\n"
				+ "  \"apis\": [\r\n" + "    {\r\n" + "      \"path\": \"/pet\",\r\n"
				+ "      \"description\": \"Operations about pets\"\r\n" + "    },\r\n" + "    {\r\n"
				+ "      \"path\": \"/user\",\r\n" + "      \"description\": \"Operations about user\"\r\n"
				+ "    },\r\n" + "    {\r\n" + "      \"path\": \"/store\",\r\n"
				+ "      \"description\": \"Operations about store\"\r\n" + "    }\r\n" + "  ],\r\n"
				+ "  \"authorizations\": {\r\n" + "    \"oauth2\": {\r\n" + "      \"type\": \"oauth2\",\r\n"
				+ "      \"scopes\": [\r\n" + "        {\r\n" + "          \"scope\": \"email\",\r\n"
				+ "          \"description\": \"Access to your email address\"\r\n" + "        },\r\n" + "        {\r\n"
				+ "          \"scope\": \"pets\",\r\n" + "          \"description\": \"Access to your pets\"\r\n"
				+ "        }\r\n" + "      ],\r\n" + "      \"grantTypes\": {\r\n" + "        \"implicit\": {\r\n"
				+ "          \"loginEndpoint\": {\r\n"
				+ "            \"url\": \"http://petstore.swagger.wordnik.com/oauth/dialog\"\r\n" + "          },\r\n"
				+ "          \"tokenName\": \"access_token\"\r\n" + "        },\r\n"
				+ "        \"authorization_code\": {\r\n" + "          \"tokenRequestEndpoint\": {\r\n"
				+ "            \"url\": \"http://petstore.swagger.wordnik.com/oauth/requestToken\",\r\n"
				+ "            \"clientIdName\": \"client_id\",\r\n"
				+ "            \"clientSecretName\": \"client_secret\"\r\n" + "          },\r\n"
				+ "          \"tokenEndpoint\": {\r\n"
				+ "            \"url\": \"http://petstore.swagger.wordnik.com/oauth/token\",\r\n"
				+ "            \"tokenName\": \"access_code\"\r\n" + "          }\r\n" + "        }\r\n" + "      }\r\n"
				+ "    }\r\n" + "  },\r\n" + "  \"info\": {\r\n" + "    \"title\": \"Swagger Sample App\",\r\n"
				+ "    \"description\": \"This is a sample server Petstore server.  You can find out more about Swagger \\n    at <a href=\\\"http://swagger.wordnik.com\\\">http://swagger.wordnik.com</a> or on irc.freenode.net, #swagger.  For this sample,\\n    you can use the api key \\\"special-key\\\" to test the authorization filters\",\r\n"
				+ "    \"termsOfServiceUrl\": \"http://helloreverb.com/terms/\",\r\n"
				+ "    \"contact\": \"apiteam@wordnik.com\",\r\n" + "    \"license\": \"Apache 2.0\",\r\n"
				+ "    \"licenseUrl\": \"http://www.apache.org/licenses/LICENSE-2.0.html\"\r\n" + "  }\r\n" + "}";
		JsonNode jsonNode2 = objectMapper.readTree(oas1);

		lint.getVersion(jsonNode2);
		JsonNode version2= jsonNode2.get("swaggerVersion");
		assertEquals("\"1.2\"",version2.toString());

	}
	

}