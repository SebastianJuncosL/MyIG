package com.example.myig;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.Date;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUser;
        private ImageView ivPost;
        private TextView tvDesc;
        private TextView tvTime;
        private Date createdAt;
        private String timeAgo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.tvUser);
            ivPost = itemView.findViewById(R.id.ivPost);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvTime = itemView.findViewById(R.id.tvTime);
        }

        public void bind(Post post) {
            // Bind the post data to the view elements
            tvDesc.setText(post.getDescription());
            tvUser.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if(image != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivPost);
            }
            createdAt = post.getCreatedAt();
            timeAgo = calculateTimeAgo(createdAt);
            tvTime.setText(timeAgo);

        }

        public String calculateTimeAgo(Date createdAt) {

            int SECOND_MILLIS = 1000;
            int MINUTE_MILLIS = 60 * SECOND_MILLIS;
            int HOUR_MILLIS = 60 * MINUTE_MILLIS;
            int DAY_MILLIS = 24 * HOUR_MILLIS;

            try {
                createdAt.getTime();
                long time = createdAt.getTime();
                long now = System.currentTimeMillis();

                final long diff = now - time;
                if (diff < MINUTE_MILLIS) {
                    return "just now";
                } else if (diff < 2 * MINUTE_MILLIS) {
                    return "a minute ago";
                } else if (diff < 50 * MINUTE_MILLIS) {
                    return diff / MINUTE_MILLIS + " m ago";
                } else if (diff < 90 * MINUTE_MILLIS) {
                    return "an hour ago";
                } else if (diff < 24 * HOUR_MILLIS) {
                    return diff / HOUR_MILLIS + " h ago";
                } else if (diff < 48 * HOUR_MILLIS) {
                    return "yesterday";
                } else {
                    return diff / DAY_MILLIS + " d ago";
                }
            } catch (Exception e) {
                Log.i("Error:", "getRelativeTimeAgo failed", e);
                e.printStackTrace();
            }

            return "";
        }
    }
}
