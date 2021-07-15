package io.github.vinayalodha.elvis.annotation;

import java.lang.annotation.*;


/**
 * Marking this annotation on local variable will prevent {@link NullPointerException}
 * <br/>
 * For example, consider below snippet
 * <br/><br/>
 * <pre>{@code
 * @NullSafe("Hello World")
 * String hello = getNullString().trim();
 * System.out.println(hello);
 * }</pre>
 * <br/>
 * This expression will print "Hello World".
 * <p>
 * If you open class file you can see modified AST by Elvis plugin as below
 *
 * <pre>{@code
 * String hello = Optional.ofNullable(getNullString())
 *                  .map(it->it.trim())
 *                  .orElse("Hello World");
 * System.out.println(hello);
 * }</pre>
 *
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.LOCAL_VARIABLE})
public @interface NullSafe {


    /**
     * Default value to use if rhs of expression is evaluated to null.
     * <br/><br/>
     * For example, below snippet will print "Hello World"
     * <pre>{@code
     *      @NullSafe("Hello World")
     *      String hello = getNullString().trim();
     *      System.out.println(hello);
     * }</pre>
     * <br/>
     * If not specified, then Object will be defaulted to null and primitive types will be defaulted to java defaults
     */
    String value() default "";
}
