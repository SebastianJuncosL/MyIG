package com.example.myig;

import android.app.Application;

import com.example.myig.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("DAMfnIW498OeO1Y7Qixr7ybB7mk1zLb5vZODPjW5")
                .clientKey("e2lN7qxPjUGEucZTFigHwKzw5TooWvVaY8Kj3j4u")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
