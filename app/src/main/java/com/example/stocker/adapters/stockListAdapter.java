package com.example.stocker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stocker.R;
import com.example.stocker.stockOpe.profileStock;

import java.util.ArrayList;

public class stockListAdapter extends RecyclerView.Adapter<stockListAdapter.stockViewHolder> {
    private static final String TAG = stockListAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<profileStock> stocksList;


    public stockListAdapter(Context context) {
        this.mContext = context;
    }

    public void initStocks(ArrayList<profileStock> stocksList) {
        this.stocksList = stocksList;
    }

    @NonNull
    @Override
    public stockListAdapter.stockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemStockView = LayoutInflater.from(mContext).inflate(R.layout.stockitem, parent, false);
        return new stockViewHolder(itemStockView);
    }

    @Override
    public void onBindViewHolder(@NonNull stockViewHolder holder, int position) {
//        队列中前三条不用做列表显示

        profileStock itemStock = stocksList.get(position);

        holder.itemStock_code.setText(itemStock.getStockCode());
        holder.itemStock_name.setText(itemStock.getStockName());
        holder.itemStock_zdf.setText(itemStock.getZdf());
        holder.itemStock_volume.setText(itemStock.getVolume());
        holder.itemStock_nPrice.setText(itemStock.getNewestPrice());
    }

    @Override
    public int getItemCount() {
        return stocksList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class stockViewHolder extends RecyclerView.ViewHolder {
        View stocks_proView;
        private TextView itemStock_name, itemStock_code, itemStock_zdf, itemStock_volume, itemStock_nPrice;

        public stockViewHolder(@NonNull final View itemStockView) {
            super(itemStockView);
            itemStock_name = (TextView) itemStockView.findViewById(R.id.stock_Name);
            itemStock_code = (TextView) itemStockView.findViewById(R.id.stock_Code);
            itemStock_nPrice = (TextView) itemStockView.findViewById(R.id.stock_nPrice);
            itemStock_zdf = (TextView) itemStockView.findViewById(R.id.stock_Zdf);
            itemStock_volume = (TextView) itemStockView.findViewById(R.id.stock_Volume);

            itemStockView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext,"点击了"+itemBoxView., Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(v, stocksList.get(getLayoutPosition()));
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
        public void OnItemClick(View view, profileStock data);
    }


    //需要外部访问，所以需要设置set方法，方便调用
    private stockListAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(stockListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
