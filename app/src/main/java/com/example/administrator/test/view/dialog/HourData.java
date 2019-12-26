package com.example.administrator.test.view.dialog;

import com.example.administrator.test.view.pickerview.PickerDataSet;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 17:40 2019/12/9
 */
public class HourData implements PickerDataSet {

    private int hour;

    public HourData(int hour) {
        this.hour = hour;
    }


    @Override
    public CharSequence getCharSequence() {
        return hour + "";
    }

    @Override
    public String getValue() {
        return hour + "";
    }
}
