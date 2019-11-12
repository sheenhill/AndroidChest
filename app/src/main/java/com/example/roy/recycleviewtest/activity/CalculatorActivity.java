package com.example.roy.recycleviewtest.activity;

import android.content.Intent;

import androidx.constraintlayout.widget.Guideline;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.roy.recycleviewtest.R;
import com.example.roy.recycleviewtest.base.BaseActivity;
import com.example.roy.recycleviewtest.fragment.BottomDialogFragment;
import com.example.roy.recycleviewtest.util.ToastUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

public class CalculatorActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.view)
    View view;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_hint1)
    TextView tvHint1;
    @BindView(R.id.et_loan)
    EditText etLoan;
    @BindView(R.id.tv_hint2)
    TextView tvHint2;
    @BindView(R.id.et_interest)
    EditText etInterest;
    @BindView(R.id.tv_hint3)
    TextView tvHint3;
    @BindView(R.id.et_start_time)
    EditText etStartTime;
    @BindView(R.id.btn_start_time)
    Button btnStartTime;
    @BindView(R.id.et_end_time)
    EditText etEndTime;
    @BindView(R.id.btn_end_time)
    Button btnEndTime;
    @BindView(R.id.btn_calculate)
    Button btnCalculate;
    @BindView(R.id.btn_generating_bill)
    Button btnGeneratingBill;
    @BindView(R.id.calculator_view_stub)
    ViewStub calculatorViewStub;
    @BindView(R.id.gl_left)
    Guideline glLeft;
    @BindView(R.id.gl_right)
    Guideline glRight;
    @BindView(R.id.gl_between)
    Guideline glBetween;
    @BindView(R.id.scroll_text_view_stub)
    ViewStub scrollViewStub;

    TextView tvAggregate;
    int oneYearDays = 365;
    long s1 = 0;
    long s2 = 0;
    int day = 0;
    double aggregate = 0;
    double count;
    String s;
    StringBuffer sbAggregate;
    DecimalFormat df = new DecimalFormat("0.00");//格式化小数


    private String show_1="";
    private String show_2="";
    private String show_3="";
    private String show_4="";
    private String show_5="";


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_time:
                hideSoftKeyBoard(this);
                showStartDate();
                break;
            case R.id.btn_end_time:
                hideSoftKeyBoard(this);
                showEndDate();
                break;
            case R.id.btn_calculate:
                if (etLoan.getText().toString().trim().isEmpty())
                    ToastUtils.showShort(this, "填好必填项！");
                else if (etInterest.getText().toString().trim().isEmpty())
                    ToastUtils.showShort(this, "填好必填项！");
                else if (etStartTime.getText().toString().trim().isEmpty())
                    ToastUtils.showShort(this, "填好必填项！");
                else if (etEndTime.getText().toString().trim().isEmpty())
                    ToastUtils.showShort(this, "填好必填项！");
                else {
                    scrollViewStub.inflate();
                    //可以通过Acivity的findViewById方法获取TextView对象
                    tvAggregate=this.findViewById(R.id.tv_aggregate);
                    day = (int) ((s2 - s1) / 1000 / 60 / 60 / 24);
                    aggregate = compoundInterest(
                            Float.parseFloat(etLoan.getText().toString()),
                            Float.parseFloat(etInterest.getText().toString()),
                            day);
                    sbAggregate = new StringBuffer();
                    sbAggregate.append(df.format(aggregate));//返回的是String类型
                    tvAggregate.setText(sbAggregate);
                    btnCalculate.setVisibility(View.GONE);
//                    calculatorViewStub.inflate();
                    calculatorViewStub.inflate();
                }
                break;
            case R.id.btn_generating_bill:
                show_1=etLoan.getText().toString();
                show_2=etInterest.getText().toString();
                show_3=etStartTime.getText().toString();
                show_4=etEndTime.getText().toString();

                Intent intentBill = new Intent(CalculatorActivity.this, ShareActivity.class);
                intentBill.putExtra("LOAN", show_1);
                intentBill.putExtra("INTEREST", show_2);
                intentBill.putExtra("START_TIME", show_3);
                intentBill.putExtra("END_TIME", show_4);
                intentBill.putExtra("AGGREGATE", show_5);
                startActivity(intentBill);
                break;
        }
    }

    @Override
    protected int setStatusBarColor() {
        return R.color.colorAccent;
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.calculator_activity);
    }

    /**
     * 以下俩个是toolbar中的选项
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_show:
                BottomDialogFragment bottomDialogFr = new BottomDialogFragment();
                bottomDialogFr.show(getSupportFragmentManager(), "DF");
                return true;
            case R.id.menu_close:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        s = intent.getStringExtra("tool_title");
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        int thisYear = Integer.parseInt(format.format(new Date()));
        if (thisYear % 4 == 0) {
            oneYearDays = 366;
        }
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolBar);
        toolBar.setTitle(s);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        etStartTime.setFocusable(false);
        etEndTime.setFocusable(false);

    }

    @Override
    protected void initEvent() {
        btnStartTime.setOnClickListener(this);
        btnEndTime.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);
        btnGeneratingBill.setOnClickListener(this);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }


    public void showStartDate() {
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                etStartTime.setText(getTime(date));
                s1 = date.getTime();//将时间转为毫秒
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                //               .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                //.setTitleText("Title")//标题文字
//                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
//                //.setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                //.setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
////                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
////                .setRangDate(startDate,endDate)//起始终止年月日设定
//                //.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                //.isDialog(true)//是否显示为对话框样式
                .build();

        pvTime.show();
    }

    public void showEndDate() {
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                etEndTime.setText(getTime(date));
                s2 = date.getTime();//将时间转为毫秒
            }
        }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();

        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        //"YYYY-MM-DD HH:MM:SS"        "yyyy-MM-dd"
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 删除一条数据
     *
     * @param presentValue 初始金额
     * @param interest     日化利息，利率或折现率
     * @param number       计息次数（按天计算）
     */
    private double compoundInterest(float presentValue, double interest, int number) {
        double f;//终值
        double i = interest / oneYearDays / 100;
        f = presentValue * Math.pow(1 + i, number);
        return twoDecimal(f);
    }

    private double twoDecimal(double td) {
        BigDecimal bd = new BigDecimal(td);
        return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public void goonClick(View view) {
        if (etLoan.getText().toString().trim().isEmpty())
            ToastUtils.showShort(CalculatorActivity.this, "填好必填项！");
        else if (etInterest.getText().toString().trim().isEmpty())
            ToastUtils.showShort(CalculatorActivity.this, "填好必填项！");
        else if (etStartTime.getText().toString().trim().isEmpty())
            ToastUtils.showShort(CalculatorActivity.this, "填好必填项！");
        else if (etEndTime.getText().toString().trim().isEmpty())
            ToastUtils.showShort(CalculatorActivity.this, "填好必填项！");
        else {
            day = (int) ((s2 - s1) / 1000 / 60 / 60 / 24);
            count = compoundInterest(
                    Float.parseFloat(etLoan.getText().toString()),
                    Float.parseFloat(etInterest.getText().toString()),
                    day);
            sbAggregate.append("+" + df.format(count));
            aggregate = aggregate + count;
            //返回的是String类型
            sbAggregate.append("\n" + df.format(aggregate));
            tvAggregate.setText(sbAggregate);

            show_5=df.format(aggregate); // show_5
        }
    }
    public void refreshClick(View view) {
        finish();
        CalculatorActivity.actionStart(CalculatorActivity.this, CalculatorActivity.class);
    }

}
