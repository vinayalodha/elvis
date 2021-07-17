package io.github.vinayalodha.elvis.plugin.ast.scanners;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.tree.JCTree;
import io.github.vinayalodha.elvis.plugin.ast.vistors.analyze.VariableTreeCorrector;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class AnalyzeTreeScanner extends TreeScanner<Void, Void> {

    protected final VariableTreeCorrector variableTreeCorrector;

    public AnalyzeTreeScanner(JavacTask javacTask, CompilationUnitTree compilationUnitTree) {
        variableTreeCorrector = new VariableTreeCorrector(javacTask, compilationUnitTree);
    }

    @Override
    public Void visitVariable(VariableTree node, Void unused) {
        variableTreeCorrector.handle((JCTree.JCVariableDecl) node);
        return super.visitVariable(node, unused);
    }
}


