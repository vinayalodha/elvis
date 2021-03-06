package io.github.vinayalodha.elvis.plugin.test.positive;

import io.github.vinayalodha.elvis.annotation.NullSafe;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class AstTransformationTests {

    public static InnerClass NULL_INNER_CLASS = null;
    public String hello;

    @Test
    public void lot_of_parenthesis() {
        String nullString = null;
        @NullSafe()
        String vinay = (((nullString).toUpperCase(Locale.ENGLISH)).trim());
        assertNull(vinay);
    }


    @Test
    public void method_invocation_on_identifier() {
        String nullString = null;
        @NullSafe()
        String vinay = nullString.toUpperCase(Locale.ENGLISH).trim();
        assertNull(vinay);
    }


    @Test
    public void method_invocation_on_identifier_to_array() {
        String nullString = null;
        @NullSafe()
        char[] vinay = nullString.toUpperCase(Locale.ENGLISH).trim().toCharArray();
        assertNull(vinay);
    }

    @Test
    public void method_invocation_on_another_method() {
        @NullSafe
        String vinay = getNull().toUpperCase(Locale.ENGLISH);
        assertNull(vinay);
    }

    private String getNull() {
        return null;
    }

    @Test
    public void this_pointer_field_access() {
        String hello = "local";

        @NullSafe("world")
        String vinay = this.hello.trim();
        assertEquals("world", vinay);
    }

    @Test
    public void this_pointer_method_invocation() {

        @NullSafe("world")
        String vinay = this.getNull().trim();
        assertEquals("world", vinay);
    }

    @Test
    public void method_invocation_on_static_method_call() {
        @NullSafe
        String vinay = getNull().toUpperCase(Locale.ENGLISH);
        assertNull(vinay);
    }

    @Test
    public void null_array_access() {
        String[] nullArray = null;

        @NullSafe
        String vinay = nullArray[2];
        assertNull(vinay);
    }

    @Test
    public void field_access_only() {
        @NullSafe
        String vinay = NULL_INNER_CLASS.notNullField;
        assertNull(vinay);
    }

    @Test
    public void method_invocation_on_constructor() {
        @NullSafe
        String vinay = "Hello".toLowerCase();
        assertEquals("hello", vinay);
    }

    @Test
    public void type_cast() {
        @NullSafe("Vinay")
        String vinay = ((String) null).trim();
        assertEquals("Vinay", vinay);
    }

    @Test
    public void ternary_operator() {
        @NullSafe("default")
        String vinay = (false ? "Hello" : null);
        assertEquals("default", vinay);
    }

    @Test
    public void binary_operator_method_invocation() {
        String a = "a";
        @NullSafe("default")
        String vinay = (a + "b").trim();
        assertEquals("ab", vinay);
    }

    public static class InnerClass {
        public String notNullField = "";
    }
}
