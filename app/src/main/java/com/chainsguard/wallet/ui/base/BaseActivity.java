package com.chainsguard.wallet.ui.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chainsguard.wallet.R;
import com.chainsguard.wallet.util.SystemUIUtils;

/**
 * Created by l0neman on 2019/12/01.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected final Activity self = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setCustomDensity();
        setStatusBar(true);
    }

    protected void setStatusBar(@SuppressWarnings("SameParameterValue") boolean transparent) {
        setUiUnderTheStatusBar();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setStatusBarColor(0x32000000);
        } else {
            if (transparent) {
                setStatusBarColor(0x01000000);
            } else {
                setStatusBarColor(getColor(R.color.colorPrimary));
            }
        }

        setStatusBarWithTheme();
    }

    protected void setUiUnderTheStatusBar() {
        SystemUIUtils.setUiUnderTheStatusBar(this);
    }

    protected void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SystemUIUtils.setStatueBarColor(this, color);
        }
    }

    protected void setStatusBarWithTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SystemUIUtils.setLightStatusBar(this);
        }
    }

//    private static float sNonCompatDensity;
//    private static float sNonCompatScaledDensity;
//
//    public void setCustomDensity() {
//        final Application app = getApplication();
//        final DisplayMetrics displayMetrics = app.getResources().getDisplayMetrics();
//
//        if (sNonCompatDensity == 0) {
//            sNonCompatDensity = displayMetrics.density;
//            sNonCompatScaledDensity = displayMetrics.scaledDensity;
//            app.registerComponentCallbacks(new ComponentCallbacks() {
//                @Override
//                public void onConfigurationChanged(@NonNull Configuration newConfig) {
//                    if (newConfig.fontScale > 0) {
//                        sNonCompatScaledDensity = app.getResources().getDisplayMetrics().scaledDensity;
//                    }
//                }
//
//                @Override
//                public void onLowMemory() {
//                }
//            });
//
//
//        }
//
//        final float targetDensity = getTargetDensity();
//        final float targetScaledDensity = targetDensity * (sNonCompatScaledDensity / sNonCompatDensity);
//        final int targetDensityDpi = (int) (160 * targetDensity);
//
//        displayMetrics.density = targetDensity;
//        displayMetrics.scaledDensity = targetScaledDensity;
//        displayMetrics.densityDpi = targetDensityDpi;
//
//        final DisplayMetrics activityDisplayMetrics = getResources().getDisplayMetrics();
//
//        activityDisplayMetrics.density = targetDensity;
//        activityDisplayMetrics.scaledDensity = targetScaledDensity;
//        activityDisplayMetrics.densityDpi = targetDensityDpi;
//    }
//
//    private float getTargetDensity() {
//        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        return displayMetrics.widthPixels * 1F / displayMetrics.heightPixels > 9 / 16F ?
//                displayMetrics.heightPixels / 640F : displayMetrics.widthPixels / 360F;
//    }
}
