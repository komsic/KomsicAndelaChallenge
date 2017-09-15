package com.example.dell.komsicandelachallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 9/13/2017.
 */

public class GitHubUserList {
    @SerializedName("items")
    @Expose
    private List<GitHubUser> items = null;

    public List<GitHubUser> getItems() {
        return items;
    }
}
