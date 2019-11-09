package com.example.roy.recycleviewtest.entity;

import android.net.Uri;

import java.util.List;

public class GirlBean {

    /**
     * error : false
     * results : [{"_id":"5bbb0de09d21226111b86f1c","createdAt":"2018-10-08T07:57:20.978Z","desc":"2018-10-08","publishedAt":"2018-10-08T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqly1fw0vdlg6xcj30j60mzdk7.jpg","used":true,"who":"lijinshanmx"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public class ResultsBean {
        /**
         * _id : 5bbb0de09d21226111b86f1c
         * createdAt : 2018-10-08T07:57:20.978Z
         * desc : 2018-10-08
         * publishedAt : 2018-10-08T00:00:00.0Z
         * source : web
         * type : 福利
         * url : https://ws1.sinaimg.cn/large/0065oQSqly1fw0vdlg6xcj30j60mzdk7.jpg
         * used : true
         * who : lijinshanmx
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public float getScale() {
            return scale;
        }

        public void setScale(float scale) {
            this.scale = scale;
        }

        private float scale;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }
    }
}
