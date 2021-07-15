package io.github.vinayalodha.elvis.plugin.ast.vistors.parse;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import com.sun.tools.javac.tree.JCTree;
import io.github.vinayalodha.elvis.plugin.ast.vistors.AbstractTreeVisitor;
import io.github.vinayalodha.elvis.plugin.constants.ErrorMessage;
import io.github.vinayalodha.elvis.plugin.utils.SymbolStackUtils;
import io.github.vinayalodha.elvis.plugin.utils.TreeTypeUtils;
import io.github.vinayalodha.elvis.plugin.utils.TreeUtils;

import javax.tools.Diagnostic;
import java.util.Deque;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class VariableTreeVisitor extends AbstractTreeVisitor<JCTree.JCVariableDecl> {

    public VariableTreeVisitor(JavacTask javacTask, CompilationUnitTree compilationUnitTree) {
        super(javacTask, compilationUnitTree);
    }

    public void handle(JCTree.JCVariableDecl variableTree) {
        if (TreeUtils.findNotNullAnnotation(TreeUtils.getAnnotations(variableTree), imports).isEmpty())
            return;
        JCTree.JCExpression initializer = variableTree.getInitializer();
        correctTreeMakerPos(initializer);

        JCTree.JCMethodInvocation transformedTree = null;
        if (shouldDoStackProcessing(initializer)) {
            transformedTree = transform(initializer);
        } else if (TreeTypeUtils.isLiteralTree(initializer)) {
            trees.printMessage(Diagnostic.Kind.ERROR,
                    ErrorMessage.NULL_SAFE_CODE,
                    initializer,
                    compilationUnitTree);
        } else if (TreeTypeUtils.isBinaryTree(variableTree.getInitializer())) {
            trees.printMessage(Diagnostic.Kind.ERROR,
                    ErrorMessage.BINARY_EXPRESSION,
                    initializer,
                    compilationUnitTree);
        } else {
            trees.printMessage(Diagnostic.Kind.ERROR,
                    ErrorMessage.PARSING_BUG,
                    initializer,
                    compilationUnitTree);
        }
        if (transformedTree != null) {
            variableTree.init = transformedTree;
        }
    }

    private void correctTreeMakerPos(JCTree.JCExpression initializer) {
        if (initializer != null)
            treeMaker.at(initializer.pos());
    }

    private boolean shouldDoStackProcessing(JCTree.JCExpression initializer) {
        return TreeTypeUtils.isMethodInvocationTree(initializer)
                || TreeTypeUtils.isArrayAccessTree(initializer)
                || TreeTypeUtils.isFieldAccessTree(initializer)
                || TreeTypeUtils.isIdentifierTree(initializer);
    }

    public JCTree.JCMethodInvocation transform(JCTree.JCExpression ast) {

        Deque<JCTree> expressionStack = SymbolStackUtils.buildSymbolStack(ast);
        if (expressionStack == null) {
            trees.printMessage(Diagnostic.Kind.ERROR,
                    ErrorMessage.PARSING_BUG,
                    ast,
                    compilationUnitTree);
            return null;
        }
        JCTree.JCMethodInvocation jcMethodInvocation = SymbolStackUtils.processSymbolStack(treeMakerExtension, expressionStack);
        return treeMakerExtension.buildOrElse(jcMethodInvocation, ast);
    }

}
