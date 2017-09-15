package com.example.dell.komsicandelachallenge.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class is model class
 */

public class GitHubUser implements Parcelable {

    public static final Creator<GitHubUser> CREATOR = new Creator<GitHubUser>() {
        @Override
        public GitHubUser createFromParcel(Parcel in) {
            return new GitHubUser(in);
        }

        @Override
        public GitHubUser[] newArray(int size) {
            return new GitHubUser[size];
        }
    };

    //the user username
    @SerializedName("login")
    @Expose
    private String login;

    //the link to user profile photo
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    //the link to user profile htmlUrl
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;

    protected GitHubUser(Parcel in) {
        login = in.readString();
        avatarUrl = in.readString();
        htmlUrl = in.readString();
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeString(avatarUrl);
        dest.writeString(htmlUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
