package io.github.vinayalodha.elvis.plugin.el.converters.primitive;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.model.JavacTypes;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import io.github.vinayalodha.elvis.plugin.el.converters.AbstractExpressionConverter;
import io.github.vinayalodha.elvis.plugin.utils.StringUtils;

import javax.lang.model.type.TypeKind;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class BoolConverter extends AbstractExpressionConverter {

    @Override
    public JCTree.JCExpression doConvert(String annotationValue, Type typeToUse, TreeMaker treeMaker, JavacTypes javacTypes) {
        boolean val = false;
        if (StringUtils.isNotBlank(annotationValue)) {
            val = Boolean.parseBoolean(annotationValue);
        }
        return setType(javacTypes, treeMaker.Literal(val), TypeKind.BOOLEAN);
    }


    @Override
    public boolean canConvert(String annotationValue, Type typeToUse) {
        return typeOfType(typeToUse, "boolean", Boolean.class.getName());
    }
}
