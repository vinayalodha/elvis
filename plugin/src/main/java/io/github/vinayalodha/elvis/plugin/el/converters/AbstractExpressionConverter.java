package io.github.vinayalodha.elvis.plugin.el.converters;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.model.JavacTypes;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import io.github.vinayalodha.elvis.plugin.el.ExpressionConverter;

import javax.lang.model.type.TypeKind;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public abstract class AbstractExpressionConverter implements ExpressionConverter {
    @Override
    public final JCTree.JCExpression convert(String annotationValue, Type typeToUse, TreeMaker treeMaker, JavacTypes javacTypes) {
        try {
            return doConvert(annotationValue, typeToUse, treeMaker, javacTypes);
        } catch (Exception e) {
            return null;
        }
    }

    protected abstract JCTree.JCExpression doConvert(String annotationValue, Type typeToUse, TreeMaker treeMaker, JavacTypes javacTypes);

    protected JCTree.JCExpression nullLiteral(TreeMaker treeMaker, JavacTypes javacTypes) {
        JCTree.JCLiteral literal = treeMaker.Literal(TypeTag.BOT, null);
        literal.setType((Type) javacTypes.getNullType());
        return literal;
    }

    protected boolean typeOfType(Type type, String... supportedTypes) {
        String givenType = type.toString();
        for (String supportedType : supportedTypes) {
            if (supportedType.equals(givenType)) return true;
        }
        return false;
    }

    protected JCTree.JCLiteral setType(JavacTypes javacTypes, JCTree.JCLiteral jcLiteral, TypeKind typeKind) {
        return jcLiteral.setType((Type) javacTypes.getPrimitiveType(typeKind));
    }

}
