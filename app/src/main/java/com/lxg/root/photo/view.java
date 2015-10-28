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
import android.widget.Toast;

import java.io.File;
import java.util.List;

/**
 * Created by root on 15-10-21.
 * @author lxg lxgvisual@163.com
 */
public class view extends Activity{
    private ImageButton back;
    private ImageButton delete;
    private ImageView imageView;
    private Context context;
    private String mPath;//当前文件父文件路径
    private Intent intent;
    private int position;
    private List<String> mFile;
    private String path;//当前文件路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);
        init();
        setImage(imageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        delete.setOnClickListener(new MyClickListener());
    }

    public void init()
    {
        back=(ImageButton)findViewById(R.id.back);
        delete=(ImageButton)findViewById(R.id.delete);
        imageView=(ImageView)findViewById(R.id.view);
        context=getBaseContext();
        intent=getIntent();
        Bundle bundle=intent.getExtras();
        DATA data=(DATA)bundle.getSerializable("DATA");
        mPath=data.getmPath();
        mFile=data.getmFile();
        position=data.getPosition();
        path=mPath+"/"+mFile.get(position);
    }
    public void setImage(ImageView image)
    {
        ImageView i=image;
        Bitmap bitmap= BitmapFactory.decodeFile(path);
        i.setImageBitmap(bitmap);
    }
    public void showNext(ImageView image1)
    {
        ImageView imageView=image1;
        String path;
        position+=1;

        if(position<=mFile.size()) {
             path= mPath + "/" + mFile.get(position);
        }else
            path=mPath + "/" + mFile.get(0);

        Bitmap bitmapNext=BitmapFactory.decodeFile(path);
        imageView.setImageBitmap(bitmapNext);
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
            showDialog();
        }
    }

    public void showDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("删除")
                .setMessage("确定删除吗?");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    File fileToDelete = new File(path);
                    fileToDelete.delete();
                    if(fileToDelete.getParentFile().list().length!=0) {
                        showNext(imageView);
                    }else
                        onBackPressed();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(context, "您点击了取消", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });
        AlertDialog ad=builder.create();
        ad.show();
    }
}
