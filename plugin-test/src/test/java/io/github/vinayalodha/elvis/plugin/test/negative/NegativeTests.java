package io.github.vinayalodha.elvis.plugin.test.negative;

import java.util.Locale;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class NegativeTests {

    public void t1() {
//        @NullSafe
        String vinay = "Vishal";
    }

    public void t2() {
        String vinay = "Vishal";

        // @NullSafe
        String hello = vinay;
    }

    public void test() {
//        @NullSafe
        String vinay = getNull().toUpperCase(Locale.ROOT) + getNull().toUpperCase(Locale.ROOT) + getNull().toUpperCase(Locale.ROOT);
    }

    public static String getNull() {
        return null;
    }

    public void t3() {

        // @NullSafe
        int vinay = 0;
    }

    public void t4() {
        int vinay = 1;
        // @NullSafe
        int vishal = vinay;
    }

    public void t5() {
        // @NullSafe
        String vinay = null;
    }
}
