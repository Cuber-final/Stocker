package com.example.stocker.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.stocker.R;
import com.example.stocker.newsOpe.profileNews;

import java.util.ArrayList;


public class newsListAdapter extends RecyclerView.Adapter<newsListAdapter.MyViewHolder> {

    private static final String TAG = newsListAdapter.class.getSimpleName();
    private ArrayList<profileNews> newsArrayList;

    private Context mContext;


    public newsListAdapter(Context context) {
        this.mContext = context;
    }

    public void initNewsData(ArrayList<profileNews> newsArrayList) {
        this.newsArrayList = newsArrayList;
    }

    @NonNull
    @Override
    public newsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        此方法很关键
        View itemBoxView = LayoutInflater.from(mContext).inflate(R.layout.newsitem, parent, false);
        return new MyViewHolder(itemBoxView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull newsListAdapter.MyViewHolder holder, int position) {
//        Log.i("绑定位置", "位置是" + position);
        profileNews itemBox = newsArrayList.get(position);
//        设置各控件绑定新闻对象对应内容
        holder.itemBox_title.setText(itemBox.getNewsTitle());
        holder.itemBox_pdate.setText(itemBox.getPublish_date());
        holder.itemBox_mediaName.setText(itemBox.getSourceMedia());
//        设置圆边图片,新版本的写法有所不同
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
//        使用Glide加载网络图片
        Glide.with(mContext)
                .load(itemBox.getImgUrl())
                .centerCrop()
                .apply(requestOptions)
                .into(holder.itemBox_img);

    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        View newsView;
        private TextView itemBox_title, itemBox_pdate, itemBox_mediaName;
        private ImageView itemBox_img;

        public MyViewHolder(@NonNull final View itemBoxView) {
            super(itemBoxView);
            newsView = itemBoxView;
            //文章标题
            itemBox_title = (TextView) itemBoxView.findViewById(R.id.news_title);
            //媒体来源
            itemBox_mediaName = (TextView) itemBoxView.findViewById(R.id.media_name);
            //发布日期
            itemBox_pdate = (TextView) itemBoxView.findViewById(R.id.p_date);

            //新闻图片
            itemBox_img = (ImageView) itemBoxView.findViewById(R.id.news_img);

            itemBoxView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext,"点击了"+itemBoxView., Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(v, newsArrayList.get(getLayoutPosition()));
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
        public void OnItemClick(View view, profileNews data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
