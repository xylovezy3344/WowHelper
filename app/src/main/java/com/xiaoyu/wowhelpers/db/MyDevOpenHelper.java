package com.xiaoyu.wowhelpers.db;

import android.content.Context;

import com.xiaoyu.wowhelpers.db.DaoMaster;

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
