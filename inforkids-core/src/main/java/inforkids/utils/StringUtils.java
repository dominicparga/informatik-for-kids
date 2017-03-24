package inforkids.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static String readFile(File file, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(file.toPath());
        return new String(encoded, encoding);
    }

    public static String readFile(File file) throws IOException {
        return readFile(file, Charset.defaultCharset());
    }
}
