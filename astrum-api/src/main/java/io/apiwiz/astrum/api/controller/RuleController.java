package io.apiwiz.astrum.api.controller;


import io.apiwiz.astrum.api.service.RuleService;
import io.apiwiz.astrum.core.model.SwaggerLintingOutput;
import io.apiwiz.astrum.rule.model.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/astrum/")
public class RuleController {

	@Autowired
    private RuleService ruleService;

	@RequestMapping(value = "/rule", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> createRules(@RequestBody Rule rule) {
		ruleService.save(rule);
		return new ResponseEntity(HttpStatus.CREATED);

	}

	@RequestMapping(value = "/rule", method = RequestMethod.PUT)
	public ResponseEntity<HttpStatus> updateRules(@RequestBody Rule rule) {
		 ruleService.update(rule);
		 return new ResponseEntity(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/rule/{ruleId}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteRules(@PathVariable String ruleId) {
		ruleService.delete(ruleId);
		return new ResponseEntity(HttpStatus.ACCEPTED);

	}

	@RequestMapping(value = "/rule/{ruleId}", method = RequestMethod.GET)
	public Rule getRuleByIdOrName(@PathVariable String ruleId) {
		return ruleService.getRuleById(ruleId);
	}

	@RequestMapping(value = "/rule/", method = RequestMethod.GET)
	private ResponseEntity<List<Rule>> getAllRules() {
		return new ResponseEntity<>(ruleService.getAllRules(), HttpStatus.OK);
	}

	@RequestMapping(value = "/lint", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	private ResponseEntity<?> lint(@RequestPart(value = "swaggerFile") MultipartFile swaggerFile) {
		return new ResponseEntity<>(ruleService.lint(swaggerFile), HttpStatus.OK);
	}

	@RequestMapping(value = "/format", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	private ResponseEntity<?> formatSwaggerFile(@RequestPart(value = "swaggerFile") MultipartFile swaggerFile) {
		return new ResponseEntity<>(ruleService.format(swaggerFile), HttpStatus.OK);
	}

}
