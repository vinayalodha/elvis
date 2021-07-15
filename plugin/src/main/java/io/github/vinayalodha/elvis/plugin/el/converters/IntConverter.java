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
public class IntConverter extends AbstractExpressionConverter {

    @Override
    public JCTree.JCExpression doConvert(String annotationValue, Type typeToUse, TreeMaker treeMaker, JavacTypes javacTypes) {
        int val = 0;
        if (StringUtils.isNotBlank(annotationValue)) {
            val = Integer.parseInt(annotationValue);
        }
        return setType(javacTypes, treeMaker.Literal(TypeTag.INT, val), TypeKind.INT);
    }


    @Override
    public boolean canConvert(String annotationValue, Type typeToUse) {
        return typeOfType(typeToUse, "int", Integer.class.getName());
    }
}
