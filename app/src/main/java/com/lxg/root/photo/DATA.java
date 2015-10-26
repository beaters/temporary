package com.lxg.root.photo;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 15-10-21.
 * @author lxg lxgvisual@163.com
 */
public class DATA implements Serializable
{
    private int position;
    private String mPath;
    private List<String> mFile;

    public void setmFile(List<String> mFile) {
        this.mFile = mFile;
    }

    public List<String> getmFile() {
        return mFile;

    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getmPath() {
        return mPath;
    }
    public void setmPath(String path) {
        this.mPath = path;
    }
}