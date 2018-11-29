package org.fonuhuolian.nohttp;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.fonuhuolian.xnohttp.XNohttpServer;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        XNohttpServer.setImageLoader(new XNohttpServer.ImageLoader() {
            @Override
            public void onLoadGifImage(Context context, ImageView imageView, int gifResId) {
                Glide.with(context).load(gifResId).into(imageView);
            }
        });
    }
}
