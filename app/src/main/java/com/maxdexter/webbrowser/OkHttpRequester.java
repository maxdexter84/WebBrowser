package com.maxdexter.webbrowser;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http2.Http2Reader;

public class OkHttpRequester {

    private OnResponseCompleted listener;
    //The callback interface
    interface OnResponseCompleted{
        void onCompleted(String content);
    }

    public OkHttpRequester(OnResponseCompleted listener) {
        this.listener = listener;
    }

    public void run(String url){
        OkHttpClient client = new OkHttpClient(); //Client
        Request.Builder builder = new Request.Builder(); //Create a builder
        builder.url(url); //specify the server address

        Request request = builder.build(); //Building a query
        Call cal = client.newCall(request); //Putting the request in the queue
        cal.enqueue(new Callback() {
            //this handler is needed for syncing threads:
            //if you construct it, it will remember the stream and sync with it in the future
            Handler mHandler = new Handler();
            @Override//if it fails
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override //getting a response from the server
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String answer = Objects.requireNonNull(response.body()).string();
                //synchronizing the thread with the ui thread
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onCompleted(answer); //Calling the callback method
                    }
                });
            }
        });
    }
}
