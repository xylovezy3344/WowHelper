package com.xiaoyu.wowhelpers.utils;

import android.content.Context;

import com.xiaoyu.wowhelpers.MyApplication;

/**
 * 图片工具
 * Created by xiaoyu on 17-1-9.
 */

public class ImageUtil {

    /**
     * 根据数据库id组装成文件名，再用文件名拿到资源id
     */
    public static int mountIdToResourceId(Long mountId) {

        String imageName = null;

        if (mountId > 99) {
            imageName = "mount_" + mountId;
        } else if (mountId > 9) {
            imageName = "mount_0" + mountId;
        } else {
            imageName = "mount_00" + mountId;
        }

        Context context = MyApplication.getInstance().getApplicationContext();
        return context.getResources().getIdentifier(imageName, "drawable",
                "com.xiaoyu.wowhelpers");
    }
}
