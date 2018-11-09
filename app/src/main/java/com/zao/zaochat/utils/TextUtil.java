package com.zao.zaochat.utils;

import java.util.regex.Pattern;

public class TextUtil {
    /**
     * 包括空格判断
     * @param input
     * @return
     */
    public static boolean containSpace(CharSequence input){
        return Pattern.compile("\\s+").matcher(input).find();
    }
}