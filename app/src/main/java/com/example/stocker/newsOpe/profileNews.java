package com.example.stocker.newsOpe;

import androidx.annotation.NonNull;

public class profileNews {
    //    新闻标题
    private String newsTitle;
    //      文章网页地址
    private String newsUrl;
    //    爬取图片地址，直接通过图片地址加载
    private String imgUrl;
    //    新闻发布时间
    private String publish_date;
    //      资讯来源媒体
    private String sourceMedia;

//    在新闻资讯列表通过设置RecycleView保存爬取数据，若要访问文章，则需要通过点击每一文章创建新活动(需要新活动的WebView布局,直接打开网址即可)

    public profileNews() {
    }

    public profileNews(String newsTitle, String newsUrl, String imgUrl,String publish_date, String sourceMedia) {
        this.newsTitle = newsTitle;
        this.newsUrl=newsUrl;
        this.imgUrl = imgUrl;
        this.publish_date = publish_date;
        this.sourceMedia = sourceMedia;
//        暂时用到这些，后续还需加入图片地址
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getSourceMedia() {
        return sourceMedia;
    }

    public void setSourceMedia(String sourceMedia) {
        this.sourceMedia = sourceMedia;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return "GetNews{" +
                "新闻标题='" + newsTitle + '\'' +
                ", 文章地址='" + newsUrl + '\'' +
                ", 图片源='" + imgUrl + '\'' +
                ", 发布时间='" + publish_date + '\'' +
                ", 媒体='" + sourceMedia + '\'' +
                '}';
    }
}
