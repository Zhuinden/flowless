package com.zhuinden.examplegithubclient.domain.data.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Owner on 2016.12.10.
 */
@JsonObject
public class Owner {
    @JsonField(name = "login")
    private String login;
    @JsonField(name = "id")
    private Integer id;
    @JsonField(name = "avatar_url")
    private String avatarUrl;
    @JsonField(name = "gravatar_id")
    private String gravatarId;
    @JsonField(name = "url")
    private String url;
    @JsonField(name = "html_url")
    private String htmlUrl;
    @JsonField(name = "followers_url")
    private String followersUrl;
    @JsonField(name = "following_url")
    private String followingUrl;
    @JsonField(name = "gists_url")
    private String gistsUrl;
    @JsonField(name = "starred_url")
    private String starredUrl;
    @JsonField(name = "subscriptions_url")
    private String subscriptionsUrl;
    @JsonField(name = "organizations_url")
    private String organizationsUrl;
    @JsonField(name = "repos_url")
    private String reposUrl;
    @JsonField(name = "events_url")
    private String eventsUrl;
    @JsonField(name = "received_events_url")
    private String receivedEventsUrl;
    @JsonField(name = "type")
    private String type;
    @JsonField(name = "site_admin")
    private Boolean siteAdmin;

    /**
     * @return The login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login The login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * @param avatarUrl The avatar_url
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * @return The gravatarId
     */
    public String getGravatarId() {
        return gravatarId;
    }

    /**
     * @param gravatarId The gravatar_id
     */
    public void setGravatarId(String gravatarId) {
        this.gravatarId = gravatarId;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The htmlUrl
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     * @param htmlUrl The html_url
     */
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    /**
     * @return The followersUrl
     */
    public String getFollowersUrl() {
        return followersUrl;
    }

    /**
     * @param followersUrl The followers_url
     */
    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }

    /**
     * @return The followingUrl
     */
    public String getFollowingUrl() {
        return followingUrl;
    }

    /**
     * @param followingUrl The following_url
     */
    public void setFollowingUrl(String followingUrl) {
        this.followingUrl = followingUrl;
    }

    /**
     * @return The gistsUrl
     */
    public String getGistsUrl() {
        return gistsUrl;
    }

    /**
     * @param gistsUrl The gists_url
     */
    public void setGistsUrl(String gistsUrl) {
        this.gistsUrl = gistsUrl;
    }

    /**
     * @return The starredUrl
     */
    public String getStarredUrl() {
        return starredUrl;
    }

    /**
     * @param starredUrl The starred_url
     */
    public void setStarredUrl(String starredUrl) {
        this.starredUrl = starredUrl;
    }

    /**
     * @return The subscriptionsUrl
     */
    public String getSubscriptionsUrl() {
        return subscriptionsUrl;
    }

    /**
     * @param subscriptionsUrl The subscriptions_url
     */
    public void setSubscriptionsUrl(String subscriptionsUrl) {
        this.subscriptionsUrl = subscriptionsUrl;
    }

    /**
     * @return The organizationsUrl
     */
    public String getOrganizationsUrl() {
        return organizationsUrl;
    }

    /**
     * @param organizationsUrl The organizations_url
     */
    public void setOrganizationsUrl(String organizationsUrl) {
        this.organizationsUrl = organizationsUrl;
    }

    /**
     * @return The reposUrl
     */
    public String getReposUrl() {
        return reposUrl;
    }

    /**
     * @param reposUrl The repos_url
     */
    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }

    /**
     * @return The eventsUrl
     */
    public String getEventsUrl() {
        return eventsUrl;
    }

    /**
     * @param eventsUrl The events_url
     */
    public void setEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
    }

    /**
     * @return The receivedEventsUrl
     */
    public String getReceivedEventsUrl() {
        return receivedEventsUrl;
    }

    /**
     * @param receivedEventsUrl The received_events_url
     */
    public void setReceivedEventsUrl(String receivedEventsUrl) {
        this.receivedEventsUrl = receivedEventsUrl;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The siteAdmin
     */
    public Boolean getSiteAdmin() {
        return siteAdmin;
    }

    /**
     * @param siteAdmin The site_admin
     */
    public void setSiteAdmin(Boolean siteAdmin) {
        this.siteAdmin = siteAdmin;
    }

}

