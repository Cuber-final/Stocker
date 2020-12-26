package com.example.stocker.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stocker.R;

import com.example.stocker.stockOpe.stockMap;

import java.util.ArrayList;

public class queAdapter extends RecyclerView.Adapter<queAdapter.MyViewHolder> {

    //    private MainActivity.MyFilter mFilter;
    private static final String TAG = queAdapter.class.getSimpleName();
    private ArrayList<stockMap> queStockList;
    private Context mContext;

    public ArrayList<stockMap> getAll() {
        return this.queStockList;
    }

    public queAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemBoxView = LayoutInflater.from(mContext).inflate(R.layout.queitem, parent, false);
        return new queAdapter.MyViewHolder(itemBoxView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        stockMap itemBox = queStockList.get(position);
//        设置各控件绑定新闻对象对应内容
        holder.itemBox_code.setText(itemBox.getDm());
        holder.itemBox_name.setText(itemBox.getMc());

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return queStockList.size();
    }


    public void initQueData(ArrayList<stockMap> queStockList) {
        this.queStockList = queStockList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View mView;
        private TextView itemBox_code, itemBox_name;

        public MyViewHolder(@NonNull final View itemBoxView) {
            super(itemBoxView);
            mView = itemBoxView;
            //股票代码
            itemBox_code = (TextView) itemBoxView.findViewById(R.id.queItem_code);
            //股票名称
            itemBox_name = (TextView) itemBoxView.findViewById(R.id.queItem_name);
            itemBoxView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext,"点击了"+itemBoxView., Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(v, queStockList.get(getLayoutPosition()));
                    }
                }
            });

        }

    }

    /**
     * 设置item的监听事件的接口
     */
    public interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据,即单个对象
         */
        public void OnItemClick(View view, stockMap data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private queAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(queAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
