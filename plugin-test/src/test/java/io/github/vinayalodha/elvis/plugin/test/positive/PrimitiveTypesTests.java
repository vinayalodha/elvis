package io.github.vinayalodha.elvis.plugin.test.positive;

import io.github.vinayalodha.elvis.annotation.NullSafe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PrimitiveTypesTests {

    private static final PrimitiveDataProvider PRIMITIVE_DATA_PROVIDER = null;
    private static final NonPrimitiveDataProvider NON_PRIMITIVE_DATA_PROVIDER = null;

    private <T> T getNull(Class<T> clazz) {
        return null;
    }

    public static class PrimitiveDataProvider {
        int intField = 0;
        boolean booleanField = false;
        byte byteField = 0;
        char charField = '\u0000';
        long longField = 0;
    }

    public static class NonPrimitiveDataProvider {
        Integer integerField;
        Boolean booleanField;
        Byte byteField;
        Character charField;
        Long longField;
    }

    @Nested
    public class IntTest {
        @Test
        public void primitive_type_default_value_int_1() {
            @NullSafe("")
            int vinay = getNull(Integer.class);
            Assertions.assertEquals(0, vinay);
        }


        @Test
        public void primitive_type_default_value_int_2() {
            @NullSafe
            int vinay = PRIMITIVE_DATA_PROVIDER.intField;
            Assertions.assertEquals(0, vinay);
        }

        @Test
        public void non_primitive_type_default_value_1() {
            @NullSafe("")
            var vinay = getNull(Integer.class);
            Assertions.assertEquals(null, vinay);
        }

        @Test
        public void non_primitive_type_default_value_2() {
            @NullSafe
            Integer vinay = NON_PRIMITIVE_DATA_PROVIDER.integerField;
            Assertions.assertEquals(null, vinay);
        }

        @Test
        public void primitive_type_non_default_value() {
            @NullSafe("1")
            int vinay = getNull(Integer.class);
            Assertions.assertEquals(1, vinay);
        }

        @Test
        public void non_primitive_type_non_default_value() {
            @NullSafe("1")
            Integer vinay = getNull(Integer.class);
            Assertions.assertEquals(1, vinay);
        }
    }

    @Nested
    public class ByteTest {
        @Test
        public void primitive_type_default_value_int_1() {
            @NullSafe("")
            byte vinay = getNull(Byte.class);
            Assertions.assertEquals(0, vinay);
        }


        @Test
        public void primitive_type_default_value_int_2() {
            @NullSafe
            byte vinay = PRIMITIVE_DATA_PROVIDER.byteField;
            Assertions.assertEquals(0, vinay);
        }

        @Test
        public void non_primitive_type_default_value_1() {
            @NullSafe("")
            var vinay = getNull(Byte.class);
            Assertions.assertEquals(null, vinay);
        }

        @Test
        public void non_primitive_type_default_value_2() {
            @NullSafe
            Byte vinay = NON_PRIMITIVE_DATA_PROVIDER.byteField;
            Assertions.assertEquals(null, vinay);
        }

        @Test
        public void primitive_type_non_default_value() {
            @NullSafe("100")
            byte vinay = getNull(Byte.class);
            Assertions.assertEquals(100, vinay);
        }

        @Test
        public void non_primitive_type_non_default_value() {
            @NullSafe("100")
            Byte vinay = getNull(Byte.class);
            Assertions.assertEquals(true, 100 == vinay);
        }
    }

    @Nested
    public class BooleanTest {
        @Test
        public void primitive_type_default_value_int_1() {
            @NullSafe("")
            boolean vinay = getNull(Boolean.class);
            Assertions.assertEquals(false, vinay);
        }


        @Test
        public void primitive_type_default_value_int_2() {
            @NullSafe
            boolean vinay = PRIMITIVE_DATA_PROVIDER.booleanField;
            Assertions.assertEquals(false, vinay);
        }

        @Test
        public void non_primitive_type_default_value_1() {
            @NullSafe("")
            var vinay = getNull(Boolean.class);
            Assertions.assertEquals(null, vinay);
        }

        @Test
        public void non_primitive_type_default_value_2() {
            @NullSafe
            Boolean vinay = NON_PRIMITIVE_DATA_PROVIDER.booleanField;
            Assertions.assertEquals(null, vinay);
        }

        @Test
        public void primitive_type_non_default_value() {
            @NullSafe("true")
            boolean vinay = getNull(Boolean.class);
            Assertions.assertEquals(true, vinay);
        }

        @Test
        public void non_primitive_type_non_default_value() {
            @NullSafe("true")
            Boolean vinay = getNull(Boolean.class);
            Assertions.assertEquals(true, vinay);
        }
    }

    @Nested
    public class CharTest {
        @Test
        public void primitive_type_default_value_int_1() {
            @NullSafe("")
            char vinay = getNull(Character.class);
            Assertions.assertEquals('\u0000', vinay);
        }


        @Test
        public void primitive_type_default_value_int_2() {
            @NullSafe
            char vinay = PRIMITIVE_DATA_PROVIDER.charField;
            Assertions.assertEquals('\u0000', vinay);
        }

        @Test
        public void non_primitive_type_default_value_1() {
            @NullSafe("")
            var vinay = getNull(Character.class);
            Assertions.assertEquals(null, vinay);
        }

        @Test
        public void non_primitive_type_default_value_2() {
            @NullSafe
            Character vinay = NON_PRIMITIVE_DATA_PROVIDER.charField;
            Assertions.assertEquals(null, vinay);
        }

        @Test
        public void primitive_type_non_default_value() {
            @NullSafe("1")
            char vinay = getNull(Character.class);
            Assertions.assertEquals('1', vinay);
        }

        @Test
        public void non_primitive_type_non_default_value() {
            @NullSafe("1")
            Character vinay = getNull(Character.class);
            Assertions.assertEquals('1', vinay);
        }
    }

    @Nested
    public class LongTest {
        @Test
        public void primitive_type_default_value_int_1() {
            @NullSafe("")
            long vinay = getNull(Long.class);
            Assertions.assertEquals(0, vinay);
        }


        @Test
        public void primitive_type_default_value_int_2() {
            @NullSafe
            long vinay = PRIMITIVE_DATA_PROVIDER.longField;
            Assertions.assertEquals(0, vinay);
        }

        @Test
        public void non_primitive_type_default_value_1() {
            @NullSafe("")
            var vinay = getNull(Long.class);
            Assertions.assertEquals(null, vinay);
        }

        @Test
        public void non_primitive_type_default_value_2() {
            @NullSafe
            Long vinay = NON_PRIMITIVE_DATA_PROVIDER.longField;
            Assertions.assertEquals(null, vinay);
        }

        @Test
        public void primitive_type_non_default_value() {
            @NullSafe("1")
            long vinay = getNull(Long.class);
            Assertions.assertEquals(1l, vinay);
        }

        @Test
        public void non_primitive_type_non_default_value() {
            @NullSafe("1")
            Long vinay = getNull(Long.class);
            Assertions.assertEquals(1l, vinay);
        }
    }

}
