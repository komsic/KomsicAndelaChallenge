package com.example.dell.komsicandelachallenge.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dell.komsicandelachallenge.R;
import com.example.dell.komsicandelachallenge.adapter.GitHubUserRecyclerAdapter;
import com.example.dell.komsicandelachallenge.model.GitHubUserList;
import com.example.dell.komsicandelachallenge.service.RestApiClient;
import com.example.dell.komsicandelachallenge.service.RestApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;

    /** Adapter for the list of github users */
    private GitHubUserRecyclerAdapter mAdapter;

    private RecyclerView mRecyclerView;

    NetworkInfo activeNetworkInfo;

    LinearLayout linearLayout;
    Button button;

    private static final int DISPLAY_ERROR_PAGE = 1;
    private static final int HIDE_ERROR_PAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.retry_button);
        linearLayout = (LinearLayout) findViewById(R.id.error_root_layout);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_indicator);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ConnectivityManager cm = (ConnectivityManager) getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = cm.getActiveNetworkInfo();
        networkValidation();
    }

    private void fetchUserInfo() {

        /**
         * query for github user data from the github api
         */
        final String REQUEST_QUERY = "type:user location:lagos language:java";

        RestApiService apiService = new RestApiClient().getClient().create(RestApiService.class);
        Call<GitHubUserList> userListCall = apiService.getGitHubUserList(REQUEST_QUERY);
        userListCall.enqueue(new Callback<GitHubUserList>() {
            @Override
            public void onResponse(Call<GitHubUserList> call, Response<GitHubUserList> response) {
                if(response.isSuccessful()){
                    GitHubUserList userList = response.body();
                    mProgressBar.setVisibility(View.GONE);
                    prepareData(userList);
                }
            }

            @Override
            public void onFailure(Call<GitHubUserList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error Processing Request",
                        Toast.LENGTH_SHORT).show();
                shouldDisplayErrorPage(DISPLAY_ERROR_PAGE);
            }
        });
    }

    private void prepareData(GitHubUserList userList) {
        mAdapter = new GitHubUserRecyclerAdapter(userList.getItems());
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * this method handles the display error of the page.
     */
    private void networkValidation() {

        shouldDisplayErrorPage(HIDE_ERROR_PAGE);


        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            fetchUserInfo();
        } else {
            shouldDisplayErrorPage(DISPLAY_ERROR_PAGE);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shouldDisplayErrorPage(HIDE_ERROR_PAGE);
                    fetchUserInfo();
                    Toast.makeText(MainActivity.this, "Retry Button Clicked",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void shouldDisplayErrorPage(int status) {
        if (status == HIDE_ERROR_PAGE) {
            button.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        if (status == DISPLAY_ERROR_PAGE) {
            mProgressBar.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }
    }
}
