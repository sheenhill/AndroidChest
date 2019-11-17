package com.example.roy.recycleviewtest.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.roy.recycleviewtest.R;
import com.example.roy.recycleviewtest.baidutrans.TransApi;
import com.example.roy.recycleviewtest.base.BaseActivity;
import com.example.roy.recycleviewtest.entity.TransBean;
import com.example.roy.recycleviewtest.util.LogUtil;
import com.example.roy.recycleviewtest.util.PhotoUtil;
import com.example.roy.recycleviewtest.util.ToastUtils;
import com.google.gson.Gson;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class TensorFlowLiteActivity extends BaseActivity implements View.OnClickListener {
    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20181005000215537";
    private static final String SECURITY_KEY = "kqOpGnOD9O0PQJz43aMh";
    private static final int USE_PHOTO = 1001;
    private static final int START_CAMERA = 1002;
    private NetworkChangeReceiver networkChangeReceiver;
    private String camera_image_path;
    private boolean hasNetwork=false;
    private boolean isLoadModel = false;
    private Interpreter tflite = null;
    private int model_index=0;

    private List<String> resultLabel = new ArrayList<>();
    private int[] ddims = {1, 3, 224, 224};
    private static final Map<String, String> mapPADDLEModel = new HashMap<>();
    private static final String[] PADDLE_MODEL = {
            "mobilenet_v1",
            "mobilenet_v2"
    };
    @BindView(R.id.show_image)
    ImageView showImage;
    @BindView(R.id.result_text)
    TextView resultText;
    @BindView(R.id.trans_text)
    TextView transText;
    @BindView(R.id.load_model)
    Button loadModel;
    @BindView(R.id.use_photo)
    Button usePhoto;
    @BindView(R.id.start_camera)
    Button startCamera;

    // request permissions 6.0后获取权限
    private void request_permissions() {

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.INTERNET);
        }
        // if list is not empty will request permissions
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.load_model:
                showDialog();
                break;
            case R.id.use_photo:
                if(!isLoadModel){
                    ToastUtils.showShort(this,"未加载模型");
                    return;
                }
                PhotoUtil.use_photo(this,USE_PHOTO);
                break;
            case R.id.start_camera:
                if(!isLoadModel){
                    ToastUtils.showShort(this,"未加载模型");
                    return;
                }
                camera_image_path=PhotoUtil.startCamera(this,START_CAMERA);
                break;
        }
    }

    @Override
    protected int setStatusBarColor() {
        return R.color.colorAccent;
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.tensor_flow_lite_activity);
    }

    @Override
    protected void initView() {
        request_permissions();
    }

    @Override
    protected void initData() {
        mapPADDLEModel.put("mobilenet_v1", "一号人工智障模型");
        mapPADDLEModel.put("mobilenet_v2", "二号人工智障模型");
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver =new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);

    }

    @Override
    protected void initEvent() {
        loadModel.setOnClickListener(this);
        usePhoto.setOnClickListener(this);
        startCamera.setOnClickListener(this);
        readCacheLabelFromLocalFile();
    }

    /**
     * 点击相册或拍照返回照片地址
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String image_path;
        RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case USE_PHOTO:
                    if (data == null) {
                        LogUtil.w("相册好像没东西");
                        return;
                    }
                    Uri image_uri = data.getData();
                    Glide.with(this).load(image_uri).apply(options).into(showImage);
                    // get image path from uri
                    image_path = PhotoUtil.get_path_from_URI(this, image_uri);
                    // predict image
                    predict_image(image_path);
                    break;
                case START_CAMERA:
                    // show photo
                    Glide.with(this).load(camera_image_path).apply(options).into(showImage);
                    // predict image
                    predict_image(camera_image_path);
                    break;
            }
        }
    }

    /**
     * 把模型文件读取成MappedByteBuffer，之后给Interpreter类初始化模型，这个模型存放在main的assets目录下。
     * Memory-map the model file in Assets.
     */
    private MappedByteBuffer loadModelFile(String model) throws IOException {
        AssetFileDescriptor fileDescriptor=getApplicationContext().getAssets().openFd(model+".tflite");
        FileInputStream inputStream=new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startOffset=fileDescriptor.getStartOffset();
        long declaredLength=fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);
    }

    /**
     * load infer model
     * 加载模型，并得到一个对象tflite，之后就是使用这个对象来预测图像，同时可以使用这个对象设置一些参数，比如设置使用的线程数量tflite.setNumThreads(4)
     * @param model
     */
    private void loadModel(String model){
        try{
            tflite=new Interpreter(loadModelFile(model));
            ToastUtils.showShort(this,model+"加载成功");
            LogUtil.d(model + " model load success");
            tflite.setNumThreads(4);
            isLoadModel=true;
        }catch (IOException e){
            ToastUtils.showShort(this,model+"加载失败");
            LogUtil.d(model + " model load fail");
            isLoadModel=false;
            e.printStackTrace();
        }
    }

    /**
     * 显示弹窗，通过这个弹窗的选择不同的模型。
     */
    private void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(TensorFlowLiteActivity.this);
        builder.setTitle("选择模型");
        builder.setCancelable(true);
        builder.setNegativeButton("取消",null);
        builder.setSingleChoiceItems(PADDLE_MODEL, model_index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                model_index=i;
                loadModel(PADDLE_MODEL[model_index]);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 读取文件种分类标签对应的名称，这个文件比较长，
     * 可以参考 https://blog.csdn.net/haoji007/article/details/76782642 获取标签名称，也可以下载笔者的项目，里面有对用的文件。
     * 这个文件cacheLabel.txt跟模型一样存放在assets目录下
     */
    private void readCacheLabelFromLocalFile(){
        BufferedReader reader;
        try {
            AssetManager assetManager = getApplicationContext().getAssets();
            reader = new BufferedReader(new InputStreamReader(assetManager.open("cacheLabel.txt")));
            String readLine;
            while ((readLine=reader.readLine())!=null){
            resultLabel.add(readLine);
            }
            reader.close();
        }catch (Exception e){
            LogUtil.e("labelCache"+e);
        }
    }
    /**
     * 预测图片并显示结果的，预测的流程是：获取图片的路径，然后使用对图片进行压缩，
     * 之后把图片转换成ByteBuffer格式的数据，最后调用子线程进行预测
     * @param image_path
     */
    private void predict_image(String image_path){
        Bitmap bmp=PhotoUtil.getScaleBitmap(image_path);//压缩
        ByteBuffer inputData=PhotoUtil.getScaledMatrix(bmp,ddims);//转换格式的数据
        try {
            float[][] labelProArray = new float[1][1001];
            tflite.run(inputData, labelProArray);
            float[] results = new float[labelProArray[0].length];
            System.arraycopy(labelProArray[0], 0, results, 0, labelProArray[0].length);
            int r = get_max_result(results);
            String show_text = resultLabel.get(r);
            resultText.setText(show_text);
            if (hasNetwork) {
                sendRequestWithHttpURLConnection(r);
            } else {
                transText.setTextColor(getResources().getColor(R.color.textColorPrimary));
                transText.setTextSize(14);
                transText.setText("小老弟，你是不是网不好哇！");
            }
        }catch (Exception e){
            LogUtil.d("tflite好像有点问题："+e);
        }
    }

    /**
     * get max probability label
     * 获取最大概率的标签
     * @param   result 预测的标签数组
     * @return  最大标签的位置
     */
    private int get_max_result(float[] result){
        float probability=result[0];
        int r=0;
        for (int i=0;i<result.length;i++){
            if(probability<result[i]){
                probability=result[i];
                r=i;
            }
        }
        return r;
    }

    /**
     * @param   query
     * @return  生成json需要的字符
     */
    public String getTrans(String query){
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        return api.getTransResult(query, "en", "zh");
    }
    /**
     * 解析出百度翻译api返回的json数组中翻译部分
     * @param r
     */
    private void sendRequestWithHttpURLConnection(final int r){
        //开启线程发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.w(getTrans(resultLabel.get(r)));
                showResponse(parseJSONWithGSON(getTrans(resultLabel.get(r))));
            }
        }).start();
    }

    /**
     * 从子线程里获取的翻译更新在UI中
     * @param response  字符串
     */
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                transText.setText(response);
            }
        });
    }

    /**
     * 解析出百度翻译api返回的json中翻译的部分
     * @param jsonData  百度翻译api返回的json
     * @return          json中翻译的部分
     */
    private String parseJSONWithGSON(String jsonData) {
        Gson gson=new Gson();
        TransBean trans=gson.fromJson(jsonData,TransBean.class);
        List<TransBean.TransResultBean> transResultBeanList=trans.getTrans_result();
        return transResultBeanList.get(0).getDst();
    }

    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null&&networkInfo.isAvailable())
                hasNetwork=true;
            else
                hasNetwork=false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
}
