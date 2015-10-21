package com.lxg.root.photo;

/**
 * Created by root on 15-10-21.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.Toast;

import utils.ImageLoader;

public class MyAdapter extends BaseAdapter
{

    private Context mContext;
    private LinkedList<String> mData;
    private List<File> mList;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;

    public MyAdapter(Context context, LinkedList<String> mData, List<File> mList)
    {
        this.mContext = context;
        this.mData = mData;
        this.mList = mList;
        mInflater = LayoutInflater.from(mContext);

        mImageLoader = ImageLoader.getInstance(3 , ImageLoader.Type.LIFO);
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
        holder.mImageView
                .setImageResource(R.drawable.pictures_no);

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DATA data=new DATA();
                String path=mData.get(position);
                File file=mList.get(position);
                data.setFile(file);
                data.setPath(path);
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putSerializable("data", data);
                intent.putExtras(bundle);
                intent.setAction("com.lxg.root.secondAcctivity");
                mContext.getApplicationContext().startActivity(intent);
            }
        });
        return convertView;
    }

    private final class ViewHolder
    {
        ImageView mImageView;
    }

}
