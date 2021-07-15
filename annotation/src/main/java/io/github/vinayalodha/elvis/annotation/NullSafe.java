package io.github.vinayalodha.elvis.annotation;

import java.lang.annotation.*;


/**
 * Marking this annotation on local variable will prevent {@link NullPointerException}
 * For example, consider below snippet
 *
 * @NullSafe("Hello World")
 * String hello = getNullString().trim();
 * System.out.println(hello);
 *
 * This expression will print "Hello World".

 * If you open class file you can see modified AST by Elvis plugin as below
 *
 * String hello = Optional.ofNullable(getNullString())
 *                  .map(it->it.trim())
 *                  .orElse("Hello World");
 * System.out.println(hello);
 *
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.LOCAL_VARIABLE})
public @interface NullSafe {


    /**
     * Default value to use if rhs of expression is evaluated to null.
     * For example, below snippet will print "Hello World"
     *
     * @NullSafe("Hello World")
     * String hello = getNullString().trim();
     * System.out.println(hello);
     * <p>
     * If not specified, then Object will be defaulted to null and primitive types will be defaulted to java defaults
     */
    String value() default "";
}
