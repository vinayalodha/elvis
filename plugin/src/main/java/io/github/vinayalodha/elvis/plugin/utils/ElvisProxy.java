package io.github.vinayalodha.elvis.plugin.utils;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
@SuppressWarnings("SameReturnValue")
public final class ElvisProxy {

    private ElvisProxy() {
    }

    public static <T> T dummyCall(Object originalExpression, T annotationValue) {
        return null;
    }
}
