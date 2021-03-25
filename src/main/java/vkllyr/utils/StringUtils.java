package vkllyr.utils;

public class StringUtils {
    public static final String Empty = "";

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && str.length() != 0;
    }
}
