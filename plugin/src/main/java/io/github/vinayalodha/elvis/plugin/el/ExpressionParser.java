package io.github.vinayalodha.elvis.plugin.el;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.model.JavacTypes;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import io.github.vinayalodha.elvis.plugin.el.converters.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public final class ExpressionParser {

    private static final List<ExpressionConverter> CONVERTERS = buildAllConverters();

    private static List<ExpressionConverter> buildAllConverters() {
        List<ExpressionConverter> retVal = new ArrayList<>();
        retVal.add(new MissingExpressionConverter());

        retVal.add(new BoolConverter());
        retVal.add(new ByteConverter());
        retVal.add(new CharConverter());
        retVal.add(new CharSequenceConverter());
        retVal.add(new DoubleConverter());
        retVal.add(new FloatConverter());
        retVal.add(new IntConverter());
        retVal.add(new LongConverter());
        retVal.add(new ShortConverter());
        return retVal;
    }

    public static JCTree.JCExpression elToJCExpression(String annotationValue, Type typeToUse, TreeMaker treeMaker, JavacTypes javacTypes) {
        return CONVERTERS.stream()
                .map(obj -> {
                    if (obj.canConvert(annotationValue, typeToUse))
                        return obj.convert(annotationValue, typeToUse, treeMaker, javacTypes);
                    return null;
                })
                .filter(Objects::nonNull)
                .findAny().orElse(null);
    }
}
