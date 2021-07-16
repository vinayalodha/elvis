package io.github.vinayalodha.elvis.plugin.utils;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class StringUtils {
    public static boolean isNotBlank(String input) {
        return !isBlank(input);
    }

    public static boolean isBlank(String input) {
        if (input == null) return true;
        return input.trim().equals("");
    }

    public static boolean isEmpty(String input) {
        if (input == null) return true;
        return input.equals("");
    }
}
