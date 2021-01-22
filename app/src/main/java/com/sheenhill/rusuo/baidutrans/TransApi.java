package com.sheenhill.rusuo.baidutrans;

import java.util.HashMap;
import java.util.Map;

public class TransApi {
    private static final String TRANS_API_HOST="http://api.fanyi.baidu.com/api/trans/vip/translate";

    private String appId;
    private String securityKey;

    /**
     * @param appId       APP ID
     * @param securityKey 密钥
     */
    public TransApi(String appId,String securityKey){
        this.appId=appId;
        this.securityKey=securityKey;
    }

    public String getTransResult(String query,String from,String to){
        Map<String,String> params=buildParams(query,from,to);
        return TransHttpGet.get(TRANS_API_HOST,params);
    }

    /**
     *
     * @param query  请求翻译query
     * @param from   翻译源语言
     * @param to     译文语言
     * @return    请求
     */
    private Map<String,String> buildParams(String query,String from,String to){
        Map<String,String> params=new HashMap<>();
        params.put("q",query);
        params.put("from",from);
        params.put("to",to);

        params.put("appid",appId);

        //随机数
        String salt=String.valueOf(System.currentTimeMillis());
        params.put("salt",salt);

        //签名
        String src=appId+query+salt+securityKey;//加密前的原文
        params.put("sign",EncodeByMD5.encode(src));
        return params;
    }
}
