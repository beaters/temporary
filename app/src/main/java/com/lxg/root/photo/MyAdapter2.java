package com.lxg.root.photo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import utils.ImageLoader;

/**
 * Created by root on 15-10-21.
 */
public class MyAdapter2 extends BaseAdapter{
    private Context mContext;
    private String mPath;
    private List<String> mFile;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    public MyAdapter2(Context context,String path,List<String> file)
    {
        mContext=context;
        mPath=path;
        mFile=file;
        mInflater=LayoutInflater.from(mContext);
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
    public View getView(int position, View convertView, ViewGroup parent) {
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
        return convertView;

    }
    private final class ViewHolder
    {
        ImageView mImageView;
    }
}
