/*
 * NOTICE
 *
 * This is the copyright work of The MITRE Corporation, and was produced for
 * the U. S. Government under Contract Number DTFAWA-10-C-00080, and is subject
 * to Federal Aviation Administration Acquisition Management System Clause
 * 3.5-13, Rights In Data-General, Alt. III and Alt. IV (Oct. 1996).  No other
 * use other than that granted to the U. S. Government, or to those acting on
 * behalf of the U. S. Government, under that Clause is authorized without the
 * express written permission of The MITRE Corporation. For further information,
 * please contact The MITRE Corporation, Contracts Office,7515 Colshire Drive,
 * McLean, VA  22102-7539, (703) 983-6000.
 *
 * Approved for Public Release; Distribution Unlimited. Case Number 14-4045
 */

package com.stanza.typeswitcher;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 */
public class TypeSwitcher extends RadioGroup {

    //Helpers
    private int mSdk;
    private Context mCtx;

    //Interaction
    private OnSelectionChangedListener mListener;

    //UI
    private int textSize;
    private int selectedTextSize;
    private int unselectedTextSize;
    private int selectedColor ;
    private int selectedTextColor ;
    private int unselectedColor ;
    private int unselectedTextColor ;
    private int cornersRadius;

    private float[] LeftCornersRadius ;
    private float[] RightCornersRadius ;

    private int defaultSelection;
    private boolean stretch;
    private boolean equalWidth;
    private String identifier;
    private ColorStateList textColorStateList;

    //Item organization
    private LinkedHashMap<String, String> itemMap = new LinkedHashMap<>();

    public TypeSwitcher(Context context) {
        super(context, null);
        //Initialize
        init(context);
    }

    public TypeSwitcher(Context context, AttributeSet attrs) throws Exception {
        super(context, attrs);
        //Initialize
        init(context);
        //Here's where overwrite the defaults with the values from the xml attributes
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TypeSwitcher,
                0, 0);
        textSize= attributes.getDimensionPixelSize(R.styleable.TypeSwitcher_selected_text_size, textSize);
        selectedTextSize = attributes.getDimensionPixelSize(R.styleable.TypeSwitcher_selected_text_size, selectedTextSize);
        unselectedTextSize = attributes.getDimensionPixelSize(R.styleable.TypeSwitcher_unselected_text_size, unselectedTextSize);
        selectedColor = attributes.getColor(R.styleable.TypeSwitcher_selected_color, selectedColor);
        selectedTextColor = attributes.getColor(R.styleable.TypeSwitcher_selected_text_color, selectedTextColor);
        unselectedColor = attributes.getColor(R.styleable.TypeSwitcher_unselected_color, unselectedColor);
        unselectedTextColor = attributes.getColor(R.styleable.TypeSwitcher_unselected_text_color, unselectedTextColor);
        cornersRadius=attributes.getDimensionPixelSize(R.styleable.TypeSwitcher_corners_radius,cornersRadius);
        setCornersRadius(cornersRadius);
        defaultSelection = attributes.getInt(R.styleable.TypeSwitcher_default_selection, defaultSelection);
        equalWidth = attributes.getBoolean(R.styleable.TypeSwitcher_equal_width, equalWidth);
        stretch = attributes.getBoolean(R.styleable.TypeSwitcher_stretch, stretch);
        identifier = attributes.getString(R.styleable.TypeSwitcher_identifier);

        CharSequence[] itemArray = Objects.requireNonNull(attributes.getString(R.styleable.TypeSwitcher_item_arrays)).split(",");
        CharSequence[] valueArray = Objects.requireNonNull(attributes.getString(R.styleable.TypeSwitcher_value_arrays)).split(",");
        // 预览
//        if (this.isInEditMode()) {
//            itemArray = new CharSequence[]{"YES", "NO", "MAYBE", "DON'T KNOW"};
//        }


