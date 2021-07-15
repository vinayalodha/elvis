package io.github.vinayalodha.elvis.plugin.ast.vistors;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Trees;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import io.github.vinayalodha.elvis.plugin.utils.TreeMakerExtension;
import io.github.vinayalodha.elvis.plugin.utils.TreeUtils;

import java.util.Set;


/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public abstract class AbstractTreeVisitor<T extends JCTree> implements TreeVisitor<T> {

    protected final Trees trees;
    protected final TreeMaker treeMaker;

    protected final Context context;

    protected final JavacTask javacTask;
    protected final CompilationUnitTree compilationUnitTree;
    protected final Set<String> imports;
    protected final TreeMakerExtension treeMakerExtension;


    protected AbstractTreeVisitor(JavacTask javacTask, CompilationUnitTree compilationUnitTree) {
        this.javacTask = javacTask;
        this.compilationUnitTree = compilationUnitTree;
        this.context = ((BasicJavacTask) javacTask).getContext();

        trees = Trees.instance(javacTask);
        this.treeMaker = TreeMaker.instance(context);
        this.treeMakerExtension = new TreeMakerExtension(context);

        this.imports = TreeUtils.getImports((JCTree.JCCompilationUnit) compilationUnitTree);
    }
}
