package com.example.roy.recycleviewtest.entity;

import org.litepal.crud.LitePalSupport;

import java.text.SimpleDateFormat;
import java.util.Date;


public class HoursPlan extends LitePalSupport{
//    private int id;
    private int thisStudyTime;
    private int residualTime;
    private Date time;


    private String formatTime;

    public int getThisStudyTime() {
        return thisStudyTime;
    }

    public void setThisStudyTime(int thisStudyTime) {
        this.thisStudyTime = thisStudyTime;
    }


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    public int getResidualTime() {
        return residualTime;
    }

    public void setResidualTime(int residualTime) {
        this.residualTime = residualTime;
    }

    public String getFormatTime() {
        SimpleDateFormat timeSDF = new SimpleDateFormat("MM月dd日 HH:mm");
        return timeSDF.format(getTime());
    }

}
