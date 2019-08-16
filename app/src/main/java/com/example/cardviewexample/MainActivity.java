package com.example.cardviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);
        mListView = findViewById(R.id.listview);

        ArrayList<Cards> list = new ArrayList<>();
        list.add(new Cards("drawable://" + R.drawable.unsplash1,"My landscape"));
        list.add(new Cards("drawable://" + R.drawable.unsplash2,"My landscape"));
        list.add(new Cards("drawable://" + R.drawable.unsplash3,"My landscape"));
        list.add(new Cards("drawable://" + R.drawable.unsplash4,"My landscape"));
        list.add(new Cards("drawable://" + R.drawable.unsplash5,"My landscape"));
        list.add(new Cards("drawable://" + R.drawable.unsplash6,"My landscape"));
        list.add(new Cards("drawable://" + R.drawable.unsplash7,"My landscape"));
        list.add(new Cards("drawable://" + R.drawable.landscape,"My landscape"));

        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.activity_main, list);
        mListView.setAdapter(adapter);
    }
}
