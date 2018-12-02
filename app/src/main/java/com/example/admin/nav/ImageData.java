package com.example.admin.nav;

import java.io.Serializable;

public class ImageData implements Serializable {
    private String img;

    public ImageData() {
    }

    public ImageData(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
