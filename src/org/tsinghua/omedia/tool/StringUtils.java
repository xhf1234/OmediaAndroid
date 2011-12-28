package org.tsinghua.omedia.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author xuhongfeng
 *
 */
public class StringUtils {

    private static final Pattern EMAIL_PATTERN = 
            Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*" +
                    "@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    private static final Pattern ALPHA_NUM_PATTERN = Pattern.compile("^[0-9a-zA-Z]+$");
    
    public static boolean isEmail(String s) {
        Matcher m = EMAIL_PATTERN.matcher(s);
        return m.matches();
    }
    
    public static boolean isAlphaOrNum(String s) {
        Matcher m = ALPHA_NUM_PATTERN.matcher(s);
        return m.matches();
    }
}
