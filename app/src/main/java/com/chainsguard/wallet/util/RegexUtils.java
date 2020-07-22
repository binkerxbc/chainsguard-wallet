package com.chainsguard.wallet.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * @author i11m20n
 */
public final class RegexUtils {

    /**
     * (?=.*[0-9]): 一个数字必须至少出现一次
     * (?=.*[a-z]): 小写字母必须至少出现一次
     * (?=.*[A-Z]): 大写字母必须至少出现一次
     * (?=\S+$): 整个字符串中不允许有空格
     * .{10,}: 至少 10 个字符
     */
    private static final String REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{10,}$";

    public static boolean isMatchPassword(CharSequence input) {
        return isMatch(REGEX_PASSWORD, input);
    }

    public static boolean isMatch(String regex, CharSequence input) {
        return !TextUtils.isEmpty(input) && Pattern.matches(regex, input);
    }
}
