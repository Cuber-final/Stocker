package com.example.stocker.ui.fragLibrary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.stocker.R;

import com.example.stocker.activities.MyApplication;
import com.example.stocker.activities.WebsiteAct;
import com.example.stocker.activities.stockCharts;
import com.example.stocker.stockOpe.profileStock;
import com.example.stocker.toolsOpe.okhttpUtils;
import com.example.stocker.adapters.stockListAdapter;
import com.example.stocker.toolsOpe.webCallBack;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.stocker.stockOpe.getStock.parseProStock;


public class mainFragment extends BaseFragment {
    private static final String TAG = "mainFragment";
    private boolean isPrepared;

    private boolean mHasLoadedOnce;
    private TextView timeView;

    private RecyclerView mStockRecycle;
    private ArrayList<profileStock> stockArrayList;

    private String getStockUrl;
    private stockListAdapter mStockAdapter;
    private TextView sh_nPrice, sh_Zdf, sh_Zde;
    private TextView sz_nPrice, sz_Zdf, sz_Zde;
    private TextView cz_nPrice, cz_Zdf, cz_Zde;

    private Timer timer;
    private TimerTask timerTask;
    private Parcelable recyclerViewState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {

            mView = inflater.inflate(R.layout.fragment_index, container, false);
            getStockUrl = "http://q.10jqka.com.cn/";
            initBase();
            mStockAdapter = new stockListAdapter(this.getActivity());
            initRecycleView();
//            初始化获取数据,nesArrayList
            getStockData(getStockUrl);
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

    //监听事件的注册
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
//        初始化fragment中的控件
        TextView sh_btn = (TextView) (Objects.requireNonNull(getActivity())).findViewById(R.id.sh_btn);
        TextView sz_btn = (TextView) (Objects.requireNonNull(getActivity())).findViewById(R.id.sz_btn);
        TextView cz_btn = (TextView) (Objects.requireNonNull(getActivity())).findViewById(R.id.cz_btn);
        TextView refStock_btn = (TextView) (Objects.requireNonNull(getActivity())).findViewById(R.id.refreshStock);
        sh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String webUrl = "https://m.10jqka.com.cn/stockpage/hs_1A0001";
                Toast.makeText(getActivity(), "JUMP TO shIndex", Toast.LENGTH_LONG).show();
                Intent webIntent = new Intent(getActivity(), WebsiteAct.class);
                webIntent.putExtra("webUrl", webUrl);
                startActivity(webIntent);
            }
        });
        sz_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String webUrl = "https://m.10jqka.com.cn/stockpage/hs_1A0001";
                Toast.makeText(getActivity(), "JUMP TO szIndex", Toast.LENGTH_LONG).show();
                Intent webIntent = new Intent(getActivity(), WebsiteAct.class);
                webIntent.putExtra("webUrl", webUrl);
                startActivity(webIntent);
            }
        });
        cz_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String webUrl = "https://m.10jqka.com.cn/stockpage/hs_1A0001";
                Toast.makeText(getActivity(), "JUMP TO czIndex", Toast.LENGTH_LONG).show();
                Intent webIntent = new Intent(getActivity(), WebsiteAct.class);
                webIntent.putExtra("webUrl", webUrl);
                startActivity(webIntent);
            }
        });
        refStock_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getStockUrl = "http://q.10jqka.com.cn/";
                Toast.makeText(getActivity(), "refresh success", Toast.LENGTH_LONG).show();
                getStockData(getStockUrl);
            }
        });
    }

    /**
     * 初始化控件
     */

    private void initBase() {
        Bundle bundle = getArguments();
        assert bundle != null;
        String args = bundle.getString("tabText");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        timeView = mView.findViewById(R.id.system_time);
        timeView.setText(dateFormat.format(date));
    }

    private void initRecycleView() {
        mStockRecycle = mView.findViewById(R.id.list_of_proStock);

        mStockRecycle.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));

        mStockRecycle.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(this.getActivity()), DividerItemDecoration.VERTICAL));
//    不获取焦点，这样更新就不会跳至第一条
        mStockRecycle.setFocusableInTouchMode(false);
        mStockRecycle.setFocusable(false);
    }

    private void initAdapter(ArrayList<profileStock> stockArrayList) {
        //        私有Parcelable recyclerViewState;
        recyclerViewState = Objects.requireNonNull(mStockRecycle.getLayoutManager()).onSaveInstanceState();
//        初始化or每次刷新都只需要更新adapter的数据
        mStockAdapter.initStocks(stockArrayList);
        mStockAdapter.notifyDataSetChanged();//通知更新
        mStockRecycle.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        mStockRecycle.setAdapter(mStockAdapter);
        mStockAdapter.setOnItemClickListener(new stockListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, profileStock data) {
                //此处点击对应新闻对象跳转至对应文章详情
                Log.i("get item", data.getStockCode());
                Log.i("get item", data.getZdf());
//                具体股票跳转
                Intent stockIntent = new Intent(getActivity(), stockCharts.class);
                stockIntent.putExtra("cNum", data.getStockCode());
                stockIntent.putExtra("cName", data.getStockName());
                startActivity(stockIntent);
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
                            refreshIndexView();
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


    private void refreshIndexView() {
//        此过程太过重复，考虑更好办法
        sh_nPrice = (TextView) mView.findViewById(R.id.sh_nPrice);
        sh_Zdf = (TextView) mView.findViewById(R.id.sh_zdf);
        sh_Zde = (TextView) mView.findViewById(R.id.sh_zde);

        sz_nPrice = (TextView) mView.findViewById(R.id.sz_nPrice);
        sz_Zdf = (TextView) mView.findViewById(R.id.sz_zdf);
        sz_Zde = (TextView) mView.findViewById(R.id.sz_zde);

        cz_nPrice = (TextView) mView.findViewById(R.id.cz_nPrice);
        cz_Zdf = (TextView) mView.findViewById(R.id.cz_zdf);
        cz_Zde = (TextView) mView.findViewById(R.id.cz_zde);

        sh_nPrice.setText(stockArrayList.get(0).getNewestPrice());
        sh_Zdf.setText(stockArrayList.get(0).getZdf());
        sh_Zde.setText(stockArrayList.get(0).getZde());

        sz_nPrice.setText(stockArrayList.get(1).getNewestPrice());
        sz_Zdf.setText(stockArrayList.get(1).getZdf());
        sz_Zde.setText(stockArrayList.get(1).getZde());

        cz_nPrice.setText(stockArrayList.get(2).getNewestPrice());
        cz_Zdf.setText(stockArrayList.get(2).getZdf());
        cz_Zde.setText(stockArrayList.get(2).getZde());
    }

    @Override
    public void resetArgument(Bundle bundle) {

    }

    public static mainFragment newInstance(String param1) {
        mainFragment fragment = new mainFragment();
        Bundle args = new Bundle();
        args.putString("tabText", param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.e(TAG, "run: " + new Date().toString());
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        设置定时器自动更新数据
                        getStockData(getStockUrl);
                    }
                });

            }
        };
        timer = MyApplication.getTimer();
        Log.e(TAG, "onAttach: ");
        timer.schedule(timerTask, 5000, 5000);
    }

    @Override
    public void onStop() {
        super.onStop();
        timerTask.cancel();
        Log.e(TAG, "onDetach: ");
    }


}