package com.example.administrator.test.bean;

import android.view.View;

/**
 * @Author: JINO
 * @Description: 投稿弹窗Item
 * @Date: Create in 11:40 2019/9/26
 */
public class ContributeItemData {

    public String iconRes;
    public String title;
    public View.OnClickListener onClickListener;

    public ContributeItemData(String iconRes, String title, View.OnClickListener onClickListener) {
        this.iconRes = iconRes;
        this.title = title;
        this.onClickListener = onClickListener;
    }

}
