package com.example.covid_19pop_up;

public class info_class {
    String headimg;
    String text;

    public info_class(String headimg,String text) {
        this.headimg = headimg;
        this.text=text;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
}
