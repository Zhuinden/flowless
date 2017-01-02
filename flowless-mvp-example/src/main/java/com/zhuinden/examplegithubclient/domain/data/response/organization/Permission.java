package com.zhuinden.examplegithubclient.domain.data.response.organization;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Owner on 2016.12.10.
 */
@JsonObject
public class Permission {
    @JsonField(name = "admin")
    private Boolean admin;
    @JsonField(name = "push")
    private Boolean push;
    @JsonField(name = "pull")
    private Boolean pull;

    /**
     * @return The admin
     */
    public Boolean getAdmin() {
        return admin;
    }

    /**
     * @param admin The admin
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    /**
     * @return The push
     */
    public Boolean getPush() {
        return push;
    }

    /**
     * @param push The push
     */
    public void setPush(Boolean push) {
        this.push = push;
    }

    /**
     * @return The pull
     */
    public Boolean getPull() {
        return pull;
    }

    /**
     * @param pull The pull
     */
    public void setPull(Boolean pull) {
        this.pull = pull;
    }
}
