package com.example.stocker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stocker.R;
import com.example.stocker.adapters.newsListAdapter;
import com.example.stocker.adapters.queAdapter;
import com.example.stocker.newsOpe.profileNews;
import com.example.stocker.stockOpe.stockMap;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.stocker.stockOpe.getStock.getAllStocks;
import static com.example.stocker.toolsOpe.jsonUtil.getJsonString;
import static com.example.stocker.toolsOpe.stringOpe.filterSearch;

public class searchMain extends AppCompatActivity {
    private static final String TAG = "searchMain";
    private ArrayList<stockMap> stockMaps = new ArrayList<stockMap>();
    private stockMap cmpItem;
    private ArrayList<stockMap> preStockMaps;
    private EditText queBar;
    private RecyclerView mQueView;
    private queAdapter mQueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seastock);
        queBar = (EditText) findViewById(R.id.edit_search);
        preStockMaps = getAllStocks(this, "gplist.txt");
        mQueAdapter = new queAdapter(searchMain.this);
        initRecycleView();
        initData(preStockMaps);
        initQueListener();

    }

    public void initData(ArrayList<stockMap> preStockMaps) {
//        本地json所有数据作为数据库用来检索
        Log.d("get nums", String.valueOf(preStockMaps.size()));
        Log.d("GET ONE", preStockMaps.get(2).getDm());
//     UI初始化10条展示
        for (int i = 0; i < 10; i++) {
            stockMaps.add(preStockMaps.get(i));
        }
//        Log.d("get filter nums", String.valueOf(stockMaps.size()));
        initAdapter(stockMaps);
    }

    private void initRecycleView() {
//                    RecycleView布局文件
        mQueView = findViewById(R.id.list_of_queStock);
        mQueView.setLayoutManager(new LinearLayoutManager(searchMain.this, LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mQueView.addItemDecoration(new DividerItemDecoration(searchMain.this, DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
    }

    private void initAdapter(ArrayList<stockMap> stockMaps) {
        mQueAdapter.initQueData(stockMaps);
        mQueAdapter.notifyDataSetChanged();//通知更新
        mQueView.setAdapter(mQueAdapter);
        mQueAdapter.setOnItemClickListener(new queAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, stockMap data) {
                Log.d("queItem-is", data.getDm());
                Intent stockIntent = new Intent(searchMain.this, stockCharts.class);
                stockIntent.putExtra("cNum", data.getDm());
                stockIntent.putExtra("cName", data.getMc());
                startActivity(stockIntent);
            }
        });
    }

    private void initQueListener() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("count is?:", String.valueOf(count));
            }

            @Override
            public void afterTextChanged(Editable text) {
//                清除展示列表的数据集
                cmpItem = new stockMap();
                String content = queBar.getText().toString();
                stockMaps = filterSearch(content, preStockMaps);
//                更新数据列表
                initAdapter(stockMaps);
            }
        };

        queBar.addTextChangedListener(watcher);
    }

}