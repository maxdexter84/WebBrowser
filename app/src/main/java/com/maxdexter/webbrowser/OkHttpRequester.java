package com.maxdexter.webbrowser;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpRequester {
    private OnResponseCompleted listener;//Экземпляр интерфейса с обратным вызовом
    //Интерфейс с обратным вызовом
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
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }
}
