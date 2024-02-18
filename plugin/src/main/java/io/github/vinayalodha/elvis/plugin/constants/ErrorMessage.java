package io.github.vinayalodha.elvis.plugin.constants;


import io.github.vinayalodha.elvis.annotation.NullSafe;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public interface ErrorMessage {

    String NULL_SAFE_CODE = String.format(" is already null safe, Are you sure you need of %s annotation", NullSafe.class.getSimpleName());
    String PARSING_BUG = String.format("Unable to parse expression, for applying @%s. please raise a bug", NullSafe.class.getSimpleName());
    String NO_EL_CONVERTER = String.format("Unable to parse value parameter passed in %s", NullSafe.class.getSimpleName());
    String BINARY_EXPRESSION = String.format("Ambiguous expression, @%s can not be applied", NullSafe.class.getSimpleName());
}
