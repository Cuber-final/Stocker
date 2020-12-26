package com.example.stocker.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.stocker.R;
import com.example.stocker.stockOpe.stockDetail;
import com.example.stocker.toolsOpe.EchartOptionUtil;
import com.example.stocker.toolsOpe.g_webView;
import com.example.stocker.toolsOpe.okhttpUtils;
import com.example.stocker.toolsOpe.webCallBack;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.stocker.stockOpe.getStock.jsonStock;
import static com.example.stocker.stockOpe.getStock.parseProStock;
import static com.example.stocker.toolsOpe.stringOpe.filteredString;

//股票代码作为参数传递至此，对单个股票的实时数据分析
public class stockCharts extends AppCompatActivity {
    private static final String TAG = "stockCharts";
    private String stockApi, stockRkPic;
    private String license;
    private String getParam;
    private g_webView kChart;
    private stockDetail stockStream;
    private TextView codeName, codeNum;
    private Timer timer;
    private TimerTask timerTask;
    private TextView n1, n2, n3, n4, n5, n6, n7, n8, n9;
    private ImageView kPic;
    private String text;

    //sh代码为6开头，sz代码为3开头，这样网址爬取可以分支搜索
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stockview);

        codeNum = findViewById(R.id.Code);
        codeName = findViewById(R.id.stockView_CodeName);
        Intent getCode = getIntent();
        //设置股票代码
        getParam = getCode.getStringExtra("cNum");
        codeNum.setText(getParam);
//        stockApi = "http://hq.sinajs.cn/list=";
        license = "C6D7A39C-A0B4-B09F-0428-39439727973B";
        stockApi = "http://ig507.com/data/time/real//" + getParam + "?licence=" + license;

        stockRkPic = "http://image.sinajs.cn/newchart/min/n/";
        //需要根据代码开头选择标签,0-3开头为sz，6开头为sh
        if (getParam.indexOf("6") == 0) {
            stockRkPic += "sh";
        } else {
            stockRkPic += "sz";
        }
        stockRkPic += getParam + ".gif";
//        获取相应信息

        //设置股票名称
        getParam = getCode.getStringExtra("cName");
        codeName.setText(getParam);

        initData(stockApi);
//        initData(stockApi);
        initCharts();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                initData(stockApi);
            }
        }, 500, 1000);
    }


    private void initData(String apiUrl) {
//        h获取当前股票基本信息
        okhttpUtils.doGet(apiUrl, new webCallBack() {
            @Override
            public void onSuccess(String response) {
                // 解析结果,消息码1指定爬取股票列表,2表示爬取主要指数
                try {
                    stockStream = jsonStock(response);
                    stockCharts.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            更新数据
                            refreshView(stockStream);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Throwable ex) {
                Log.i("当前股票请求", "失败");
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void refreshView(stockDetail stockStream) {
        n1 = findViewById(R.id.n1);
        n2 = findViewById(R.id.n2);
        n3 = findViewById(R.id.n3);
        n4 = findViewById(R.id.n4);
        n5 = findViewById(R.id.n5);
        n6 = findViewById(R.id.n6);
        n7 = findViewById(R.id.n7);
        n8 = findViewById(R.id.n8);
        n9 = findViewById(R.id.n9);
        kPic = findViewById(R.id.rKPic);
//        现价
        n1.setText(stockStream.getLatest_price());
//        涨跌额
        n2.setText(stockStream.getZde());
//        涨跌幅
        n3.setText(stockStream.getZdf()+"%");
//        最高价
        text = stockStream.getLatest_highest() == null ? "- -" : stockStream.getLatest_highest();
        n4.setText("高" + text);
//        最低价
        text = stockStream.getLatest_lowest() == null ? "- -" : stockStream.getLatest_lowest();
        n5.setText("低" + text);
//        开盘价
        text = stockStream.getOpenPrice() == null ? "- -" : stockStream.getOpenPrice();
        n6.setText("开" + text);
//        收盘价
        text = stockStream.getPre_closePrice() == null ? "- -" : stockStream.getPre_closePrice();
        n7.setText("收" + text);
//        成交额
        text = stockStream.getDealMon() == null ? "- -" : stockStream.getDealMon();
        n8.setText("金" + text + "亿");
//        换手率
        text = stockStream.getHs() == null ? "- -" : stockStream.getHs();
        n9.setText("换" + stockStream.getHs() + "%");

        Glide.with(this)
                .load(stockRkPic)
                .centerCrop()
                .into(kPic);

    }

    public void initCharts() {
        String fileUrl = "file:///android_asset/echarts.html";
        kChart = findViewById(R.id.kChart);
        kChart.loadUrl(fileUrl);
        kChart.init();
        kChart.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                refreshKChart(kChart);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                initData(stockApi);
            }
        };
        timer = MyApplication.getTimer();
        Log.e(TAG, "onAttach: ");
        timer.schedule(timerTask, 1000, 1000);
    }

    @Override
    public void onStop() {
        super.onStop();
        timerTask.cancel();
        Log.e(TAG, "onDetach: ");
    }

    private void refreshKChart(g_webView chartView) {
//        此处传入数据
        chartView.refreshEchartsWithOption(EchartOptionUtil.getKChartOption());
    }

}
