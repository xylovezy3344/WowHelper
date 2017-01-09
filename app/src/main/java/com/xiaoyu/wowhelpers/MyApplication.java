package com.xiaoyu.wowhelpers;

import android.app.Application;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xiaoyu.wowhelpers.db.MyDevOpenHelper;
import com.xiaoyu.wowhelpers.db.DaoMaster;
import com.xiaoyu.wowhelpers.db.DaoSession;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    private static MyApplication instance;
    private DaoSession mDaoSession;
    private SQLiteDatabase mDb;
    private static Resources resource;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");

        instance = this;
        resource = instance.getResources();

        copyDB("wow.db");

        initGreenDao();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    private void initGreenDao() {
        MyDevOpenHelper helper = new MyDevOpenHelper(this, "wow.db");
        mDb = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(mDb);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }

    public static Resources resources() {
        return resource;
    }

    /**
     * //拷贝资产目录下的数据库文件到应用databases目录
     *
     * @param dbName 数据库文件名称
     */
    private void copyDB(final String dbName) {
        try {
            //创建databases文件夹
            String DATABASES_DIR = "/data/data/com.xiaoyu.wowhelpers/databases/";
            File fileDir = new File(DATABASES_DIR);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            //复制city.db
            File file = new File(fileDir, "wow.db");
            if (file.exists() && file.length() > 0) {
                Log.e("tag", "数据库是存在的。无需拷贝！");
                return;
            }
            InputStream is = getAssets().open(dbName);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            is.close();
            fos.close();
            Log.e("tag", "数据库拷贝完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
