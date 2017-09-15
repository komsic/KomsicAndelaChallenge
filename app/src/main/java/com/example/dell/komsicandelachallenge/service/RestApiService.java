package com.example.dell.komsicandelachallenge.service;

import com.example.dell.komsicandelachallenge.model.GitHubUserList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This is where the end point is declared.
 */

public interface RestApiService {
    @GET("search/users")
    Call<GitHubUserList> getGitHubUserList(@Query("q") String filter);
}
