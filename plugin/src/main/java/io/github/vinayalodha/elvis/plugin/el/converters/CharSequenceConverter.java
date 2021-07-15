package io.github.vinayalodha.elvis.plugin.el.converters;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.model.JavacTypes;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class CharSequenceConverter extends AbstractExpressionConverter {


    @Override
    public JCTree.JCExpression doConvert(String annotationValue, Type typeToUse, TreeMaker treeMaker, JavacTypes javacTypes) {
        JCTree.JCLiteral retVal = treeMaker.Literal(annotationValue);
        retVal.setType(typeToUse);
        return retVal;
    }

    @Override
    public boolean canConvert(String annotationValue, Type typeToUse) {
        return typeOfType(typeToUse, "String", String.class.getName(), CharSequence.class.getName(), "CharSequence");
    }
}
