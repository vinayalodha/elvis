package io.github.vinayalodha.elvis.plugin.utils;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class StringUtils {
    public static boolean isNotBlank(String a) {
        return !isBlank(a);
    }

    public static boolean isBlank(String a) {
        if (a == null)
            return true;

        return a.trim().equals("");
    }

    public static boolean isEmpty(String a) {
        if (a == null)
            return true;

        return a.equals("");
    }
}
