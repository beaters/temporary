package com.lxg.root.photo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

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
        String path=intent.getStringExtra("path");
        List<String> fileList= Arrays.asList(
                new File(path).list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        if(filename.toLowerCase().endsWith(".jpg"))
                            return true;
                        else
                            return false;
                    }
                })
        );
        gridView.setAdapter(new MyAdapter2(getApplicationContext(),path,fileList));
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
