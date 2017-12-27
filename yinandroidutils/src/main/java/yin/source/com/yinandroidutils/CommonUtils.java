package yin.source.com.yinandroidutils;

import android.content.Context;

/**
 * Created by yin on 2017/12/27.
 */

public class CommonUtils {


    /**
     * dip转pix
     */
    public static int dip2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * pix转dip
     */
    public static int pix2dip(Context context, int pix) {
        final float densityDpi = context.getResources().getDisplayMetrics().density;
        return (int) (pix / densityDpi + 0.5f);
    }

}
