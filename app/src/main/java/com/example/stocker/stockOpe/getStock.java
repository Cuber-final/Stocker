package com.example.stocker.stockOpe;

import android.content.Context;
import android.content.res.AssetManager;
import android.icu.util.LocaleData;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.example.stocker.toolsOpe.jsonUtil.getJsonString;


public class getStock {

    //    爬取json数据方式
    public static stockDetail jsonStock(String stockHtml) throws IOException {
        stockDetail stockDetail = new stockDetail();
        Document document = Jsoup.parse(stockHtml);
        String jsonText = document.body().text();
        Log.d("t", jsonText);
        JSONObject stockJson = JSON.parseObject(jsonText);
        //将json字符串转换为json对象

        // 股票对象
        String n1, n2, n3, n4, n5, n6, n7, n8, n9;
//        最高价
        n1 = String.valueOf(stockJson.getDouble("h"));
        stockDetail.setLatest_highest(n1);
//        换手率
        n2 = String.valueOf(stockJson.getDouble("hs"));
        stockDetail.setHs(n2);
//        最低价
        n3 = String.valueOf(stockJson.getDouble("l"));
        stockDetail.setLatest_lowest(n3);
//        涨跌幅
        n4 = String.valueOf(stockJson.getDouble("pc"));
        stockDetail.setZdf(n4);
//        涨跌额
        n5 = String.valueOf(stockJson.getDouble("ud"));
        stockDetail.setZde(n5);
//        当前价
        n6 = String.valueOf(stockJson.getDouble("p"));
        stockDetail.setLatest_price(n6);
//        成交额，元-亿元
        double getTotal = stockJson.getDouble("cje") / 100000000.0;
        n7 = String.valueOf(Math.floor(getTotal));
        stockDetail.setDealMon(n7);
//        成交量，股-百股
        double getNum = stockJson.getDouble("v") / 100.0;
        n8 = String.valueOf(Math.floor(getNum));
        stockDetail.setDealNum(n8);
//        收盘价
        n9 = String.valueOf(stockJson.getDouble("yc"));
        stockDetail.setPre_closePrice(n9);

        Log.d("股票单条数据", stockDetail.toString());
        return stockDetail;
    }


    //        解析网页方式
//首页获取
    public static ArrayList<profileStock> parseProStock(String stockHtml) throws IOException {
        ArrayList<profileStock> stockDetails = new ArrayList<>();
        Document document = Jsoup.parse(stockHtml);
//        Log.i("指数信息", document.text());
        //股票对象
        String stockCode, stockName, newestPrice, Zdf, Zde, volume, codeUrl;

//        获取股票列表部分
        Elements elements = document.select("div[class=body m-pager-box]")
                .select("div[id=maincont]")
                .select("table[class=m-table m-pager-table]")
                .select("tbody")
                .select("tr");

//        获取多条股票相关信息<tr>包裹
        for (Element tdEle : elements) {
            //获取td序列<td>*n
            Elements children = tdEle.select("td");
            //股票代码
            stockCode = children.get(1).select("a").text();
            //股票地址, tdEle.select("td").get(1).select("a").attr("href");
            codeUrl = children.get(1).select("a").attr("href");
            //股票名字
            stockName = children.get(2).select("a").text();
            //股票现价
            newestPrice = children.get(3).text();
            //股票涨跌幅
            Zdf = children.get(4).text();
//            股票涨跌额
            Zde = children.get(5).text();
            //股票成交额
            volume = children.get(10).text();
            profileStock proStock = new profileStock(stockCode, stockName, newestPrice, Zdf, Zde, volume, codeUrl);
            stockDetails.add(proStock);
        }
        Log.d("获取总条数", String.valueOf(stockDetails.size()));
        return stockDetails;
    }


    public static ArrayList<stockMap> getAllStocks(Context mContext, String fileName) {
        //filename assets目录下的json文件名
        ArrayList<stockMap> stockDetails = new ArrayList<>();
        JSONArray jsonDate = null;
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(mContext.getAssets().open(fileName), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            jsonDate = JSON.parseArray(stringBuilder.toString());//得到JSONobject对象
            bufferedReader.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert jsonDate != null;
        String jsonStr = jsonDate.toJSONString();

        stockDetails = JSON.parseObject(jsonStr, new TypeReference<ArrayList<stockMap>>() {
        });

//        如要实现更好的搜索功能，需要使用数据库，将json解析成的对象集合保存到sqlite中使用
        Log.d("456-000 the last", stockDetails.get(456).getDm());

        Log.d("457-001 the first", stockDetails.get(457).getDm());
        Log.d("462-001 the last", stockDetails.get(462).getDm());
//002+ 代码有接近1000条，因此可以分0-9十个部分每个100
        Log.d("463-002 the first", stockDetails.get(463).getDm());

        Log.d("1461-00 the last", stockDetails.get(1461).getDm());

        Log.d("1462-300 the first", stockDetails.get(1462).getDm());
        Log.d("2351-300 the last", stockDetails.get(2351).getDm());
        Log.d("2352-600 the first", stockDetails.get(2352).getDm());

//       从本地json中获取stock队列
        return stockDetails;
    }


/*    public static ArrayList<stockMap> filterText(String newText) {
        ArrayList<stockMap> allStocks = new ArrayList<>();
        allStocks = getAllStocks();
        ArrayList<stockMap> filterStocks = new ArrayList<>();
        for (int i = 0; i < allStocks.size(); i++) {
            String cmpCode = allStocks.get(i).getDm();
            if (cmpCode.contains(newText)) {
                filterStocks.add(allStocks.get(i));
            }
        }
//        筛选后返回集合并由适配器更新
        return filterStocks;
    }*/

    public static void getRate(String stockHtml) throws IOException {
        ArrayList<profileStock> stockDetails = new ArrayList<>();
//        东方财富网的网页编码格式不是UTF-8，爬取会乱码，需要转换
        Document document = Jsoup.parse(new URL(stockHtml).openStream(), "GBK", stockHtml);
        Log.e("document", document.text());
        Element tableList = document.select("div[id=page]")
                .select("div[class=main]")
                .select("div[class=framecontent]").select("div[class=sitebody]")
                .select("div[class=maincont]")
                .select("div[class=contentBox]")
                .select("div").get(4);
        Log.e("listtext", tableList.text());
        Elements table = tableList.select("table[class=tab1]")
                .select("tobdy").select("tr");
        for (Element single : table) {
            String test = single.select("td").get(1).text();
            Log.d("testCode", test);
        }
    }


}
