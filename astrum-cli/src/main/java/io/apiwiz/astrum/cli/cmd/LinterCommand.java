package io.apiwiz.astrum.cli.cmd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.apiwiz.astrum.cli.dao.SwaggerLinterRulesInMemRepo;
import io.apiwiz.astrum.core.impl.SwaggerLinter;
import io.apiwiz.astrum.core.impl.ValidateSchema;
import io.apiwiz.astrum.core.model.SwaggerLintingReport;
import io.apiwiz.astrum.core.util.SwaggerLinterUtil;
import io.apiwiz.astrum.rule.model.Rule;
import org.apache.commons.io.FileUtils;
import picocli.CommandLine;
import static io.apiwiz.astrum.core.util.SwaggerLinterUtil.formatSwaggerStr;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name="astrum", version = "1.0", mixinStandardHelpOptions = true, description = "Reads the Swagger file and lints it using custom linting rules")
public class LinterCommand implements Callable<Integer> {

    @CommandLine.Option(names = {"-f", "-sf", "-swaggerFile"}, description = "The Swagger file to lint", required = true)
    private File swaggerFile;

    @CommandLine.Option(names={"-r", "-ruleFile"}, description = "The custom rules file based on which the swagger file will be linted")
    private File rulesFile;

    @CommandLine.Option(names={"-o", "-output"}, description = "The output file in which the linting report will be generated")
    private File outputFile;

    @CommandLine.Option(names={"-p", "-prettyPrint"}, description = "The input swaggerFile will be pretty printed to the console. This will allow to reference the line numbers of the linting report")
    private boolean prettyPrintSwagger;


    @CommandLine.Option(names={"-skipLint"}, description = "The linting will be skipped and the input swagger file will be pretty printed in console")
    private boolean skipLinting;


    @Override
    public Integer call() throws Exception {
        ValidateSchema validateSchema = new ValidateSchema();
        String swaggerString = FileUtils.readFileToString(swaggerFile, StandardCharsets.UTF_8);
        List<Rule> rules = null;
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

        if(skipLinting) {
            System.out.println(formatSwaggerStr(swaggerString));
            return 0;
        }


        if (rulesFile == null) {
            String rulesFileName = "rules.json";
            if(!SwaggerLinterUtil.isSwaggerVersion2(SwaggerLinterUtil.getVersion(validateSchema.readNode(swaggerString)))) {
                rulesFileName = "rulesOAS.json";
            }
            rules = Arrays.asList(objectMapper.readValue(readFromFile(rulesFileName), Rule[].class));
        } else {
            rules = Arrays.asList(objectMapper.readValue(rulesFile, Rule[].class));
        }

        SwaggerLinterRulesInMemRepo repo = new SwaggerLinterRulesInMemRepo(rules);

        SwaggerLinter linter = new SwaggerLinter(repo);

        List<SwaggerLintingReport> lintingReports = linter.lint(swaggerString);

        if(outputFile != null) {
            outputFile.createNewFile();
            objectWriter.writeValue(outputFile, lintingReports);
        } else {
            System.out.println(objectWriter.writeValueAsString(lintingReports));
        }

        writeFormattedSwaggerStr(swaggerString);

        return 0;
    }



    private void writeFormattedSwaggerStr(String swaggerString) throws IOException {
        ValidateSchema validateSchema = new ValidateSchema();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        JsonNode jsonNode = validateSchema.readNode(swaggerString);
        File formattedSwaggerFile = new File(System.getProperty("user.dir"), "formatted" + "_" + swaggerFile.getName());
        objectWriter.writeValue(formattedSwaggerFile, jsonNode);
    }


    private String readFromFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename)));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
            sb.append(line);
        br.close();
        return sb.toString();
    }
}
