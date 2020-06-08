package com.example.feetfall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.loopj.android.http.*;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ApiActivity extends AppCompatActivity {

    public static ImageView ivImg;
    public static Context cntx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        ivImg = findViewById(R.id.iv);
        cntx = this;

        String uris = "https://api.giphy.com/v1/gifs/random?api_key=1Gor3Owx7t1K4Sia7oXg9cc408bDYnKU";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(uris,null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String gif = "";
                try {
                    JSONObject data = response.getJSONObject("data");
                    gif = data.getString("image_original_url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Glide.with(cntx)
                        .load(gif)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(ApiActivity.ivImg);
            }

            @Override
            public void onFailure ( int statusCode, Header[] headers, String res, Throwable t){
                Log.wtf("JSON", "NO SE PUDO OBTENER EL GIF");
            }

        });
}

}
