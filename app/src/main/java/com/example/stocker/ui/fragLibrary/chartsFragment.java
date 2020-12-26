package com.example.stocker.ui.fragLibrary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stocker.R;
import com.example.stocker.adapters.stockListAdapter;
import com.example.stocker.stockOpe.profileStock;
import com.example.stocker.toolsOpe.EchartOptionUtil;
import com.example.stocker.toolsOpe.g_webView;
import com.example.stocker.toolsOpe.okhttpUtils;
import com.example.stocker.toolsOpe.webCallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.stocker.stockOpe.getStock.getRate;
import static com.example.stocker.stockOpe.getStock.parseProStock;

public class chartsFragment extends BaseFragment {
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;

    private g_webView barChart;
    private RecyclerView mStockRecycle;
    private ArrayList<profileStock> stockArrayList;

    private String getStockUrl;
    private stockListAdapter mStockAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            // 需要inflate一个布局文件 填充Fragment，是否可以尝试嵌入子fragment
            mView = inflater.inflate(R.layout.fragment_echarts, container, false);
//            getStockUrl = "http://data.eastmoney.com/zjlx/detail.html";
            getStockUrl = "http://q.10jqka.com.cn/";
            mStockAdapter = new stockListAdapter(this.getActivity());
            initBase();
            initRecycleView();
            //            初始化获取数据,nesArrayList
            getStockData(getStockUrl);
//            采用以下方法解决子元素滑动与父元素滑动出现冲突的问题,好像并没有用
            mView.setFocusable(true);
            mView.setFocusableInTouchMode(true);
            mView.requestFocus();
            isPrepared = true;
//        实现懒加载
            lazyLoad();
        }
        //缓存的mView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mView已经有parent的错误。
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        return mView;
    }

    /**
     * 初始化控件
     */
    private void initBase() {
        Bundle bundle = getArguments();
        assert bundle != null;
        String args = bundle.getString("tabText");
    }

    private void initRecycleView() {
        mStockRecycle = mView.findViewById(R.id.list_of_rate);
        mStockRecycle.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));

        mStockRecycle.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(this.getActivity()), DividerItemDecoration.VERTICAL));
    }

    private void initAdapter(ArrayList<profileStock> stockArrayList) {
//        初始化or每次刷新都只需要更新adapter的数据
        mStockAdapter.initStocks(stockArrayList);
        mStockAdapter.notifyDataSetChanged();//通知更新
        mStockRecycle.setAdapter(mStockAdapter);
        mStockAdapter.setOnItemClickListener(new stockListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, profileStock data) {
                Log.i("get item", data.getStockName());
            }
        });
    }


    private void getStockData(String url) {

        stockArrayList = new ArrayList<>();
//        okhttpUtils.getInstance();test是否有区别
        okhttpUtils.doGet(url, new webCallBack() {
            @Override
            public void onSuccess(String response) {
                // 解析结果,消息码1指定爬取股票列表,2表示爬取主要指数
                try {
                    stockArrayList = parseProStock(response);
                    if (stockArrayList.size() == 0) {
                        Log.d("股票处理状态", "数据未获取");
                    } else {
                        Log.d("股票处理状态", "数据提取成功");
                    }
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initAdapter(stockArrayList);
                            initCharts();
//                    调用方法将数据适配到adapter
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailed(Throwable ex) {
                Log.i("请求状态", "失败");
            }
        });
    }

    private void initCharts() {
        String fileUrl = "file:///android_asset/echarts.html";
        barChart = mView.findViewById(R.id.barChart);
        barChart.loadUrl(fileUrl);//加载指定页面
        barChart.init();
        barChart.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                refreshBarChart(barChart, stockArrayList);
            }
        });

    }


    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        //填充各控件的数据
        mHasLoadedOnce = true;
    }

    @Override
    public void resetArgument(Bundle bundle) {

    }

    public static chartsFragment newInstance(String param1) {
        chartsFragment fragment = new chartsFragment();
        Bundle args = new Bundle();
        args.putString("tabText", param1);
        fragment.setArguments(args);
        return fragment;
    }

    //此处的数据最好是由子线程获取并处理好回传
    private void refreshBarChart(g_webView chartView, ArrayList<profileStock> stockArrayList) {
        int size = 5;
        Object[] x = new Object[size];
        Object[] y = new Object[size];
        int i = size - 1;
        while (i >= 0) {
            x[i] = stockArrayList.get(i).getStockName();
            y[i] = stockArrayList.get(i).getZdf();
            i--;
        }
        chartView.refreshEchartsWithOption(EchartOptionUtil.getBarChartOptions(x, y));
    }


}
