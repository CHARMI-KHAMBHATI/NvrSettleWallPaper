package com.kc.unsplashdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.kc.unsplash.Unsplash;
import com.kc.unsplash.api.Order;
import com.kc.unsplash.models.Photo;
import com.kc.unsplash.models.SearchResults;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String CLIENT_ID = "210a904c87abc4f7ea704b5f9749801e57018775f8a57fa55d425a1f11c85473";
    private Unsplash unsplash;
    private RecyclerView recyclerView;

    int max_images_to_load = 50;// total number of images to load

    int displayLimit=2; // number of images to be displayed in single row

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unsplash = new Unsplash(CLIENT_ID);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),displayLimit);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        unsplash.getPhotos(1, max_images_to_load, Order.POPULAR, new Unsplash.OnPhotosLoadedListener() {
            @Override
            public void onComplete(List<Photo> photos) {
                Log.d("Photos", "Photos Fetched " + photos.size());
                PhotoRecyclerAdapter adapter = new PhotoRecyclerAdapter(photos, MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void search(View view){
        EditText editText = (EditText) findViewById(R.id.editText);
        String query = editText.getText().toString();

        unsplash.searchPhotos(query, new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());
                List<Photo> photos = results.getResults();
                PhotoRecyclerAdapter adapter = new PhotoRecyclerAdapter(photos, MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });
    }
}
