package com.xiaoyu.wowhelpers.utils;

import android.app.ProgressDialog;

/**
 * 正在加载...
 * Created by xiaoy on 16/9/18.
 */
public class ProgressDialogUtils {

    private static ProgressDialog mDialog;

    public static void ShowProgressDialog() {

        mDialog = new ProgressDialog(ActivityUtils.getAppManager().currentActivity());
        mDialog.setMessage("正在加载...");
        mDialog.setIndeterminate(true);
        mDialog.setCancelable(false);

        mDialog.show();
    }

    public static void hideProgressDialog() {
        if (mDialog != null) {
            mDialog.hide();
        }
    }
}
