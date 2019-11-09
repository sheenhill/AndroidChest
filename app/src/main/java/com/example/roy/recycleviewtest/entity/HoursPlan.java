package com.example.roy.recycleviewtest.entity;

import org.litepal.crud.LitePalSupport;

import java.util.Date;


public class HoursPlan extends LitePalSupport{
//    private int id;
    private int thisStudyTime;
    private int residualTime;
    private Date time;

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
}
