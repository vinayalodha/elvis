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
import java.util.Optional;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class VariableTreeVisitor extends AbstractTreeVisitor<JCTree.JCVariableDecl> {

    public VariableTreeVisitor(JavacTask javacTask, CompilationUnitTree compilationUnitTree) {
        super(javacTask, compilationUnitTree);
    }

    public void doHandle(JCTree.JCVariableDecl variableTree) {
        Optional<JCTree.JCAnnotation> notNullAnnotation = TreeUtils.findNotNullAnnotation(TreeUtils.getAnnotations(variableTree), imports);
        if (notNullAnnotation.isEmpty())
            return;

        JCTree.JCExpression initializer = TreeUtils.removeParenthesis(variableTree.getInitializer());
        correctTreeMakerPos(initializer);

        JCTree.JCMethodInvocation transformedTree = null;
        if (SymbolStackUtils.shouldDoStackProcessing(initializer)) {
            transformedTree = transform(initializer, notNullAnnotation.get());
        } else if (TreeTypeUtils.isLiteralTree(initializer)) {
            trees.printMessage(Diagnostic.Kind.ERROR,
                    ErrorMessage.NULL_SAFE_CODE,
                    notNullAnnotation.get(),
                    compilationUnitTree);
        } else if (TreeTypeUtils.isBinaryTree(initializer)) {
            trees.printMessage(Diagnostic.Kind.ERROR,
                    ErrorMessage.BINARY_EXPRESSION,
                    notNullAnnotation.get(),
                    compilationUnitTree);
        } else {
            trees.printMessage(Diagnostic.Kind.ERROR,
                    ErrorMessage.PARSING_BUG,
                    notNullAnnotation.get(),
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

    public JCTree.JCMethodInvocation transform(JCTree.JCExpression astToTransform, JCTree.JCAnnotation jcAnnotation) {
        Deque<JCTree.JCExpression> symbols = SymbolStackUtils.buildSymbols(astToTransform);
        if (symbols == null || symbols.isEmpty()) {
            trees.printMessage(Diagnostic.Kind.ERROR,
                    ErrorMessage.PARSING_BUG,
                    jcAnnotation,
                    compilationUnitTree);
            return null;
        }
        JCTree.JCMethodInvocation jcMethodInvocation = SymbolStackUtils.processSymbols(treeMakerExtension, symbols);
        return treeMakerExtension.buildOrElse(jcMethodInvocation, astToTransform);
    }

}
