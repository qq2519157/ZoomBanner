package com.pppcar.zoombanner.entity;

public class BannerBean {

    /**
     * imgLinkUrl : http://www.pppcar.com/search?keyWord=racechip&vinCode=&tmpSearchVal=racechip
     * imgUrl : http://img.pppcar.com/image/getImage/5a5c6c934d5d392ebf3da4c9
     * title : RACECHIP app
     */

    private String imgLinkUrl;
    private String imgUrl;
    private String title;

    public String getImgLinkUrl() {
        return imgLinkUrl;
    }

    public void setImgLinkUrl(String imgLinkUrl) {
        this.imgLinkUrl = imgLinkUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
