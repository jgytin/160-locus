package com.example.jt.locus_draft;

/**
 * Created by User on 4/20/2018.
 */

public class Photo {

    private String mImgUrl;

    public Photo() {

    }

    public Photo(String img) {
        mImgUrl = img;
        System.out.println(img);
    }

    public String getImgUrl() {
        return mImgUrl;
    }

}
