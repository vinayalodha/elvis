package io.github.vinayalodha.elvis.plugin.el.converters.primitive;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.model.JavacTypes;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import io.github.vinayalodha.elvis.plugin.el.converters.AbstractExpressionConverter;
import io.github.vinayalodha.elvis.plugin.utils.StringUtils;

import javax.lang.model.type.TypeKind;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class FloatConverter extends AbstractExpressionConverter {
    float obj;

    @Override
    public JCTree.JCExpression doConvert(String annotationValue, Type shouldUsePrimitiveType, TreeMaker treeMaker, JavacTypes javacTypes) {
        float val = obj;
        if (StringUtils.isNotBlank(annotationValue)) {
            val = Float.valueOf(annotationValue);
        }
        return setType(javacTypes, treeMaker.Literal(TypeTag.FLOAT, val), TypeKind.FLOAT);
    }

    @Override
    public boolean canConvert(String annotationValue, Type typeToUse) {
        return typeOfType(typeToUse, "float", Float.class.getName());
    }
}
