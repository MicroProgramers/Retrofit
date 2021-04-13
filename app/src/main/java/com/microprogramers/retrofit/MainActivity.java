package com.microprogramers.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.microprogramers.retrofit.Models.JsonPlaceHolderApi;
import com.microprogramers.retrofit.Models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView text_result;
    int i = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_result = findViewById(R.id.text_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response)
            {
                i++;
                Log.d("TAG", "onResponse: " + i + " times called");
                if (!response.isSuccessful())
                {
                    text_result.setText("Error Code: " +  response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post: posts)
                {
                    String content = "";
                    content += "ID: " + post.getId();
                    content += "\nUser ID: " + post.getUserId();
                    content += "\nTitle: " + post.getTitle();
                    content += "\nText: " + post.getText() + "\n\n";

                    text_result.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                text_result.setText(t.getMessage());
            }
        });
    }
}