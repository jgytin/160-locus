package com.example.jt.locus_draft;

import java.io.Serializable;

/**
 * Created by MZ on 4/26/18.
 */

public class PhotoLoc implements Serializable {

    String name;
    double lat;
    double lng;
    String file;

    public PhotoLoc(String name, double lat, double lng, String file) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.file = file;
    }

    @Override
    public boolean equals(Object pl) {
        return this.name.equals(((PhotoLoc) pl).name);
    }

}
