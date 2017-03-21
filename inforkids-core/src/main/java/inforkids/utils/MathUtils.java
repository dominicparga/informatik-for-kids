package inforkids.utils;

/**
 * @author Dominic Parga Cacheiro
 */
public class MathUtils {

    public static int getDigitCountDecimal(int n) {

        /* overflow if n is negative */
        if (n == Integer.MIN_VALUE)
            return 10;


        /* make positive */
        if (n < 0)
            n *= -1;


        if (n < 100000)
            /* 1 <= digit count <= 5 */
            if (n < 100)
                /* 1 <= digit count <= 2 */
                return (n < 10) ? 1 : 2;
            else
                /* 3 <= digit count <= 5 */
                if (n < 1000)
                    return 3;
                else
                    /* 4 <= digit count <= 5 */
                    return (n < 10000) ? 4 : 5;
        else
            /* 6 <= digit count <= 10 */
            if (n < 10000000)
                /* 6 <= digit count <= 7 */
                return (n < 1000000) ? 6 : 7;
            else
                /* 8 <= digit count <= 10 */
                if (n < 100000000)
                    return 8;
                else
                    /* 9 <= digit count <= 10 */
                    return (n < 1000000000) ? 9 : 10;
    }
}
