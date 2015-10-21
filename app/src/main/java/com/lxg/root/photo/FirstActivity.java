package com.lxg.root.photo;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

public class FirstActivity extends Activity
{
    private ProgressDialog mProgressDialog;

    /**
     * 防止同一个文件夹的多次扫描
     */
    private LinkedList<String> mDirPaths = new LinkedList<String>();
    private LinkedList<File> mFileList;
    private GridView mGirdView;
    private ListAdapter mAdapter;
    private Handler mHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        mGirdView = (GridView) findViewById(R.id.id_gridView);

        mHandler = new Handler()
        {
            public void handleMessage(android.os.Message msg)
            {
                mProgressDialog.dismiss();
                mAdapter = new MyAdapter(getApplicationContext(), mDirPaths,mFileList);
                mGirdView.setAdapter(mAdapter);

        getImages();

            }
        };
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描
     */
    private void getImages()
    {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
        {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = FirstActivity.this
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] { "image/jpeg", "image/png" },
                        MediaStore.Images.Media.DATE_MODIFIED);

                while (mCursor.moveToNext())
                {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    String dirPath = parentFile.getAbsolutePath();


                    if(mDirPaths.contains(dirPath))
                    {
                        continue;
                    }
                    else
                    {
                        mDirPaths.add(dirPath);
                        mFileList.add(parentFile);
                    }

                }
                mCursor.close();
                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);

            }
        }).start();

    }
}