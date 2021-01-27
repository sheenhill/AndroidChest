package com.sheenhill.rusuo.v2.index;

import android.text.SpannableString;

import java.util.List;

public class BingPicBean {
    /**
     * images : [{"startdate":"20190911","fullstartdate":"201909111600","enddate":"20190912","url":"/th?id=OHR.MilkyWayCanyonlands_ZH-CN2363274510_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp","urlbase":"/th?id=OHR.MilkyWayCanyonlands_ZH-CN2363274510","copyright":"在犹他州峡谷地国家公园中看到的银河 (© Cavan Images/Offset)","copyrightlink":"https://www.bing.com/search?q=%E5%B3%A1%E8%B0%B7%E5%9C%B0%E5%9B%BD%E5%AE%B6%E5%85%AC%E5%9B%AD&form=hpcapt&mkt=zh-cn","title":"","quiz":"/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20190911_MilkyWayCanyonlands%22&FORM=HPQUIZ","wp":true,"hsh":"984348a03ad08bb94a9aab1593755713","drk":1,"top":1,"bot":1,"hs":[]},{"startdate":"20190905","fullstartdate":"201909051600","enddate":"20190906","url":"/th?id=OHR.ElMorro_ZH-CN1911346184_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp","urlbase":"/th?id=OHR.ElMorro_ZH-CN1911346184","copyright":"旧圣胡安的圣费利佩海角城堡，波多黎各 (© grandriver/Getty Images)","copyrightlink":"https://www.bing.com/search?q=%E5%9C%A3%E8%B4%B9%E5%88%A9%E4%BD%A9%E6%B5%B7%E8%A7%92%E5%9F%8E%E5%A0%A1&form=hpcapt&mkt=zh-cn","title":"","quiz":"/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20190905_ElMorro%22&FORM=HPQUIZ","wp":true,"hsh":"8a9f779449f6dbaa815220a2ebb3f9b0","drk":1,"top":1,"bot":1,"hs":[]}]
     * tooltips : {"loading":"正在加载...","previous":"上一个图像","next":"下一个图像","walle":"此图片不能下载用作壁纸。","walls":"下载今日美图。仅限用作桌面壁纸。"}
     */

    private TooltipsBean tooltips;
    private List<ImagesBean> images;

    public TooltipsBean getTooltips() {
        return tooltips;
    }

    public void setTooltips(TooltipsBean tooltips) {
        this.tooltips = tooltips;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class TooltipsBean {
        /**
         * loading : 正在加载...
         * previous : 上一个图像
         * next : 下一个图像
         * walle : 此图片不能下载用作壁纸。
         * walls : 下载今日美图。仅限用作桌面壁纸。
         */

        private String loading;
        private String previous;
        private String next;
        private String walle;
        private String walls;

        public String getLoading() {
            return loading;
        }

        public void setLoading(String loading) {
            this.loading = loading;
        }

        public String getPrevious() {
            return previous;
        }

        public void setPrevious(String previous) {
            this.previous = previous;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getWalle() {
            return walle;
        }

        public void setWalle(String walle) {
            this.walle = walle;
        }

        public String getWalls() {
            return walls;
        }

        public void setWalls(String walls) {
            this.walls = walls;
        }
    }

    public static class ImagesBean {
        /**
         * startdate : 20190911
         * fullstartdate : 201909111600
         * enddate : 20190912
         * url : /th?id=OHR.MilkyWayCanyonlands_ZH-CN2363274510_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp
         * urlbase : /th?id=OHR.MilkyWayCanyonlands_ZH-CN2363274510
         * copyright : 在犹他州峡谷地国家公园中看到的银河 (© Cavan Images/Offset)
         * copyrightlink : https://www.bing.com/search?q=%E5%B3%A1%E8%B0%B7%E5%9C%B0%E5%9B%BD%E5%AE%B6%E5%85%AC%E5%9B%AD&form=hpcapt&mkt=zh-cn
         * title :
         * quiz : /search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20190911_MilkyWayCanyonlands%22&FORM=HPQUIZ
         * wp : true
         * hsh : 984348a03ad08bb94a9aab1593755713
         * drk : 1
         * top : 1
         * bot : 1
         * hs : []
         */

        // url like this:https://www.bing.com/th?id=OHR.MilkyWayCanyonlands_ZH-CN2363274510_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp

        private String startdate;
        private String fullstartdate;
        private String enddate;
        private String url;
        private String urlbase;
        private String copyright;
        private String copyrightlink;
        private String title;
        private String quiz;
        private boolean wp;
        private String hsh;
        private int drk;
        private int top;
        private int bot;
        private List<?> hs;

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getFullstartdate() {
            return fullstartdate;
        }

        public void setFullstartdate(String fullstartdate) {
            this.fullstartdate = fullstartdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlbase() {
            return urlbase;
        }

        public void setUrlbase(String urlbase) {
            this.urlbase = urlbase;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public String getCopyrightlink() {
            return copyrightlink;
        }

        public void setCopyrightlink(String copyrightlink) {
            this.copyrightlink = copyrightlink;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getQuiz() {
            return quiz;
        }

        public void setQuiz(String quiz) {
            this.quiz = quiz;
        }

        public boolean isWp() {
            return wp;
        }

        public void setWp(boolean wp) {
            this.wp = wp;
        }

        public String getHsh() {
            return hsh;
        }

        public void setHsh(String hsh) {
            this.hsh = hsh;
        }

        public int getDrk() {
            return drk;
        }

        public void setDrk(int drk) {
            this.drk = drk;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getBot() {
            return bot;
        }

        public void setBot(int bot) {
            this.bot = bot;
        }

        public List<?> getHs() {
            return hs;
        }

        public void setHs(List<?> hs) {
            this.hs = hs;
        }
    }
}
