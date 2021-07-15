package io.github.vinayalodha.elvis.plugin.ast.vistors.analyze;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.JavacTask;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.model.JavacTypes;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import io.github.vinayalodha.elvis.plugin.ast.vistors.AbstractTreeVisitor;
import io.github.vinayalodha.elvis.plugin.constants.ErrorMessage;
import io.github.vinayalodha.elvis.plugin.el.ExpressionParser;
import io.github.vinayalodha.elvis.plugin.utils.StringUtils;
import io.github.vinayalodha.elvis.plugin.utils.TreeTypeUtils;
import io.github.vinayalodha.elvis.plugin.utils.TreeUtils;

import javax.tools.Diagnostic;
import java.util.Optional;

/**
 * Populate correct value in orElse block
 *
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class VariableTreeCorrector extends AbstractTreeVisitor<JCTree.JCVariableDecl> {
    private final JavacTypes javacTypes;

    public VariableTreeCorrector(JavacTask javacTask, CompilationUnitTree compilationUnitTree) {
        super(javacTask, compilationUnitTree);
        this.javacTypes = JavacTypes.instance(context);
    }

    public void handle(JCTree.JCVariableDecl tree) {
        Optional<JCTree.JCAnnotation> notNullAnnotation = TreeUtils.findNotNullAnnotation(TreeUtils.getAnnotations(tree), imports);
        if (notNullAnnotation.isEmpty() || tree.init.getKind() != Tree.Kind.METHOD_INVOCATION)
            return;

        JCTree.JCMethodInvocation initializer = (JCTree.JCMethodInvocation) tree.init;

        if (initializer.getArguments().size() != 1)
            return;

        JCTree.JCExpression firstArgumentOfInitializer = initializer.getArguments().get(0);
        if (firstArgumentOfInitializer.getKind() == Tree.Kind.METHOD_INVOCATION
                && "io.github.vinayalodha.elvis.plugin.utils.ElvisProxy.dummyCall".equals(getMethodName(firstArgumentOfInitializer))) {

            JCTree.JCLiteral notNullAnnotationStringLiteralValue = TreeUtils.extractNotNullAnnotationValue(notNullAnnotation.get());
            JCTree.JCMethodInvocation dummyMethod = (JCTree.JCMethodInvocation) firstArgumentOfInitializer;
            List<JCTree.JCExpression> dummyMethodArguments = dummyMethod.getArguments();
            Type typeToUse;
            Type rhsType = dummyMethodArguments.get(0).type;
            Type lhsType = tree.type;

            if (rhsType.isPrimitive())
                typeToUse = rhsType;
            else if (lhsType.isPrimitive())
                typeToUse = lhsType;
            else
                typeToUse = lhsType;

            correctTreeMakerPos(initializer);
            String notNullLiteralStringValue = convertToString(notNullAnnotationStringLiteralValue);
            JCTree.JCExpression finalExpressionToUseInNotNullOrElseBlock = ExpressionParser.elToJCExpression(
                    notNullLiteralStringValue, typeToUse, treeMaker, javacTypes);

            if (finalExpressionToUseInNotNullOrElseBlock == null) {
                trees.printMessage(Diagnostic.Kind.ERROR,
                        ErrorMessage.NO_EL_CONVERTER,
                        notNullAnnotation.get(),
                        compilationUnitTree);
            } else {
                initializer.args = List.of(finalExpressionToUseInNotNullOrElseBlock);
            }
        }
    }

    public String getMethodName(JCTree.JCExpression expression) {
        if (expression.getKind() != Tree.Kind.METHOD_INVOCATION)
            return "";
        return ((JCTree.JCMethodInvocation) expression).meth.toString();
    }

    private void correctTreeMakerPos(JCTree.JCExpression initializer) {
        if (initializer != null)
            treeMaker.at(initializer.pos());
    }

    private String convertToString(JCTree.JCLiteral annotationValueLiteral) {
        if (annotationValueLiteral == null || annotationValueLiteral.value == null || TreeTypeUtils.isNullLiteral(annotationValueLiteral))
            return null;
        if (StringUtils.isEmpty(annotationValueLiteral.value.toString()))
            return "";
        return annotationValueLiteral.value.toString();
    }
}
