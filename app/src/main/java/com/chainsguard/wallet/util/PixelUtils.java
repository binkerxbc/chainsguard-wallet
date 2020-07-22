package com.chainsguard.wallet.util;

import android.content.res.Resources;

/**
 * @author i11m20n
 */
public final class PixelUtils {

    /**
     * 从 dp 到 px 的转换
     *
     * @param dpValue dp 值
     * @return px 值
     */
    public static int dp2px(float dpValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
