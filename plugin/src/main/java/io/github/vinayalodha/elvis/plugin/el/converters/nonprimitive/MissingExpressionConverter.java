package io.github.vinayalodha.elvis.plugin.el.converters.nonprimitive;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.model.JavacTypes;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import io.github.vinayalodha.elvis.plugin.el.converters.AbstractExpressionConverter;
import io.github.vinayalodha.elvis.plugin.utils.StringUtils;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class MissingExpressionConverter extends AbstractExpressionConverter {
    @Override
    public JCTree.JCExpression doConvert(String annotationValue, Type typeToUse, TreeMaker treeMaker, JavacTypes javacTypes) {
        return nullLiteral(treeMaker, javacTypes);
    }

    @Override
    public boolean canConvert(String annotationValue, Type inferredType) {
        if (StringUtils.isNotBlank(annotationValue))
            return false;

        if (inferredType.isPrimitive())
            return false;

        if (annotationValue == null)
            return true;

        return !annotationValue.isBlank() || !inferredType.toString().equals(String.class.getName());
    }
}
