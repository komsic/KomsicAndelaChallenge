package com.example.dell.komsicandelachallenge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.komsicandelachallenge.R;
import com.example.dell.komsicandelachallenge.model.GitHubUser;

public class GitHubUserDetailsActivity extends AppCompatActivity {

    private GitHubUser mGitHubUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_user_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
        } else {
            mGitHubUser = getIntent().getParcelableExtra("githubUser");
        }

        TextView usernameTxt = (TextView) findViewById(R.id.username);
        usernameTxt.setText(mGitHubUser.getLogin());

        TextView urlTxt = (TextView) findViewById(R.id.url);
        urlTxt.setText(getString(R.string.user_profile_url,
                mGitHubUser.getHtmlUrl()));
        if (!urlTxt.getText().toString().isEmpty()) {
            Linkify.addLinks(urlTxt, Linkify.WEB_URLS);
        }

        ImageView profilePic = (ImageView) findViewById(R.id.profile_pic);
        Glide.with(getApplicationContext()).load(mGitHubUser.getAvatarUrl()).into(profilePic);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/*");
                intent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer @"
                        + mGitHubUser.getLogin() + ", " + mGitHubUser.getHtmlUrl());
                startActivity(intent);
            }
        });
    }

}
