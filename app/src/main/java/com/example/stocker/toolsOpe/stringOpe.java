package com.example.stocker.toolsOpe;

import android.util.Log;

import com.example.stocker.stockOpe.stockDetail;
import com.example.stocker.stockOpe.stockMap;

import java.util.ArrayList;


public class stringOpe {

    public static stockDetail filteredString(String sourceString) {
        int detailStart = sourceString.indexOf('"');
        int detailEnd = sourceString.lastIndexOf(",");
        // 获取主要信息字符串由双引号包裹，方便截取
        String cutString = sourceString.substring(detailStart + 1, detailEnd);
        String[] blockStrings = cutString.split(",");

//        股票代码名称
        String stockName = blockStrings[0];
//        开盘价,此部分交由json处理
/*        String opPrice = blockStrings[1];
        String prePrice = blockStrings[2];
        String latePrice = blockStrings[3];
        String highest = blockStrings[4];
        String lowest = blockStrings[5];

        */
        String buyOne = blockStrings[11];
        String buyTwo = blockStrings[13];
        String buyThree = blockStrings[15];
        String buyFour = blockStrings[17];
        String buyFive = blockStrings[19];

//成交数
        double num = Integer.parseInt(blockStrings[8]) / 100.0;
        String dealNum = String.valueOf(num);
//      成交金额，  需要除以10000并显示亿元为单位
        double money = Double.parseDouble(blockStrings[9]) / 100000000;
        String dealMon = String.valueOf(money);

//当前日期
        String date = blockStrings[30];
//        当前时间
        String time = blockStrings[31];
        //单条股票基本数据，将会批量处理
        stockDetail stockDetail = new stockDetail();

/*        stockDetail.setStockName(stockName);
//        开盘价
        stockDetail.setOpenPrice(opPrice);
//        收盘价
        stockDetail.setPre_closePrice(prePrice);
        stockDetail.setLatest_price(latePrice);
        stockDetail.setLatest_highest(highest);
        stockDetail.setLatest_lowest(lowest);*/

        stockDetail.setBuyOne(buyOne);
        stockDetail.setBuyTwo(buyTwo);
        stockDetail.setBuyThree(buyThree);
        stockDetail.setBuyFour(buyFour);
        stockDetail.setBuyFive(buyFive);

        stockDetail.setStock_date(date);
        stockDetail.setStock_time(time);
        //test输出一条股票完整信息
        Log.i("股票数据是", stockDetail.toString());
        return stockDetail;
    }

    public static ArrayList<stockMap> filterSearch(String content, ArrayList<stockMap> preStockMaps) {
//        int countLimit = 30;
        int index = 0;
        ArrayList<stockMap> filtererList = new ArrayList<>();
        stockMap cmpItem = new stockMap();
//        输入300进行搜索
        if (content.length() == 3) {
//            查找第一个字符判断为3or6
            switch (content.charAt(0)) {
                case '3':
                    for (index = 1462; index < 1492; index++) {
//                    遍历数据库的股票代码数据进行比较
                        cmpItem = preStockMaps.get(index);
                        if (cmpItem.getDm().contains(content)) {
                            filtererList.add(cmpItem);
                        }
                    }
                    ;
                    break;
                case '6':
                    for (index = 2352; index < 2382; index++) {
                        cmpItem = preStockMaps.get(index);
                        if (cmpItem.getDm().contains(content)) {
                            filtererList.add(cmpItem);
                        }
                    }
                    ;
                    break;
                default:
                    break;
            }
        }

        if (content.equals("600036")) {
            index = 2379;
            cmpItem = preStockMaps.get(index);
            filtererList.add(cmpItem);
        }
        return filtererList;
    }
}
