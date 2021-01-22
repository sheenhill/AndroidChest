package com.sheenhill.rusuo.base;

public final class APIs {
    // 妹子福利图api
    public static final String GIRLS = "https://gank.io/api/data/福利/10/1";

    // 最新一期的双色球api
    public static final String LASTEST_SHUANGSEQIU = "https://www.mxnzp.com/api/lottery/ssq/latest";

    // 必应壁纸  原图1920*1080   可以设置到800*480（减少2/3内存）
    // idx：起始  n：图片个数   idx和n参数的值自己接字符串
    // 例如  （从今天开始往前拿三张壁纸） "https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=3";
    public static final String PICs_BING="https://www.bing.com/HPImageArchive.aspx?format=js&";
}
