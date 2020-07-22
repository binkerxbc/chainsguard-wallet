package com.chainsguard.wallet.util;

import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * @author i11m20n
 */
public final class AnimationUtil {

    public static Animation shakeAnimation() {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(5));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }
}