        if (itemArray.length != valueArray.length) {
            throw new Exception("Item labels 和 value 数组一定要一致");
        }
        for (int i = 0; i < itemArray.length; i++) {
            itemMap.put(itemArray[i].toString(), valueArray[i].toString());
        }


        attributes.recycle();
        //Setup the view
        update();
    }



    // 初始化成员变量
    private void init(Context context) {
        mCtx = context;
        this.setPadding(10, 10, 10, 10);// padding固定
        textSize=14;
        selectedTextSize=textSize;
        unselectedTextSize=textSize;
        selectedColor =  Color.parseColor("#41BAFF");
        selectedTextColor = Color.WHITE;
        unselectedColor = Color.WHITE;
        unselectedTextColor =Color.parseColor("#41BAFF");
        cornersRadius=7;
        LeftCornersRadius = new float[8];
        RightCornersRadius = new float[8];
        defaultSelection=0;
        stretch = false;
        equalWidth = false;
        identifier = "";
        // 状态与颜色对应的类
        textColorStateList = new ColorStateList(new int[][]{
                {-android.R.attr.state_checked}, {android.R.attr.state_checked}},
                new int[]{unselectedTextColor, selectedTextColor}
        );
    }

    /**
     * Does the setup and re-setup of the view based on the currently set options
     */
    @TargetApi(16)
    private void update() {
        this.removeAllViews();
        // 边界  固定值：1
        int border = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        this.setOrientation(RadioGroup.HORIZONTAL);// 方向


        float textWidth = 0;
        List<RadioButton> options = new ArrayList<>();

        Iterator itemIterator = itemMap.entrySet().iterator();
        int i = 0;
        while (itemIterator.hasNext()) {
            Map.Entry<String, String> item = (Map.Entry) itemIterator.next();
            RadioButton rb = new RadioButton(mCtx);
            rb.setTextColor(textColorStateList);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (stretch) {
                params.weight = 1.0f;
            }
            if (i > 0) {
                params.setMargins(-border, 0, 0, 0);
            }
            rb.setLayoutParams(params);
            //Clear out button drawable (text only)
            rb.setButtonDrawable(new StateListDrawable());

            //设置背景
            if (i == 0) {
                //Left
                GradientDrawable leftUnselected = (GradientDrawable) mCtx.getResources().getDrawable(R.drawable.left_option).mutate();
                leftUnselected.setStroke(border, selectedColor);
                leftUnselected.setCornerRadii(LeftCornersRadius);
                leftUnselected.setColor(unselectedColor);

                GradientDrawable leftSelected = (GradientDrawable) mCtx.getResources().getDrawable(R.drawable.left_option_selected).mutate();
                leftSelected.setColor(selectedColor);
                leftSelected.setStroke(border, selectedColor);
                leftSelected.setCornerRadii(LeftCornersRadius);

                StateListDrawable leftStateListDrawable = new StateListDrawable();
                leftStateListDrawable.addState(new int[]{-android.R.attr.state_checked}, leftUnselected);
                leftStateListDrawable.addState(new int[]{android.R.attr.state_checked}, leftSelected);

                rb.setBackground(leftStateListDrawable);
            } else if (i == (itemMap.size() - 1)) {
                //Right
                GradientDrawable rightUnselected = (GradientDrawable) mCtx.getResources().getDrawable(R.drawable.right_option).mutate();
                rightUnselected.setStroke(border, selectedColor);

                rightUnselected.setColor(unselectedColor);
                rightUnselected.setCornerRadii(RightCornersRadius);
                GradientDrawable rightSelected = (GradientDrawable) mCtx.getResources().getDrawable(R.drawable.right_option_selected).mutate();
                rightSelected.setColor(selectedColor);
                rightSelected.setStroke(border, selectedColor);
                rightSelected.setCornerRadii(RightCornersRadius);

                StateListDrawable rightStateListDrawable = new StateListDrawable();
                rightStateListDrawable.addState(new int[]{-android.R.attr.state_checked}, rightUnselected);
                rightStateListDrawable.addState(new int[]{android.R.attr.state_checked}, rightSelected);
                rb.setBackground(rightStateListDrawable);
            } else {
                //Middle
                GradientDrawable middleUnselected = (GradientDrawable) mCtx.getResources().getDrawable(R.drawable.middle_option).mutate();
                middleUnselected.setStroke(border, selectedColor);
                middleUnselected.setDither(true);
                middleUnselected.setColor(unselectedColor);
                GradientDrawable middleSelected = (GradientDrawable) mCtx.getResources().getDrawable(R.drawable.middle_option_selected).mutate();
                middleSelected.setColor(selectedColor);
                middleSelected.setStroke(border, selectedColor);
                StateListDrawable middleStateListDrawable = new StateListDrawable();
                middleStateListDrawable.addState(new int[]{-android.R.attr.state_checked}, middleUnselected);
                middleStateListDrawable.addState(new int[]{android.R.attr.state_checked}, middleSelected);
                rb.setBackground(middleStateListDrawable);
            }
            rb.setLayoutParams(params);
            rb.setMinWidth(border * 10);
            rb.setGravity(Gravity.CENTER);
            rb.setTypeface(null, Typeface.BOLD);
            rb.setText(item.getKey());
//            LogUtil.i("textSize:"+textSize);
            rb.setTextSize(textSize);
            textWidth = Math.max(rb.getPaint().measureText(item.getKey()), textWidth);
            options.add(rb);
            i++;
        }

        // 规定所有RadioButton等宽
        for (RadioButton option : options) {
            if (equalWidth) {
                option.setWidth((int) (textWidth + (border * 20)));
            }
            this.addView(option);
        }

        this.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mListener != null) {
                    mListener.newSelection(identifier, itemMap.get(((RadioButton) group.findViewById(checkedId)).getText().toString()));
                }
            }
        });

        if ((defaultSelection > -1) && (defaultSelection < itemMap.size())) {
            this.check(( getChildAt(defaultSelection)).getId());
        }else {
            this.check((getChildAt(0)).getId());
        }

    }

    //  链式调用最后一步
    public void setup() {
        update();
    }

    // 设置圆角
    public TypeSwitcher setCornersRadius(int cornersRadius) {
        for (int i = 2; i < 6; i++) {
            RightCornersRadius[i] = cornersRadius;
        }
        LeftCornersRadius = new float[]{cornersRadius, cornersRadius, 0, 0, 0, 0, cornersRadius, cornersRadius};
        return this;
    }

    /**
     * Get currently selected segment and the view identifier
     *
     * @return string array of identifier [0] value of currently selected segment [1]
     */
    public String[] getCheckedWithIdentifier() {
        return new String[]{identifier, itemMap.get(((RadioButton) this.findViewById(this.getCheckedRadioButtonId())).getText().toString())};
    }

    /**
     * Get currently selected segment
     *
     * @return value of currently selected segment
     */
    public String getChecked() {
        return itemMap.get(((RadioButton) this.findViewById(this.getCheckedRadioButtonId())).getText().toString());
    }


    /**
     * Interface for for the selection change event
     */
    public interface OnSelectionChangedListener {
        void newSelection(String identifier, String value);
    }

    /**
     * Sets the items and vaules for each segements.
     *
     * @param itemArray
     * @param valueArray
     * @throws Exception
     */
    public TypeSwitcher setItems(String[] itemArray, String[] valueArray) {
        itemMap.clear();
        if (itemArray != null && valueArray != null) {
            if (itemArray.length != valueArray.length) {
                Log.e("SegmentedControlView:", "Item labels and value arrays must be the same size");
            }
        }
        if (itemArray != null) {
            if (valueArray != null) {
                for (int i = 0; i < itemArray.length; i++) {
                    itemMap.put(itemArray[i], valueArray[i]);
                }
            } else {
                for (CharSequence item : itemArray) {
                    itemMap.put(item.toString(), item.toString());
                }
            }
        }

        return this;
    }

    /**
     * Sets the items and vaules for each segements. Also provides a helper to setting the
     * default selection
     *
     * @param items
     * @param values
     * @param defaultSelection
     * @throws Exception
     */
    public void setItems(String[] items, String[] values, int defaultSelection) throws Exception {

        if (defaultSelection > (items.length - 1)) {
            throw new Exception("Default selection cannot be greater than the number of items");
        } else {
            this.defaultSelection = defaultSelection;
            setItems(items, values);
        }
    }

    /**
     * Sets the item that is selected by default. Must be greater than -1.
     *
     * @param defaultSelection
     * @throws Exception
     */
    public TypeSwitcher setDefaultSelection(int defaultSelection) {
        if (defaultSelection > (itemMap.size() - 1)) {
            Log.e("SegmentedControlView:", "Default selection cannot be greater than the number of items");
        } else {
            this.defaultSelection = defaultSelection;
        }
        return this;
    }

    /**
     * Sets the colors used when drawing the view. The primary color will be used for selected color
     * and unselected text color, while the secondary color will be used for unselected color
     * and selected text color.
     *
     * @param primaryColor
     * @param secondaryColor
     */
    public TypeSwitcher setColors(int primaryColor, int secondaryColor) {
        this.selectedColor = primaryColor;
        this.selectedTextColor = secondaryColor;
        this.unselectedColor = secondaryColor;
        this.unselectedTextColor = primaryColor;

        //Set text selectedColor state list
        textColorStateList = new ColorStateList(new int[][]{
                {-android.R.attr.state_checked}, {android.R.attr.state_checked}},
                new int[]{unselectedTextColor, selectedTextColor}
        );
        return this;
    }

    /**
     * Sets the colors used when drawing the view
     *
     * @param selectedColor
     * @param selectedTextColor
     * @param unselectedColor
     * @param unselectedTextColor
     */
    public void setColors(int selectedColor, int selectedTextColor, int unselectedColor, int unselectedTextColor) {
        this.selectedColor = selectedColor;
        this.selectedTextColor = selectedTextColor;
        this.unselectedColor = unselectedColor;
        this.unselectedTextColor = unselectedTextColor;

        //Set text selectedColor state list
        textColorStateList = new ColorStateList(new int[][]{
                {-android.R.attr.state_checked}, {android.R.attr.state_checked}},
                new int[]{unselectedTextColor, selectedTextColor}
        );

        update();
    }

    /**
     * Used to set the selected value based on the value (not the visible text) provided in the
     * value array provided via xml or code
     *
     * @param value
     */
    public void setByValue(String value) {
//        String buttonText = "";
//        if (this.itemMap.containsValue(value)) {
//            for (String entry : itemMap.keySet()) {
//                if (itemMap.get(entry).equalsIgnoreCase(value)) {
//                    buttonText = entry;
//                }
//            }
//        }
//
//        for (RadioButton option : options) {
//            if (option.getText().toString().equalsIgnoreCase(buttonText)) {
//                this.check(option.getId());
//            }
//        }

    }

    /**
     * Sets the mListener that gets called when a selection is changed
     *
     * @param listener
     */
    public void setOnSelectionChangedListener(OnSelectionChangedListener listener) {
        this.mListener = listener;
    }

    /**
     * For use with multiple views. This identifier will be provided in the
     * onSelectionChanged mListener
     *
     * @param identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Set to true if you want each segment to be equal width
     *
     * @param equalWidth
     */
    public TypeSwitcher setEqualWidth(boolean equalWidth) {
        this.equalWidth = equalWidth;
        return this;
    }

    /**
     * Set to true if the view should be stretched to fill it's parent view
     *
     * @param stretch
     */
    public TypeSwitcher setStretch(boolean stretch) {
        this.stretch = stretch;
//        update();
        return this;
    }

    private int dp2px(int value) {
        final float v = getContext().getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }
    private int sp2px(int value) {
        final float v = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (v * value + 0.5f);
    }


}
