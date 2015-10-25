package com.lxg.root.photo;

/**
 *
 * @author lxg lxgvisual@163.com
 */
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import utils.ImageLoader;

public class FirstActivity extends Activity
{
    private ImageLoader mImageLoader;
    private ProgressDialog mProgressDialog;

    /**
     * 防止同一个文件夹的多次扫描
     */
    private LinkedList<String> mDirPaths = new LinkedList<String>();
    private GridView mGirdView;
    private ListAdapter mAdapter;

    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            mProgressDialog.dismiss();
            mAdapter = new MyAdapter(getApplicationContext(), mDirPaths);
            mGirdView.setAdapter(mAdapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        mGirdView = (GridView) findViewById(R.id.id_gridView);
        getImages();
    }

    /**
     * 利用ContentProvider扫描手机中的图片,此方法运行在子线程中,完成图片的扫描
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
                    }

                }
                mCursor.close();
                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);

            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    public class MyAdapter extends BaseAdapter
    {

        private Context mContext;
        private LinkedList<String> mData;
        private LayoutInflater mInflater;


        public MyAdapter(Context context, LinkedList<String> mData)
        {
            this.mContext = context;
            this.mData = mData;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount()
        {
            return mData.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent)
        {
            ViewHolder holder = null;
            if (convertView == null)
            {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.grid_item, parent,
                        false);
                holder.mImageView = (ImageView) convertView
                        .findViewById(R.id.id_item_image);
                convertView.setTag(holder);
            } else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            getFirstBitmap(position,holder.mImageView);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String path=mData.get(position);
                    Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
                    intent.putExtra("path",path);
                    startActivity(intent);
                }
            });
            return convertView;
        }

        private final class ViewHolder
        {
            ImageView mImageView;
        }

    }
    public void getFirstBitmap(int po,ImageView imageView)
    {
        int position=po;
        ImageView i=imageView;
        int a=0;
        String path=mDirPaths.get(position);
        File file_parent=new File(path);
        List<String> photoNames= Arrays.asList(file_parent.list());
        while(!photoNames.get(a).endsWith(".jpg"))
            a++;
        String photoname=photoNames.get(a);
        mImageLoader = ImageLoader.getInstance(3, ImageLoader.Type.LIFO);
        mImageLoader.loadImage(path+"/"+photoname,i);
    }
}