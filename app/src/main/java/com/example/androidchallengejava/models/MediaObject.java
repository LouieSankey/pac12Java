package com.example.androidchallengejava.models;

import org.json.JSONArray;

public class MediaObject {

    private String title;
    private String media_url;
    private String thumbnail;
    private String description;
    private Integer duration;
    private JSONArray schoolIds;
    private JSONArray sportIds;
    private String schools;
    private String sports;


    //this will be the fields you need for the video

    public MediaObject(String title, String media_url, String thumbnail, String description, Integer duration, JSONArray schoolIDs, JSONArray sportIds, String schools, String sports) {
        this.title = title;
        this.media_url = media_url;
        this.thumbnail = thumbnail;
        this.description = description;
        this.duration = duration;
        this.schoolIds = schoolIDs;
        this.sportIds = sportIds;
        this.schools = schools;
        this.sports = sports;

    }

    public MediaObject() {
    }

    public String getSchools() {
        return schools;
    }

    public void setSchools(String schools) {
        this.schools = schools;
    }

    public String getSports() {
        return sports;
    }

    public void setSports(String sports) {
        this.sports = sports;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public JSONArray getSchoolIds() {
        return schoolIds;
    }

    public void setSchoolIds(JSONArray schoolIds) {
        this.schoolIds = schoolIds;
    }

    public JSONArray getSportIds() {
        return sportIds;
    }

    public void setSportIds(JSONArray sportIds) {
        this.sportIds = sportIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}










