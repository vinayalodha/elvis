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

    public static boolean isArrayAccessTree(JCTree tree) {
        return isKind(tree, Tree.Kind.ARRAY_ACCESS);
    }

    public static boolean isFieldAccessTreeOnThis(JCTree tree) {
        if (!isFieldAccessTree(tree))
            return false;

        JCTree.JCFieldAccess fieldAccess = (JCTree.JCFieldAccess) tree;
        JCTree parent = TreeUtils.parentForFieldAccess(fieldAccess);
        if (parent == null)
            return false;

        return "this".equals(parent.toString());
    }

    public static boolean isIdentifierTree(JCTree tree) {
        return isKind(tree, Tree.Kind.IDENTIFIER);
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

    public static boolean isBinaryTree(JCTree tree) {
        return isKind(tree, Tree.Kind.PLUS);
    }

    public static boolean isFieldAccessTree(JCTree tree) {
        return isKind(tree, Tree.Kind.MEMBER_SELECT);
    }

    public static boolean isMethodInvocationTree(JCTree tree) {
        return isKind(tree, Tree.Kind.METHOD_INVOCATION);
    }

    public static boolean isConstructor(JCTree tree) {
        return isKind(tree, Tree.Kind.NEW_CLASS);
    }

    public static boolean isNullLiteral(JCTree tree) {
        return isKind(tree, Tree.Kind.NULL_LITERAL);
    }

    public static boolean isParenthesis(JCTree tree) {
        return isKind(tree, Tree.Kind.PARENTHESIZED);
    }

    public static boolean isCast(JCTree tree) {
        return isKind(tree, Tree.Kind.TYPE_CAST);
    }

    private static boolean isKind(JCTree jcTree, Tree.Kind kind) {
        return isTreeNotNull(jcTree) && jcTree.getKind() == kind;
    }

    private static boolean isTreeNotNull(Tree tree) {
        return tree != null;
    }

}
