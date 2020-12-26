package com.example.stocker.stockOpe;

import androidx.annotation.NonNull;

public class stockDetail {
    private String stockName;
    //    今日开盘价
    private String openPrice;
    //    昨日收盘价
    private String pre_closePrice;

    private String latest_price;
    //    最新价格
    private String latest_highest;
    //    当前最高价
    private String latest_lowest;
    //    当前最低价

    //成交量
    private String dealNum;
    //    成交金额
    private String dealMon;
    //    买一报价
    private String buyOne;
    //    买二报价
    private String buyTwo;
    //    买三报价
    private String buyThree;
    //    买四报价
    private String buyFour;
    //    买五报价
    private String buyFive;

    private String stock_date;
    //    股票日期
    private String stock_time;
    //    涨跌幅
    private String zdf;
    //涨跌额
    private String zde;
    //   换手率
    private String hs;

    public stockDetail() {
    }

    //收盘时间

    public String getHs() {
        return hs;
    }

    public String getZde() {
        return zde;
    }

    public String getStock_date() {
        return stock_date;
    }

    public String getLatest_highest() {
        return latest_highest;
    }

    public String getLatest_lowest() {
        return latest_lowest;
    }

    public String getLatest_price() {
        return latest_price;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public String getPre_closePrice() {
        return pre_closePrice;
    }

    public String getZdf() {
        return zdf;
    }

    public String getBuyFive() {
        return buyFive;
    }

    public String getBuyFour() {
        return buyFour;
    }

    public String getBuyOne() {
        return buyOne;
    }

    public String getBuyThree() {
        return buyThree;
    }

    public String getBuyTwo() {
        return buyTwo;
    }

    public String getDealMon() {
        return dealMon;
    }

    public String getDealNum() {
        return dealNum;
    }

    public String getStock_time() {
        return stock_time;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public void setPre_closePrice(String pre_closePrice) {
        this.pre_closePrice = pre_closePrice;
    }

    public void setLatest_lowest(String latest_lowest) {
        this.latest_lowest = latest_lowest;
    }

    public void setLatest_highest(String latest_highest) {
        this.latest_highest = latest_highest;
    }

    public void setLatest_price(String latest_price) {
        this.latest_price = latest_price;
    }

    public void setStock_date(String stock_date) {
        this.stock_date = stock_date;
    }

    public void setStock_time(String stock_time) {
        this.stock_time = stock_time;
    }

    public void setZdf(String zdf) {
        this.zdf = zdf;
    }

    public void setBuyFive(String buyFive) {
        this.buyFive = buyFive;
    }

    public void setBuyFour(String buyFour) {
        this.buyFour = buyFour;
    }

    public void setBuyOne(String buyOne) {
        this.buyOne = buyOne;
    }

    public void setBuyThree(String buyThree) {
        this.buyThree = buyThree;
    }

    public void setBuyTwo(String buyTwo) {
        this.buyTwo = buyTwo;
    }

    public void setDealMon(String dealMon) {
        this.dealMon = dealMon;
    }

    public void setDealNum(String dealNum) {
        this.dealNum = dealNum;
    }

    public void setZde(String zde) {
        this.zde = zde;
    }

    public void setHs(String hs) {
        this.hs = hs;
    }

    @NonNull
    @Override
    public String toString() {
        return "stockDetail{" +
                "stockName='" + stockName + '\'' +
                ", openPrice='" + openPrice + '\'' +
                ", pre_closePrice='" + pre_closePrice + '\'' +
                ", latest_price='" + latest_price + '\'' +
                ", latest_highest='" + latest_highest + '\'' +
                ", latest_lowest='" + latest_lowest + '\'' +
                ", dealNum='" + dealNum + '\'' +
                ", dealMon='" + dealMon + '\'' +
                ", buyOne='" + buyOne + '\'' +
                ", buyTwo='" + buyTwo + '\'' +
                ", buyThree='" + buyThree + '\'' +
                ", buyFour='" + buyFour + '\'' +
                ", buyFive='" + buyFive + '\'' +
                ", stock_date='" + stock_date + '\'' +
                ", stock_time='" + stock_time + '\'' +
                ", zdf='" + zdf + '\'' +
                ", zde='" + zde + '\'' +
                ", hs='" + hs + '\'' +
                '}';
    }
}
