package io.github.vinayalodha.elvis.plugin.el;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.model.JavacTypes;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;


/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public interface ExpressionConverter {
    JCTree.JCExpression convert(String annotationValue, Type typeToUse, TreeMaker treeMaker, JavacTypes javacTypes);

    boolean canConvert(String annotationValue, Type typeToUse);
}
