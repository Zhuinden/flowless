package com.zhuinden.examplegithubclient.domain.data.response.error;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class Error {
    @JsonField(name = "resource")
    private String resource;

    @JsonField(name = "field")
    private String field;

    @JsonField(name = "code")
    private String code;

    /**
     * @return The resource
     */
    public String getResource() {
        return resource;
    }

    /**
     * @param resource The resource
     */
    public void setResource(String resource) {
        this.resource = resource;
    }

    /**
     * @return The field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field The field
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return The code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(String code) {
        this.code = code;
    }

}