package org.nil;

import java.util.Objects;

public class StringJoin {

    public interface UrlValue {
        String toUrlValue();
    }

    private StringJoin() {}

    public static String join(char delim, String... parts) {
        return join(new String(new char[] {delim}), parts);
    }

    public static String join(CharSequence delim, String... parts) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i != 0) {
                result.append(delim);
            }
            result.append(parts[i]);
        }
        return result.toString();
    }

    public static String join(char delim, Object... parts) {
        return join(new String(new char[] {delim}), parts);
    }

    public static String join(CharSequence delim, Object... parts) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i != 0) {
                result.append(delim);
            }
            result.append(Objects.toString(parts[i]));
        }
        return result.toString();
    }

    public static String join(char delim, UrlValue... parts) {
        String[] strings = new String[parts.length];
        int i = 0;
        for (UrlValue part : parts) {
            strings[i++] = part.toUrlValue();
        }

        return join(delim, strings);
    }

}
