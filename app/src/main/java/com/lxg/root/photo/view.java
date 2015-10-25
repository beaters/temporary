package com.lxg.root.photo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by root on 15-10-21.
 * @author lxg lxgvisual@163.com
 */
public class view extends Activity{
    private ImageButton back;
    private ImageButton delete;
    private ImageView imageView;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);
        back=(ImageButton)findViewById(R.id.back);
        delete=(ImageButton)findViewById(R.id.delete);
        imageView=(ImageView)findViewById(R.id.view);
        context=getBaseContext();
        setImage(imageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        delete.setOnClickListener(new MyClickListener());
    }


    public void setImage(ImageView image)
    {
        ImageView i=image;
        Intent intent=getIntent();
        String path=intent.getStringExtra("path");
        Bitmap bitmap= BitmapFactory.decodeFile(path);
        i.setImageBitmap(bitmap);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    class MyClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View V)
        {
            /*AlertDialog.Builder builder=new AlertDialog.Builder(context)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("删除")
                    .setMessage("确定删除吗?")
                    .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .create()
                    .show()*/

        }
    }
}
