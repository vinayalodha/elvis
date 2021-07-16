package io.github.vinayalodha.elvis.plugin;

import com.sun.source.tree.TreeVisitor;
import com.sun.tools.javac.tree.JCTree;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class JCCastSymbol extends JCTree {

    public JCTree jcTree;

    @Override
    public Tag getTag() {
        return null;
    }

    @Override
    public void accept(Visitor v) {

    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> v, D d) {
        return null;
    }

    @Override
    public Kind getKind() {
        return Kind.TYPE_CAST;
    }
}
