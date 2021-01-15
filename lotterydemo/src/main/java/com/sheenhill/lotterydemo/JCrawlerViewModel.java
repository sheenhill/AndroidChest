package com.sheenhill.lotterydemo;

import android.content.Context;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sheenhill.common.util.LogUtil;
import com.sheenhill.common.util.ToastUtils;
import com.sheenhill.lotterydemo.data_repository.LuckNumberGeneratorKt;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.*;


public class JCrawlerViewModel extends ViewModel {
    public static final boolean SSQ = true;
    public static final boolean DLT = false;

    // 双色球集合
    private MutableLiveData<List<List<String>>> infoListSsq = new MutableLiveData<>();
    // 大乐透集合
    private MutableLiveData<List<List<String>>> infoListDlt = new MutableLiveData<>();
    // 页面类型
    private MutableLiveData<Boolean> pageInfoType = new MutableLiveData<>(SSQ);
    // 幸运数字集合
    private MutableLiveData<List<List<String>>> listLuckyNum = new MutableLiveData<>();

    public MutableLiveData<List<List<String>>> getInfoListSsq() {
        return infoListSsq;
    }

    public MutableLiveData<List<List<String>>> getInfoListDlt() {
        return infoListDlt;
    }

    public MutableLiveData<Boolean> getPageInfoType() {
        return pageInfoType;
    }

    public MutableLiveData<List<List<String>>> getListLuckyNum() {
        if (listLuckyNum.getValue() == null) {
            listLuckyNum.setValue(new ArrayList<List<String>>() {{
                add(new ArrayList<String>() {{
                    add("1");
                    add("1");
                    add("1");
                    add("1");
                    add("1");
                    add("1");
                    add("1");
                }});
                add(new ArrayList<String>() {{
                    add("1");
                    add("1");
                    add("1");
                    add("1");
                    add("1");
                    add("1");
                    add("1");
                }});
                add(new ArrayList<String>() {{
                    add("1");
                    add("1");
                    add("1");
                    add("1");
                    add("1");
                    add("1");
                    add("1");
                }});
                add(new ArrayList<String>() {{
                    add("1");
                    add("1");
                    add("1");
                    add("1");
                    add("1");
                    add("1");
                    add("1");
                }});
            }});
        }
        return listLuckyNum;
    }

    /* 爬取双色球彩票数据 */
    public void crawlerSsq(Context context, String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            //请求失败的回调方法
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.i("找不到链接啦，，，" + e);//
            }

            //请求成功的回调方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
//                LogUtil.i("给爷爬okhttp返回数据:"+data);
                if (data.contains("访问被禁止")) {
                    ToastUtils.showShort(context, "不能爬");
                    return;
                }
                List<List<String>> arr = new ArrayList<>();
                Document doc = Jsoup.parse(data);
                Elements list = doc.getElementById("tablelist").getElementById("tdata").getElementsByClass("t_tr1");
                for (Element element : list) {
//                    LogUtil.i("element:"+element.text());
                    String[] tempArr = element.text().split(" ");
                    arr.add(new ArrayList<String>(Arrays.asList(tempArr[tempArr.length - 1], tempArr[0], tempArr[1], tempArr[2], tempArr[3], tempArr[4], tempArr[5], tempArr[6], tempArr[7], "双")));
                }
                infoListSsq.postValue(arr);
//                LogUtil.i("list:"+arr);
            }
        });
    }

    /* 爬取大乐透彩票数据 */
    public void crawlerDLT(Context context, String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            //请求失败的回调方法
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.i("找不到链接啦，，，" + e);//
            }

            //请求成功的回调方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
//                LogUtil.i("给爷爬okhttp返回数据:"+data);
                if (data.contains("访问被禁止")) {
                    ToastUtils.showShort(context, "不能爬");
                    return;
                }
                List<List<String>> arr = new ArrayList<>();
                Document doc = Jsoup.parse(data);
                Elements list = doc.getElementById("tablelist").getElementById("tdata").getElementsByTag("tr");
//                LogUtil.i("crawlerDLT.list.size():"+list.size());
                for (Element element : list) {
//                    LogUtil.i("element:"+element.text());
                    String[] tempArr = element.text().split(" ");
                    arr.add(new ArrayList<String>(Arrays.asList(tempArr[tempArr.length - 1], tempArr[0], tempArr[1], tempArr[2], tempArr[3], tempArr[4], tempArr[5], tempArr[6], tempArr[7], "大")));
                }
                infoListDlt.postValue(arr);
//                LogUtil.i("list:"+arr);
            }
        });
    }

    /* 从数据库获取最新的幸运数字集合 */
    public void updateLuckyNumByDB() {
        List<List<String>> list=listLuckyNum.getValue();
        list.add(LuckNumberGeneratorKt.getLuckyNums());
        listLuckyNum.setValue(list);
    }
}
