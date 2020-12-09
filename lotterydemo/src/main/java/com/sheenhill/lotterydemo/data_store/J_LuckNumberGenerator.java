package com.sheenhill.lotterydemo.data_store;

import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class J_LuckNumberGenerator {
    // 前区1-35个号码
    private static ArrayList<String> ticket1 = (ArrayList<String>) Stream.of("01", "02", "03", "04", "05", "06", "07", "08",
            "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30", "31", "32", "33", "34", "35").collect(Collectors.toList());
    // 后区1-12个号码
    private static ArrayList<String> ticket2 = (ArrayList<String>) Stream
            .of("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
            .collect(Collectors.toList());
    public static List<String> getTicket() {
        final ArrayList<String> res = new ArrayList<>(7);
        final ArrayList<String> res1 = new ArrayList<>(2);
        for (int i = 0; i < 5; i++)
            res.add(ticket1.remove(getIndex(35-i)));
        for (int i = 0; i < 2; i++)
            res1.add(ticket2.remove(getIndex(12-i)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            res.sort(null);
        }
        res1.sort(null);
        res.addAll(res1);
        return res;
    }

    /**
     * 根据指定空间获取随机数（如n=35则随机数位0~34刚好标识35个号的索引）
     */
    public static int getIndex(int n) {
        return (int) (Math.random() * n);
    }
}
