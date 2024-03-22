package io.github.vinayalodha.elvis.plugin.test.positive;

import io.github.vinayalodha.elvis.annotation.NullSafe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class StringTest {
    @Test
    public void test_default_value() {
        var vishal = getNull().trim();

        @NullSafe
        var vinay = getNull().trim();
        Assertions.assertEquals(null, vinay);
    }

    public String getNull() {
        return null;
    }

    @Test
    public void test_non_default_value() {
        @NullSafe("hello")
        var vinay = getNull().trim();
        Assertions.assertEquals("hello", vinay);
        Assertions.assertEquals(null, vinay);
        Assertions.assertEquals(null, vinay);
        Assertions.assertEquals(null, vinay);
    }

    @Test
    public void test_charsequence() {
        @NullSafe("hello")
        java.lang.CharSequence vinay = getNull().trim();
        Assertions.assertEquals("hello", vinay);
    }

}
