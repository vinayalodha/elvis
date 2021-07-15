package io.github.vinayalodha.elvis.plugin.utils;

import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.tree.JCTree;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class TreeTypeUtils {

    public static boolean isLiteralTree(Tree tree) {
        return isTreeNotNull(tree) && tree.getKind().asInterface().equals(LiteralTree.class);
    }

    private static boolean isTreeNotNull(Tree tree) {
        return tree != null;
    }

    public static boolean isArrayAccessTree(Tree tree) {
        return isTreeNotNull(tree) && tree.getKind() == Tree.Kind.ARRAY_ACCESS;
    }

    public static boolean isIdentifierTree(Tree tree) {
        return isTreeNotNull(tree) && tree.getKind() == Tree.Kind.IDENTIFIER;
    }

    public static boolean isBinaryTree(Tree tree) {
        return isTreeNotNull(tree) && tree.getKind() == Tree.Kind.PLUS;
    }

    public static boolean isFieldAccessTreeOnThis(JCTree tree) {
        if (!isFieldAccessTree(tree))
            return false;

        JCTree.JCFieldAccess fieldAccess = ((JCTree.JCFieldAccess) tree);
        JCTree parent = TreeUtils.parentForFieldAccess(fieldAccess);
        if (parent == null)
            return false;

        return parent.toString().equals("this");
    }

    public static boolean isFieldAccessTree(Tree tree) {
        return isTreeNotNull(tree) && tree.getKind() == Tree.Kind.MEMBER_SELECT;
    }

    public static boolean isMethodInvocationTreeOnThis(JCTree tree) {
        if (!isMethodInvocationTree(tree))
            return false;

        JCTree.JCMethodInvocation methodInvocation = ((JCTree.JCMethodInvocation) tree);
        JCTree parent = TreeUtils.parentForMethodInvocation(methodInvocation);
        if (parent == null)
            return false;

        return parent.toString().equals("this");
    }

    public static boolean isMethodInvocationTree(Tree tree) {
        return isTreeNotNull(tree) && tree.getKind() == Tree.Kind.METHOD_INVOCATION;
    }

    public static boolean isConstructor(JCTree tree) {
        return isTreeNotNull(tree) && tree.getKind() == Tree.Kind.NEW_CLASS;
    }

    public static boolean isNullLiteral(JCTree.JCLiteral tree) {
        return isTreeNotNull(tree) && tree.getKind() == Tree.Kind.NULL_LITERAL;
    }

    public static boolean isParenthesis(JCTree treeTemp) {
        return isTreeNotNull(treeTemp) && treeTemp.getKind() == Tree.Kind.PARENTHESIZED;
    }


    public static boolean isCast(JCTree treeTemp) {
        return isTreeNotNull(treeTemp) && treeTemp.getKind() == Tree.Kind.TYPE_CAST;
    }
}
