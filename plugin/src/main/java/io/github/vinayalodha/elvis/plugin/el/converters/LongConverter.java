package io.github.vinayalodha.elvis.plugin.el.converters;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.model.JavacTypes;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import io.github.vinayalodha.elvis.plugin.utils.StringUtils;

import javax.lang.model.type.TypeKind;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class LongConverter extends AbstractExpressionConverter {
    long obj;

    @Override
    public JCTree.JCExpression doConvert(String annotationValue, Type shouldUsePrimitiveType, TreeMaker treeMaker, JavacTypes javacTypes) {
        long val = obj;
        if (StringUtils.isNotBlank(annotationValue)) {
            val = Long.parseLong(annotationValue);
        }
        return setType(javacTypes, treeMaker.Literal(TypeTag.LONG, val), TypeKind.LONG);
    }

    @Override
    public boolean canConvert(String annotationValue, Type typeToUse) {
        return typeOfType(typeToUse, "long", Long.class.getName());
    }
}
