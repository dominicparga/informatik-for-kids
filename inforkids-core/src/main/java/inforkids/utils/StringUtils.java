package inforkids.utils;

/**
 * @author Dominic Parga Cacheiro
 */
public class StringUtils {

    /**
     * <p>
     * Converts the given {@code number} to a {@code String} with an empty prefix to have the specified number of {@code
     * digits}.
     *
     * <p>
     * Example: <br>
     * {@code number = 4, digits = 2} returns {@code " 4"} <br>
     * {@code number = 2, digits = 3} returns {@code "  2"} <br>
     * {@code number = 42, digits = 3} returns {@code " 42"} <br>
     * {@code number = 42, digits = 1} returns {@code "2"} <br>
     *
     * @param number This number should be returned as {@code String}
     * @param digits The {@code String} will contain this number of chars.
     * @return A {@code String} having the specified number of {@code digits} and representing the given number.
     */
    public static String toString(int number, int digits) {

        StringBuilder builder = new StringBuilder();
        builder.append(number);
        if (builder.length() > digits)
            builder.replace(0, builder.length() - digits, "");


        /* add prefix */
        StringBuilder prefixBuilder = new StringBuilder();
        for (int i = builder.length(); i < digits; i++)
            prefixBuilder.append(" ");

        return prefixBuilder.append(builder).toString();
    }
}
