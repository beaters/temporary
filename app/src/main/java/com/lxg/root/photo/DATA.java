package com.lxg.root.photo;

import java.io.File;
import java.io.Serializable;

/**
 * Created by root on 15-10-21.
 */
public class DATA implements Serializable
{
    private String path;
    private File file;

    public File getFile() {
        return file;
    }

    public String getPath() {
        return path;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setPath(String path) {
        this.path = path;
    }
}