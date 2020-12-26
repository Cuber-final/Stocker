package com.example.stocker.stockOpe;

import androidx.annotation.NonNull;

public class profileStock {
    //股票代码
    private String stockCode;
    //股票名称
    private String stockName;
    //    最新价
    private String newestPrice;
    //    涨跌幅
    private String Zdf;
    //    涨跌额
    private String Zde;
    //    成交额
    private String volume;

    private String codeUrl;

    public profileStock() {
    }

    public profileStock(String stockCode, String stockName, String newestPrice, String Zdf, String Zde, String volume, String codeUrl) {

//       首页股票列表浏览所用
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.newestPrice = newestPrice;
        this.Zdf = Zdf;
        this.Zde = Zde;
        this.volume = volume;
        this.codeUrl = codeUrl;
    }

    public profileStock(String newestPrice, String Zdf, String Zde) {
//        三个主要指数,szh,sz,cz;
        this.newestPrice = newestPrice;
        this.Zdf = Zdf;
        this.Zde = Zde;
    }

    public void setZde(String zde) {
        Zde = zde;
    }


    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public void setNewestPrice(String newestPrice) {
        this.newestPrice = newestPrice;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setZdf(String zdf) {
        Zdf = zdf;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getStockName() {
        return stockName;
    }

    public String getNewestPrice() {
        return newestPrice;
    }

    public String getStockCode() {
        return stockCode;
    }

    public String getVolume() {
        return volume;
    }

    public String getZdf() {
        return Zdf;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public String getZde() {
        return Zde;
    }

    @NonNull
    @Override
    public String toString() {
        return "profileStock{" +
                "股票代码='" + stockCode + '\'' +
                ", 股票名称='" + stockName + '\'' +
                ", 最新价='" + newestPrice + '\'' +
                ", 涨跌幅='" + Zdf + '\'' +
                ", 交易额='" + volume + '\'' +
                ", 代码地址='" + volume + '\'' +
                '}';
    }
}
