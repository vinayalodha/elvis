package io.github.vinayalodha.elvis.plugin.ast.vistors;

import com.sun.tools.javac.tree.JCTree;


/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public interface TreeVisitor<T extends JCTree> {
    void handle(T tree);
}
