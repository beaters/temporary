package com.lxg.root.photo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import utils.ImageLoader;

/**
 * Created by root on 15-10-21.
 * @author lxg lxgvisual@163.com
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
        gridView.setAdapter(new MyAdapter2(path,fileList));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * custom the MyAdapter2 extends BaseAdapter
     *
     */

    class MyAdapter2 extends BaseAdapter {
        private String mPath;
        private List<String> mFile;
        private LayoutInflater mInflater;
        private ImageLoader mImageLoader;

        public MyAdapter2(String path,List<String> file)
        {
            mPath=path;
            mFile=file;
            mInflater=LayoutInflater.from(getApplicationContext());
            mImageLoader = ImageLoader.getInstance(3, ImageLoader.Type.LIFO);
        }
        @Override
        public int getCount() {
            return mFile.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
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
            holder.mImageView
                    .setImageResource(R.drawable.pictures_no);
            //使用Imageloader去加载图片
            mImageLoader.loadImage(mPath + "/" + mFile.get(position),
                    holder.mImageView);


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i=position;
                    DATA data=new DATA();
                    data.setmFile(mFile);
                    data.setmPath(mPath);
                    data.setPosition(i);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("DATA",data);
                    Intent intent=new Intent(SecondActivity.this,view.class);
                    intent.putExtras(bundle);
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

}
