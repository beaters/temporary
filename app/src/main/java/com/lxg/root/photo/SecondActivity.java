package com.lxg.root.photo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.io.File;

/**
 * Created by root on 15-10-21.
 */
public class SecondActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        GridView gridView=(GridView)findViewById(R.id.id_gridView2);
        Intent intent=getIntent();
        DATA data=(DATA)intent.getExtras().getSerializable("data");
        String path=data.getPath();
        File file=data.getFile();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
