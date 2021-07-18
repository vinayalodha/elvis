package io.github.vinayalodha.elvis.annotation;

import java.lang.annotation.*;


/**
 * <pre>
 * Marking this annotation on local variable will prevent {@link NullPointerException}
 *
 * For example, consider below snippet
 *
 * {@literal @}NullSafe("Hello World")
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
 *
 * Current Limitations
 * 1. If you have class symbol in Expression you will get compilation error;
 * 2. If you have checked exception in expression you will get compilation error
 * 3. Annotation can only applied to local variable due to @Target({ElementType.LOCAL_VARIABLE}) policy
 * 4. Eclipse IDE wont work with this plugin as Eclipse have its own compiler for doing stuff.
 *
 * More details at <a href="https://github.com/vinayalodha/elvis">github docs</a>
 * </pre>
 *
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.LOCAL_VARIABLE})
public @interface NullSafe {


    /**
     * <pre>
     * Default value to use if rhs of expression is evaluated to null.
     * For example, below snippet will print "Hello World"
     *
     * {@literal @}NullSafe("Hello World")
     * String hello = getNullString().trim();
     * System.out.println(hello);
     *
     * If not specified, then Object will be defaulted to null and primitive types will be defaulted to java defaults
     * </pre>
     */
    String value() default "";
}
