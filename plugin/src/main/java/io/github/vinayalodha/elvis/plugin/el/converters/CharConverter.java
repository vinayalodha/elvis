package io.github.vinayalodha.elvis.plugin.el.converters;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.model.JavacTypes;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import io.github.vinayalodha.elvis.plugin.utils.StringUtils;

import javax.lang.model.type.TypeKind;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class CharConverter extends AbstractExpressionConverter {
    char aChar;

    @Override
    public JCTree.JCExpression doConvert(String annotationValue, Type shouldUsePrimitiveType, TreeMaker treeMaker, JavacTypes javacTypes) {
        char val = aChar;
        if (annotationValue != null && annotationValue.length() > 1) {
            return null;
        }
        if (StringUtils.isNotBlank(annotationValue)) {
            val = annotationValue.charAt(0);
        }
        return setType(javacTypes, treeMaker.Literal(val), TypeKind.CHAR);
    }

    @Override
    public boolean canConvert(String annotationValue, Type typeToUse) {
        return typeOfType(typeToUse, "char", Character.class.getName());
    }
}
