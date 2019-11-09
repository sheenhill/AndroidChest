package com.example.roy.recycleviewtest.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roy.recycleviewtest.R;
import com.example.roy.recycleviewtest.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShareActivity extends BaseActivity {

    @BindView(R.id.imageBitmap)
    ImageView imageBitmap;

    StringBuffer stringBuffer;

    @Override
    protected void setContentView() {
        setContentView(R.layout.share_activity);
    }

    @Override
    protected void initView() {
        imageBitmap.setImageBitmap(getShareingBitmap(70));
    }

    @Override
    protected void initData() {
        Intent intent=getIntent();
        stringBuffer=new StringBuffer("借款金额"+intent.getStringExtra("LOAN")+"元\n");
        stringBuffer.append("年化利息："+intent.getStringExtra("INTEREST")+"%\n");
        stringBuffer.append("起止时间："+intent.getStringExtra("START_TIME")+"-"+intent.getStringExtra("END_TIME")+"\n");
        stringBuffer.append("合计："+intent.getStringExtra("AGGREGATE"+"元"));
    }

    @Override
    protected void initEvent() {
    }

    /**
     * 将资源文件转换成Drawabled对象然后再进行转换*/
    public Bitmap getBitmap1(){
        Drawable db = getResources().getDrawable(R.mipmap.ic_image);
        BitmapDrawable drawable = (BitmapDrawable)db;
        Bitmap bitmap = drawable.getBitmap();
        return bitmap;
    }

//    /**
//     * 直接使用BitmapFactory进行转换*/
//    public Bitmap getBitmap2(){
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_picc);
//        return bitmap;
//    }
//
//    /**
//     * 使用Canvas进行转换*/
//    public Bitmap getBitmap3(){
//        Drawable db = getResources().getDrawable(R.drawable.logo_picc);
//        Bitmap bitmap = Bitmap.createBitmap(db.getIntrinsicWidth(), db.getIntrinsicHeight(),
//                Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//
//        db.setBounds(0, 0, db.getIntrinsicWidth(), db.getIntrinsicHeight());
//        db.draw(canvas);
//
//        return bitmap;
//    }

    /**
     *  Drawable db = getResources().getDrawable(R.drawable.xxx);
     *  @param drawable Drawable实例，上述方法获得
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
    Bitmap bitmap = Bitmap.createBitmap(
            drawable.getIntrinsicWidth(),
            drawable.getIntrinsicHeight(),
            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565
    );
    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    drawable.draw(canvas);
    return bitmap;
    }

    /**
     * 放大或缩小
     * @param oldbitmap    原bitmap
     * @param scaleWidth   宽度比例
     * @param scaleHeight  高度比例
     * @return  变换过的bitmap
     */
    public Bitmap scaleMatrixImage(Bitmap oldbitmap, float scaleWidth, float scaleHeight) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);// 放大缩小比例
        Bitmap ScaleBitmap = Bitmap.createBitmap(oldbitmap, 0, 0, oldbitmap.getWidth(), oldbitmap.getHeight(), matrix, true);
        return ScaleBitmap;
    }

    /**图片裁剪
     *
     * @param bitmap       Bitmap
     * @param reqWidth     剪裁后宽度
     * @param reqHeight    建材后高度
     * @return             剪裁后位图
     */
    public Bitmap cutImage(Bitmap bitmap, int reqWidth, int reqHeight) {
        Bitmap newBitmap = null;
        if (bitmap.getWidth() > reqWidth && bitmap.getHeight() > reqHeight) {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, reqWidth, reqHeight);
        } else {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
        return bitmap;
    }

    /**
     * 保存到sd卡中
     * @param bitmap  Bitmap
     * @param path    位置
     */
    public void savePic(Bitmap bitmap,String path) {
        File file = new File(path);
        FileOutputStream fileOutputStream = null;
        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 拍摄所得的图片为imageBitmap
    private Bitmap getShareingBitmap(int textSize) {
        Bitmap.Config config = getBitmap1().getConfig();
        int sourceBitmapHeight = getBitmap1().getHeight();
        int sourceBitmapWidth = getBitmap1().getWidth();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK); // 画笔颜色
        TextPaint textpaint = new TextPaint(paint);
        textpaint.setTextSize(textSize); // 文字大小
        textpaint.setAntiAlias(true); // 抗锯齿
        //内容
        StaticLayout context= new StaticLayout(stringBuffer, textpaint,
                sourceBitmapWidth, Layout.Alignment.ALIGN_NORMAL, 1f, 1f, true);

        Bitmap share_bitmap = Bitmap.createBitmap(sourceBitmapWidth, sourceBitmapHeight +
                        context.getHeight(), config);
        Canvas canvas = new Canvas(share_bitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(getBitmap1(), 0, 0, paint); // 绘制图片
        canvas.translate(0, sourceBitmapHeight);
        context.draw(canvas);
        return share_bitmap;
    }

}
