package com.example.stocker.ui.fragLibrary;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.stocker.R;
import com.example.stocker.activities.MyApplication;
import com.example.stocker.activities.newsWeb;
import com.example.stocker.newsOpe.profileNews;
import com.example.stocker.adapters.newsListAdapter;
import com.example.stocker.toolsOpe.okhttpUtils;
import com.example.stocker.toolsOpe.webCallBack;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.stocker.newsOpe.getNews.parseNews;

public class newsListFragment extends BaseFragment {
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private static final String TAG = "newsListFragment";

    private Timer timer;
    private TimerTask timerTask;
    private boolean mHasLoadedOnce;

    private SwipeRefreshLayout mRefreshLayout;

    private RecyclerView mNewsRecycle;
    //    private ArrayList<profileNews> newsArrayList = new ArrayList<profileNews>();
    private newsListAdapter mLAdapter;
    //    private String newsTitle, imgUrl, publish_date, sourceMedia;
    private ArrayList<profileNews> newsArrayList;
    private String getNewsUrl;
    private Parcelable recyclerViewState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            // 需要inflate一个布局文件 填充Fragment
            mView = inflater.inflate(R.layout.fragment_newslist, container, false);
            getNewsUrl = "http://finance.eastmoney.com/a/czqyw.html";
            initBase();
            mLAdapter = new newsListAdapter(this.getActivity());
            initRecycleView();
//            初始化获取数据,nesArrayList
            getNewsData(getNewsUrl);

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


    private void initRecycleView() {
//                    RecycleView布局文件
        mNewsRecycle = mView.findViewById(R.id.list_of_news);
//        在fragment_newList布局中获取RecycleView的布局

//    不获取焦点，这样更新就不会跳至第一条
        mNewsRecycle.setFocusableInTouchMode(false);
        mNewsRecycle.setFocusable(false);
        //创建adapter,数据传递至适配器
        //        刷新布局
        mRefreshLayout = mView.findViewById(R.id.refresh_list);

        mRefreshLayout.setRefreshing(false);
        // 自定义 SwipeRefreshLayout 颜色
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_purple
        );


        // 设定下拉圆圈的背景色
        mRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);

        // 监听下拉刷新动作
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //恢复状态
                getNewsData(getNewsUrl);

//                每次刷新只需要更新adapter数据并通知
                mRefreshLayout.setRefreshing(false);
//                刷新延迟,调用方法更新列表
            }
        });
//        避免内容更改时导致布局加载失败，设置为true
//        mNewsRecycle.setHasFixedSize(true);

        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mNewsRecycle.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mNewsRecycle.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(this.getActivity()), DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
    }


    private void initBase() {
        Bundle bundle = getArguments();
        assert bundle != null;
        String args = bundle.getString("tabText");
    }

    private void initAdapter(ArrayList<profileNews> newsArrayList) {
        //        私有Parcelable recyclerViewState;
        recyclerViewState = Objects.requireNonNull(mNewsRecycle.getLayoutManager()).onSaveInstanceState();
//        初始化or每次刷新都只需要更新adapter的数据
        mLAdapter.initNewsData(newsArrayList);

        mNewsRecycle.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        mLAdapter.notifyDataSetChanged();//通知更新
        mNewsRecycle.setAdapter(mLAdapter);

//        此方法解决了复用重叠偏移的问题
//        mLAdapter.setHasStableIds(true);

        mLAdapter.setOnItemClickListener(new newsListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, profileNews data) {
                //此处点击对应新闻对象跳转至对应文章详情
                Log.i("get item", data.getNewsTitle());
                Intent webIntent = new Intent(getActivity(), newsWeb.class);
                webIntent.putExtra("webUrl", data.getNewsUrl());
                startActivity(webIntent);
            }
        });
    }

    private void getNewsData(String url) {

        newsArrayList = new ArrayList<>();
//        okhttpUtils.getInstance();test是否有区别
        okhttpUtils.doGet(url, new webCallBack() {
            @Override
            public void onSuccess(String response) {
                // 解析结果
                try {
                    newsArrayList = parseNews(response);
                    if (newsArrayList.size() == 0) {
                        Log.d("数据处理状态", "数据未获取");
                    } else {
                        Log.d("数据处理状态", "数据提取成功");
                    }
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initAdapter(newsArrayList);
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

    public static newsListFragment newInstance(String param1) {
        newsListFragment fragment = new newsListFragment();
        Bundle args = new Bundle();
        args.putString("tabText", param1);
        fragment.setArguments(args);
        //通过该方法可以有效解决不同fragment切换时参数的丢失
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
                        getNewsData(getNewsUrl);
                    }
                });

            }
        };
        timer = MyApplication.getTimer();
        Log.e(TAG, "onAttach: ");
        timer.schedule(timerTask, 10000, 10000);
    }

    @Override
    public void onStop() {
        super.onStop();
        timerTask.cancel();
        Log.e(TAG, "onDetach: ");
    }

}
