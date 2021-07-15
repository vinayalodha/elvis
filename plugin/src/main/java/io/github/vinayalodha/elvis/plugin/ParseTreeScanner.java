package io.github.vinayalodha.elvis.plugin;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.tree.JCTree;
import io.github.vinayalodha.elvis.plugin.ast.vistors.parse.VariableTreeVisitor;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class ParseTreeScanner extends TreeScanner<Void, Void> {

    protected final VariableTreeVisitor variableTreeVisitor;

    public ParseTreeScanner(JavacTask javacTask, CompilationUnitTree compilationUnitTree) {
        variableTreeVisitor = new VariableTreeVisitor(javacTask, compilationUnitTree);
    }

    @Override
    public Void visitVariable(VariableTree node, Void unused) {
        variableTreeVisitor.handle(((JCTree.JCVariableDecl) node));
        return super.visitVariable(node, unused);
    }
}


