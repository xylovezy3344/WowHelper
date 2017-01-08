package com.xiaoyu.wowhelpers;

import android.content.Context;

import com.xiaoyu.wowhelpers.entity.DaoMaster;

import org.greenrobot.greendao.database.Database;

public class MyDevOpenHelper extends DaoMaster.DevOpenHelper {
    public MyDevOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onCreate(Database db) {
        DaoMaster.createAllTables(db, true);
    }
}
