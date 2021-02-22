

package com.isoftstone.swipelayout;


import ohos.agp.utils.Point;
import ohos.agp.window.service.Display;
import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;

import java.util.Optional;


/**
 * 设备工具箱，提供与设备硬件相关的工具方法
 *
 * @author lemon0101
 * @version 1.0
 * @since 2/7
 */
public class DeviceUtils {


    /**
     * 获取屏幕大小
     *
     * @param context context
     * @return Point
     */
    public static Point getScreenSize(Context context) {
        Optional<Display> display = DisplayManager.getInstance().getDefaultDisplay(context);
        Point pt = new Point();
        display.get().getSize(pt);
        return pt;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context context
     * @return int
     */
    public static int getscreenW(Context context) {
        return getScreenSize(context).getPointXToInt();
    }
    /**
     * 获取屏幕尺寸与vp换算
     */
//	@SuppressWarnings("deprecation")
//
//	public static Point getScreenSize(Context context){
//		Optional<Display> display = DisplayManager.getInstance().getDefaultDisplay(context);
//		Point pt = new Point();
//		display.get().getSize(pt);
//		return pt;
//	}
}