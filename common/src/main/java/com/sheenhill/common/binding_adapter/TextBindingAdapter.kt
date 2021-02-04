package com.sheenhill.common.binding_adapter

import android.content.res.AssetManager
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sheenhill.common.util.LogUtil


//object textType{
//    object "water"
//}

/**
 * 设置每个item的文字样式
 * 给RV.item更复杂的样式（例如描边，水印）还可以用itemDecoration实现，不过没这种方法灵活方便
 * （描边用MaskFilter实现）
 *
 * type:可能有更多的样式
 * text:正常在BindingAdapter中可以通过tv.text拿到text的字符串的，不过在RV中，
 * RV复用View,不会保存View样式状态，View的样式应该用数据驱动，
 * 所以我们通过onBindViewHolder中不停传入的text来驱动视图样式，才能保证预期
 *
 */
@BindingAdapter(value = ["style", "text"], requireAll = false)
fun multiStyleText(tv: TextView, style: String, text: String) {
    if (!TextUtils.isEmpty(text)) {
        val arr = text.trim().split("(")
        var str: String
        if (arr.size > 1) {
            str = """
                ${arr[0]}
                ${arr[1].replace(")", "")}
            """.trimIndent()
            if (style == "watermark") {
                val spannableString = SpannableString(str)
                val firstFGColorSpan = ForegroundColorSpan(Color.WHITE) // 第一段文字颜色
//                val firstMaskFilterSpan = MaskFilterSpan(BlurMaskFilter(1.5F, Blur.SOLID))// 内外发光相关  https://blog.csdn.net/abcdef314159/article/details/52442208
                val boldSpan = StyleSpan(Typeface.BOLD)  // 字重
                val secondFGColorSpan = ForegroundColorSpan(Color.parseColor("#99ffffff"))// 第二段文字颜色
                val secondRelativeSizeSpan = RelativeSizeSpan(0.8f)
                spannableString.setSpan(firstFGColorSpan, 0, arr[0].length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(boldSpan, 0, arr[0].length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//                spannableString.setSpan(firstMaskFilterSpan, 0, arr[0].length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(secondFGColorSpan, arr[0].length, arr[0].length + arr[1].length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(secondRelativeSizeSpan, arr[0].length, arr[0].length + arr[1].length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                tv.text = spannableString
            }
        }
    }
    //  text示例>>>>>> "copyright":"横跨康涅狄格河畔血溪的铁路，新罕布什尔州汉诺威 (© DEEPOL by plainpicture)",
}

// 给部分文字设置字体
@BindingAdapter(value = ["type"], requireAll = true)
fun setTitleFont(tv: TextView, type: String) {
    if (type == "title") {
        //从asset 读取字体，得到AssetManager
        val mgr: AssetManager = tv.context!!.assets
        //根据路径得到Typeface
        val tf = Typeface.createFromAsset(mgr, "webfont.ttf")
//        val spannableString = SpannableString(tv.text!!)
//        spannableString.setSpan(tf, 0, tv.text!!.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        LogUtil.i("setTitleFont>>>>>>${tv.text}>>>>${tf}")
//        tv.text = spannableString
        tv.typeface = tf
    }
}