package com.sheenhill.rusuo.entity;

import androidx.annotation.NonNull;

import org.litepal.crud.LitePalSupport;

import java.text.SimpleDateFormat;
import java.util.Date;


public class HoursPlan extends LitePalSupport{
//    private int id;
    private int thisStudyTime;
    private int residualTime;
    private Date time;


    public String getThisStudyTime() {
        return String.valueOf(thisStudyTime);
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


    public String getResidualTime() {
        return String.valueOf(residualTime);
    }

    public void setResidualTime(int residualTime) {
        this.residualTime = residualTime;
    }

    public String getFormatTime() {
        if(getTime()!=null){
        SimpleDateFormat timeSDF = new SimpleDateFormat("MM月dd日 HH:mm");
        return timeSDF.format(getTime());
        }
        return null;
    }

    @NonNull
    @Override
    public String toString() {
        return "本次学习"+getThisStudyTime()+"h  剩余时间："+getResidualTime()+"h  时间："+getThisStudyTime();
    }
}
