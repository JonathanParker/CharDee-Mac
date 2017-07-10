package com.game.jason.chardeemacdennis20170417;

import java.io.Serializable;

/**
 * Created by Jason on 4/4/2017.
 */


class Data implements Serializable {

    private String description;
    private String title;
    private String imagePath;

    public Data(String imagePath, String description, String title) {
        this.imagePath = imagePath;
        this.description = description;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

}