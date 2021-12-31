package io.apiwiz.astrum.rule.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Swagger.LinterRules")
public class Rule {

    @Id
    private String id;
    private String name;
    private String description;
    private String oasVersion;
    private Status status;
    private String objectPath;
    private Severity severity;
    private SwaggerLinterRuleType ruleType;
    private long cts;
    private long mts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOasVersion() {
        return oasVersion;
    }

    public void setOasVersion(String oasVersion) {
        this.oasVersion = oasVersion;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getObjectPath() {
        return objectPath;
    }

    public void setObjectPath(String objectPath) {
        this.objectPath = objectPath;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public long getCts() {
        return cts;
    }

    public void setCts(long cts) {
        this.cts = cts;
    }

    public long getMts() {
        return mts;
    }

    public void setMts(long mts) {
        this.mts = mts;
    }

    public SwaggerLinterRuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(SwaggerLinterRuleType ruleType) {
        this.ruleType = ruleType;
    }
}