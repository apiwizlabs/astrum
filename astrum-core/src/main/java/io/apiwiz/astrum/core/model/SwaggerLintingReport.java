package io.apiwiz.astrum.core.model;


import com.fasterxml.jackson.annotation.JsonInclude;

public class SwaggerLintingReport {

    private String ruleName;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer lineNumber;
    private String severity;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

}
