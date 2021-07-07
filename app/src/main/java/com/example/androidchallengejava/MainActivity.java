package com.example.androidchallengejava;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidchallengejava.models.MediaObject;
import com.example.androidchallengejava.util.VerticalSpacingItemDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private com.example.androidchallengejava.VideoPlayerRecyclerView mRecyclerView;
    private final OkHttpClient client = new OkHttpClient();
    private ArrayList<MediaObject> mMediaObjects = new ArrayList<MediaObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        getVOCPage();
    }

    private void initRecyclerView(){

        System.out.println("hello " + mMediaObjects);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);

        //here is where you'll query the the DB

        ArrayList<MediaObject> mediaObject = new ArrayList<MediaObject>(mMediaObjects);
        mRecyclerView.setMediaObjects(mediaObject);

        com.example.androidchallengejava.VideoPlayerRecyclerAdapter adapter = new com.example.androidchallengejava.VideoPlayerRecyclerAdapter(mediaObject, initGlide());
        mRecyclerView.setAdapter(adapter);
    }

        public void getVOCPage() {

            Request request = new Request.Builder()
                    .url("https://api.pac-12.com/v3/vod")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {

                    e.printStackTrace();
                }
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                        Headers responseHeaders = response.headers();
                        for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }
                        String jsonData = responseBody.string();
                        JSONObject Jobject = new JSONObject(jsonData);
                        JSONArray Jarray = Jobject.getJSONArray("programs");

                        System.out.print("size is " + Jarray.length());


                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object = Jarray.getJSONObject(i);

                            MediaObject obj = new MediaObject(object.getString("title"),
                                    object.getString("manifest_url"),
                                    object.getJSONObject("images").getString("medium"),
                                    object.getString("title"),
                                    object.getInt("duration"),
                                    new JSONArray(),
                                    new JSONArray(),
                                   "",
                                   "");

                            mMediaObjects.add(obj);

                        }


                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // Stuff that updates the UI
                                initRecyclerView();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        private String getSchoolName(String schoolId){

            final String[] name = {""};

            Request request = new Request.Builder()
                    .url("https://api.pac-12.com/v3/schools/" + schoolId)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful())
                            throw new IOException("Unexpected code " + response);

                        Headers responseHeaders = response.headers();
                        for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }
                        name[0] = responseBody.string();

                    }
                }
            });

            return name[0];

        }



    private RequestManager initGlide(){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background);

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }


    @Override
    protected void onDestroy() {
        if(mRecyclerView!=null)
            mRecyclerView.releasePlayer();
        super.onDestroy();
    }
}

















