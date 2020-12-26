package com.example.stocker.newsOpe;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


//爬取新闻的具体过滤操作
public class getNews {

    //    爬取json文件方式


    //        直接解析网页方式
    public static ArrayList<profileNews> parseNews(String newsHtml) {
        ArrayList<profileNews> newsDetails = new ArrayList<>();
        Document document = Jsoup.parse(newsHtml);
//        分析网页结构进行筛选
//        获取新闻集合
        Elements elements = document.select("ul[id=newsListContent]").select("li");
        String newsTitle, newsUrl, imgUrl, publish_date, sourceMedia, newsBrief, httpStr;
        String hasBiosBlock, noBiosBlock, biosBlock, titleBlock, briefBlock, timeBlock;
        hasBiosBlock = "div[class=text]";

        noBiosBlock = "div[class=text text-no-img]";
        biosBlock = "div[class=image]";

        titleBlock = "p[class=title]";
//        briefBlock = "p[class=info]";
        timeBlock = "p[class=time]";
        //遍历每条新闻的各项信息板块
        for (Element element : elements) {
//            根据每个板块的id进一步筛选
//            先判断有无文章标题图 bios
            imgUrl = element.select(biosBlock).select("a").select("img").attr("src");

            if (imgUrl.equals("")) {
//                若爬取的文章无图片
                newsTitle = element.select(noBiosBlock)
                        .select(titleBlock)
                        .select("a").text();
//                用默认图片做备用
                imgUrl = "http://z1.dfcfw.com/2020/12/21/20201221115504611804752.jpg";
                newsUrl = element.select(noBiosBlock)
                        .select(titleBlock)
                        .select("a")
                        .attr("href");
/*//            文章摘要，后有用
                newsBrief = element.select(noBiosBlock)
                        .select(briefBlock).text();*/
                //            发布时间
                publish_date = element.select(noBiosBlock)
                        .select(timeBlock).text();

            } else {
                //                若爬取的文章有图片
                httpStr = "http:";
                imgUrl = httpStr + imgUrl;
                newsTitle = element.select(hasBiosBlock)
                        .select(titleBlock)
                        .select("a").text();
//                用默认图片做备用
                newsUrl = element.select(hasBiosBlock)
                        .select(titleBlock)
                        .select("a")
                        .attr("href");
/*//            文章摘要，后有用
                newsBrief = element.select(hasBiosBlock)
                        .select(briefBlock).text();*/
                publish_date = element.select(hasBiosBlock)
                        .select(timeBlock).text();
            }
//            来源媒体,暂时统一使用
            sourceMedia = "新浪财经";
            profileNews newsDetail = new profileNews(newsTitle, newsUrl, imgUrl, publish_date, sourceMedia);
            newsDetails.add(newsDetail);
//            test输出一条股票完整信息
//            Log.i("get data is:", newsDetail.toString());
        }
        return newsDetails;
    }

}


