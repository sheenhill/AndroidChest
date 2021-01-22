package com.sheenhill.rusuo.baidutrans;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5编码相关的类
 * 只能获得字符串的MD5值
 */
public class EncodeByMD5 {
    //首先初始化
    private static final char[] hexDigits={'0','1','2','3','4','5','6','7','8','9',
            'a','b','c','d','e','f'};

    public static String encode(String input){
        if(input==null)
            return null;
        try {
            //拿到一个MD5转换器。如果想要SHA1参数，"MD5"换成 "SHA1"
            MessageDigest messageDigest=MessageDigest.getInstance("MD5");
            //输入的字符串转换成字节数组
            byte[] inputByteArray=new byte[0];
            try {
                inputByteArray =input.getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //inputByteArray是输入字符串转换得到字节数组
            messageDigest.update(inputByteArray);
            //转换并返回结果，也是字节数组，包括16个元素
            byte[] resultByteArray =messageDigest.digest();
            //字符数组转换成十六进制的字符串返回
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static String byteArrayToHex(byte[] byteArray){
        // new一个字符数组，这个就是用来组成结果字符串的
        // 解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
        char[] resultCharArray =new char[byteArray.length*2];
        int index =0;
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        for(byte b:byteArray){
            resultCharArray[index++] =hexDigits[b>>>4&0xf];
            resultCharArray[index++] =hexDigits[b&0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }
}
