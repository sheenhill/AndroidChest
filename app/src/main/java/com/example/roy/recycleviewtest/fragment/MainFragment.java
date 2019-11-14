package com.example.roy.recycleviewtest.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.roy.recycleviewtest.R;
import com.example.roy.recycleviewtest.adapter.BingPicAdapter;
import com.example.roy.recycleviewtest.base.APIs;
import com.example.roy.recycleviewtest.entity.BingPicBean;
import com.example.roy.recycleviewtest.entity.ShuangseqiuBean;
import com.example.roy.recycleviewtest.util.LogUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainFragment extends Fragment implements BingPicAdapter.OnItemClickListener{
    //    @BindView(R.id.tv_motto_ssq)
//    TextView tvMottoSsq;
//    @BindView(R.id.tv_prophecy_ssq)
    TextView tvProphecySsq;
    @BindView(R.id.re_pic_display)
    RecyclerView rePicDisplay;
    private View view;

    private static final int GET_PIC = 1;
    private static final int GET_SSQ = 2;

    private static List<BingPicBean.ImagesBean> bingPicList;
//    final List<BingPicBean.ImagesBean> bingPicList=new ArrayList<>();
    private static BingPicAdapter bingPicAdapter;
    private  String tv_Shuangseqiu;

        @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_PIC:
                    bingPicAdapter.notifyDataSetChanged();
                    LogUtil.i("(3)bingPicList.HashCode:"+bingPicList.hashCode());
                    LogUtil.i("照片的集合取出来了！！！" + bingPicList);
                    break;
//                case GET_SSQ:
//                    tvMottoSsq.setText(tv_Shuangseqiu);
//                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        if(view==null) {TODO:防止重绘fragment
            view = inflater.inflate(R.layout.tabview_main, container, false);
            bingPicList=new ArrayList<>();
        LogUtil.i("bingPicList刚实例化，bingPicList.size()=" + bingPicList.size());
            ButterKnife.bind(this, view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            rePicDisplay.setLayoutManager(linearLayoutManager);
            bingPicAdapter = new BingPicAdapter(getActivity(), bingPicList);
//        beautyAdapter = new BeautyAdapter(getActivity(), girlPicUrlList);
            rePicDisplay.setAdapter(bingPicAdapter);
//        }

//        LogUtil.i("MainFragment:tvMottoSsq="+tvMottoSsq.toString());
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i("(1)bingPicList.HashCode:"+bingPicList.hashCode());
//        getShuangseqiu();
        if (bingPicList.size() == 0) {
            getBingPic();
        }

//        tvProphecySsq.setText(yuce());
        bingPicAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        LogUtil.i("在fragment中获得的recyclerView当前item的图片url：  "+"https://www.bing.com"+bingPicList.get(position).getUrl());
        Toast.makeText(getActivity(), bingPicList.get(position).getCopyright(), Toast.LENGTH_SHORT).show();
    }
//    private String yuce() {
//        Set<Integer> integers = new TreeSet<>();
//        int a = 0;
//        while (integers.size() != 6) {
//            a = (int) Math.floor(33 * Math.random() + 1);
//            integers.add(a);
//        }
//        int i = (int) Math.floor(Math.random() * 16 + 1);
//        StringBuffer sb = new StringBuffer();
//        for (Integer integer : integers)
//            sb.append(integer + "-");
//        sb.deleteCharAt(sb.length() - 1);
//        sb.append(" " + i);
//        return sb.toString();
//    }


//    private void getShuangseqiu() {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(APIs.LASTEST_SHUANGSEQIU)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            //请求失败的回调方法
//            @Override
//            public void onFailure(Call call, IOException e) {
//                LogUtil.i("双色球api获取失败" + e);//
//            }
//            //请求成功的回调方法
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String data = response.body().string();
//                LogUtil.i(data);
//                JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
//                JsonElement shuangseqiuObj = jsonObject.getAsJsonObject("data");
//                ShuangseqiuBean.DataBean dataBean = new Gson().fromJson(shuangseqiuObj,
//                        new TypeToken<ShuangseqiuBean.DataBean>() {
//                        }.getType());
//                String num = dataBean.getOpenCode();
//                String expect = dataBean.getExpect();
//                tv_Shuangseqiu = "第" + expect + "期双色球：" + num;
//                LogUtil.i("双色球：" + tv_Shuangseqiu);//双色球
//                response.body().close();//关闭body
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
//                        mHandler.sendEmptyMessage(GET_SSQ);
////                    }
////                }).start();
//            }
//        });
//    }


    private void getBingPic() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(APIs.PICs_BING+"idx=0&n=10")
                .build();
        client.newCall(request).enqueue(new Callback() {
            //请求失败的回调方法
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.i("找不到图链接啦，，，" + e);//
            }
            //请求成功的回调方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                LogUtil.i("okhttp返回数据:"+data);
                JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
                JsonArray jsonArray = jsonObject.getAsJsonArray("images");//拿到数组
                BingPicBean.ImagesBean resultsBean;

                String url;
                String copyrightStr;
                String[] strArr;
                int length0fLineOne;
                String tempStr;
                SpannableString spannableString;
                ForegroundColorSpan colorSpan;

                for (JsonElement pic : jsonArray) {
                    resultsBean= new Gson().fromJson(pic,
                            new TypeToken<BingPicBean.ImagesBean>() {
                            }.getType());
                    //GSON 提供了 TypeToken 这个类来帮助我们捕获（capture）像 List 这样的泛型信息。
                    // Java编译器会把捕获到的泛型信息编译到这个匿名内部类里，然后在运行时就可以被 getType() 方法用反射的 API 提取到。
                    //解释的很官方，实际上就是一句 通俗但不严谨 的话，它将泛型 T 转成 .class 。
                    // 比如上面的resultsBean 经过 getType() 后就是 ResultsBean.class 。
                    url = "https://www.bing.com" + resultsBean.getUrl();
                    copyrightStr = resultsBean.getCopyright().trim();
                    strArr = copyrightStr.split("\\(");
                    length0fLineOne = strArr[0].length();
                    tempStr = strArr[0] + "\n" + strArr[1].replace(")", "");

                    url = url.replaceFirst("1920x1080", "800x480");
                    spannableString = new SpannableString(tempStr);
                    colorSpan = new ForegroundColorSpan(Color.parseColor("#000000"));
                    spannableString.setSpan(colorSpan, 0, length0fLineOne, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    //样式text，参考链接 https://blog.csdn.net/u012551350/article/details/51722893

                    resultsBean.setUrl(url);
                    resultsBean.setNewcopyright(spannableString);

                    bingPicList.add(resultsBean);
                    LogUtil.i("(2)bingPicList.HashCode:"+bingPicList.hashCode());
                    LogUtil.i("bingPicList.url:"+resultsBean.getUrl());
                }
                LogUtil.i("图片信息集合:" + bingPicList.toString());//照片的集合
                response.body().close();//关闭body
                mHandler.sendEmptyMessage(GET_PIC);
            }
        });
    }


}
