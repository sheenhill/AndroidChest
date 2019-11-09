package com.example.roy.recycleviewtest.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

//start camera
public class PhotoUtil {
    /**
     * 启动相机拍照并返回图片的路径，兼容了Android 7.0
     * @param activity
     * @param requestCode
     * @return
     */
    public static String startCamera(Activity activity, int requestCode){
        Uri imageUri;
        File outputImage=new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                +"/lite_mobile/", System.currentTimeMillis()+".jpg");
        LogUtil.d(outputImage.getAbsolutePath());
        try {
            if(outputImage.exists()){
            outputImage.delete();
        }
        File out_path=new File(Environment.getExternalStorageDirectory()
        .getAbsolutePath()+"/lite_mobile/");
        if(!out_path.exists()){
            out_path.mkdir();
        }
            out_path.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=24){
            //兼容7.0以上
            imageUri=FileProvider.getUriForFile(activity,
                    "roy.testtflite.fileprovider",outputImage);
        }else {
            imageUri=Uri.fromFile(outputImage);
        }
        //启动系统相机
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //设置保存相片的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        //设置相片的质量，最低为0，最高为1
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,0);
        activity.startActivityForResult(intent,requestCode);
        //返回相片的绝对路径
        return outputImage.getAbsolutePath();
    }
    // get picture in photo
    /**
     * 打开相册，获取选择的图片的URI
     * @param activity
     * @param requestCode
     */
    public static void use_photo(Activity activity,int requestCode){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent,requestCode);
    }

    /**
     * 把图片的URI转换成图片路径
     * @param context
     * @param uri
     * @return  path
     */
    public static String get_path_from_URI(Context context,Uri uri){
        String result;
        Cursor cursor=context.getContentResolver().query(uri,null,null,null,null);
        if(cursor ==null){
            result=uri.getPath();
        }else{
            cursor.moveToFirst();
            int idx=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result =cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    /**
     * 把图片的Bitmap格式转换成TensorFlow Lite所需的数据格式
     * @param bitmap
     * @param ddims
     * @return  TFLite所需的格式
     */
    public static ByteBuffer getScaledMatrix(Bitmap bitmap,int[] ddims){
        // 缓冲区(Buffer)就是在内存中预留指定大小的存储空间用来对输入/输出(I/O)的数据作临时存储，这部分预留的内存空间就叫做缓冲区
        // 缓冲区的作用也是用来临时存储数据，可以理解为是I/O操作中数据的中转站
        //ByteBuffer是其中一种
        ByteBuffer imgData=ByteBuffer.allocateDirect(ddims[0]*ddims[1]*ddims[2]*ddims[3]*4);
        //.allocateDirect()方法是不使用JVM堆栈而是通过操作系统来创建内存块用作缓冲区，它与当前操作系统能够更好的耦合，
        // 因此能进一步提高I/O操作速度。但是分配直接缓冲区的系统开销很大，因此只有在缓冲区较大并长期存在，或者需要经常重用时，才使用这种缓冲区
        imgData.order(ByteOrder.nativeOrder());
        //get image pixel
        int[] pixels=new int[ddims[2]*ddims[3]];
        Bitmap bm=Bitmap.createScaledBitmap(bitmap,ddims[2],ddims[3],false);
        bm.getPixels(pixels,0,bm.getWidth(),0,0,ddims[2],ddims[3]);
        int pixel =0;
        for (int i=0;i<ddims[2];++i){
            for(int j=0;j<ddims[3];++j){
                final int val=pixels[pixel++];
                imgData.putFloat(((((val >> 16) & 0xFF) - 128f) / 128f));
                imgData.putFloat(((((val >> 8) & 0xFF) - 128f) / 128f));
                imgData.putFloat((((val & 0xFF) - 128f) / 128f));
            }
        }
        if(bm.isRecycled()){
            bm.recycle();
        }
        return imgData;
    }
    /**
     * 压缩图片，防止内存溢出
     * @param filePath
     * @return
     */
    public static Bitmap getScaleBitmap(String filePath){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(filePath,options);

        int bmpWidth=options.outWidth;
        int bmpHeight=options.outHeight;
        int maxSize=500;
        // compress picture with inSampleSize
        options.inSampleSize=1;
        while (true){
            if(bmpWidth/options.inSampleSize<maxSize && bmpHeight/options.inSampleSize<maxSize){
                break;//若是原尺寸宽高不超过500，就不裁剪
            }
            options.inSampleSize *=2;//若超过，每次宽高均裁剪一半，即四倍。如此往复，直至不超过。
        }
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeFile(filePath,options);
    }

}
