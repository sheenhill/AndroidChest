package com.sheenhill.rusuo.entity;

public class ShuangseqiuBean {
    /**
     * code : 1
     * msg : 该接口将在不久后停用，请替换到新版本接口【/common/latest】
     * data : {"openCode":"04,12,14,21,27,29+12","code":"ssq","expect":"19103","name":"双色球","time":"2019-09-03 21:18:20"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * openCode : 04,12,14,21,27,29+12
         * code : ssq
         * expect : 19103
         * name : 双色球
         * time : 2019-09-03 21:18:20
         */

        private String openCode;
        private String code;
        private String expect;
        private String name;
        private String time;

        public String getOpenCode() {
            return openCode;
        }

        public void setOpenCode(String openCode) {
            this.openCode = openCode;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getExpect() {
            return expect;
        }

        public void setExpect(String expect) {
            this.expect = expect;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
