package com.example.myig;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myig.models.Post;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.Date;

public class DetailedPostActivity extends AppCompatActivity {

     Post post;

    private Button btnReturn;
    private TextView tvUser;
    private ImageView ivPost;
    private TextView tvDesc;
    private TextView tvTime;
    private Date createdAt;
    private String timeAgo;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_post);

        tvUser = findViewById(R.id.tvUser);
        ivPost = findViewById(R.id.ivPost);
        tvDesc = findViewById(R.id.tvDesc);
        tvTime = findViewById(R.id.tvTime);
        btnReturn = findViewById(R.id.btnReturn);
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        tvDesc.setText(post.getDescription());
        tvUser.setText(post.getUser().getUsername());
        ParseFile image = post.getImage();
        if(image != null) {
            Glide.with(this).load(post.getImage().getUrl()).into(ivPost);
        }
        createdAt = post.getCreatedAt();
        timeAgo = post.calculateTimeAgo(createdAt);
        tvTime.setText(timeAgo);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });
    }

    public void goToMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
