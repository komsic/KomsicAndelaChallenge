package com.example.dell.komsicandelachallenge.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.komsicandelachallenge.R;
import com.example.dell.komsicandelachallenge.activity.GitHubUserDetailsActivity;
import com.example.dell.komsicandelachallenge.model.GitHubUser;

import java.util.List;

/**
 * Created by komsic on 9/13/2017.
 */

public class GitHubUserRecyclerAdapter extends RecyclerView.Adapter<GitHubUserRecyclerAdapter.GitHubUserViewHolder> {
    private List<GitHubUser> mGitHubUsers;

    public GitHubUserRecyclerAdapter(List<GitHubUser> gitHubUsers) {
        mGitHubUsers = gitHubUsers;
    }

    @Override
    public GitHubUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item,
                parent, false);
        return new GitHubUserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GitHubUserViewHolder holder, int position) {
        final GitHubUser gitHubUser = mGitHubUsers.get(position);

        holder.username.setText(gitHubUser.getLogin());

        final Context context = holder.profilePic.getContext();
        Glide.with(context).load(gitHubUser.getAvatarUrl()).into(holder.profilePic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GitHubUserDetailsActivity.class);
                intent.putExtra("githubUser", gitHubUser);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGitHubUsers.size();
    }

    public static class GitHubUserViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic;
        TextView username;

        public GitHubUserViewHolder(View itemView) {
            super(itemView);
            profilePic = (ImageView) itemView.findViewById(R.id.profile_pic);
            username = (TextView) itemView.findViewById(R.id.username);
        }
    }
}
