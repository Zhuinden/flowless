package com.zhuinden.examplegithubclient.domain.data.response.error;

/**
 * Created by Owner on 2016.12.10.
 */

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class ErrorResponse {
    @JsonField(name = "message")
    private String message;
    @JsonField(name = "errors")
    private List<Error> errors = null;
    @JsonField(name = "documentation_url")
    private String documentationUrl;

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The errors
     */
    public List<Error> getErrors() {
        return errors;
    }

    /**
     * @param errors The errors
     */
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }
}
