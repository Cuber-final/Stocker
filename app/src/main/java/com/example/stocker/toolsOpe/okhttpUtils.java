package com.example.stocker.toolsOpe;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class okhttpUtils {
    //设置基本操作，请求html网页

    // OkHttp网络请求方法，返回结果字符串
//    接口化请求数据，可以返回字符串
    public static void doGet(String url, final webCallBack callBack) {
        OkHttpClient client = new OkHttpClient();
        final Handler mUiHandler = new Handler();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                mUiHandler.post(new Runnable() {
                    //以匿名内部类的形式发送Runnable对象，在Runnable对象重写的run()方法中直接对UI进行更新
                    @Override
                    public void run() {
                        callBack.onFailed(e);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String getRes = null;
                try {
                    assert response.body() != null;
           /*         byte[] text = response.body().bytes();
                    getRes = new String(text,"GBK");*/
                    getRes = response.body().string();
//                    获取请求数据
                } catch (final IOException e) {
                    mUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onFailed(e);
                        }
                    });
                    return;
                }
                final String RespStr = getRes;
                mUiHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        数据回调
                        callBack.onSuccess(RespStr);
                    }
                });
            }
        });
    }


//    获取单条股票数据,后续url需要一定修改，包含股票代码，获取对应信息
//    在子线程回调时就进行数据处理解析

    public static String requestStockData(String url) {
        String html = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            //return
            assert response.body() != null;
            html = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }


    public static void getTrans(String url, final webCallBack callBack) {

        OkHttpClient client = new OkHttpClient();
        final Handler mUiHandler = new Handler();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                mUiHandler.post(new Runnable() {
                    //以匿名内部类的形式发送Runnable对象，在Runnable对象重写的run()方法中直接对UI进行更新
                    @Override
                    public void run() {
                        callBack.onFailed(e);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String getRes = null;
                try {
                    assert response.body() != null;
                    getRes = response.body().string();
//                    获取请求数据
                } catch (final IOException e) {
                    mUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onFailed(e);
                        }
                    });
                    return;
                }
                final String RespStr = getRes;
                mUiHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        数据回调
                        callBack.onSuccess(RespStr);
                    }
                });
            }
        });
    }


    //    生成单例
    private static okhttpUtils instance;

    private okhttpUtils() {
    }

    public static okhttpUtils getInstance() {
        if (instance == null) {
            instance = new okhttpUtils();
        }
        return instance;
    }


}
