package io.github.vinayalodha.elvis.plugin.utils;

import com.sun.tools.javac.tree.JCTree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public final class SymbolStackUtils {

    private SymbolStackUtils() {
    }

    public static Deque<JCTree.JCExpression> buildSymbols(JCTree.JCExpression expression) {
        Deque<JCTree.JCExpression> symbols = new ArrayDeque<>();
        while (true) {
            if (expression == null) {
                break;
            } else if (TreeTypeUtils.isParenthesis(expression)) {
                expression = ((JCTree.JCParens) expression).getExpression();
            } else if (TreeTypeUtils.isCast(expression)) {
                JCTree.JCTypeCast castExpression = (JCTree.JCTypeCast) expression;
                symbols.push(castExpression);
                expression = castExpression.getExpression();
            } else if (TreeTypeUtils.isConstructor(expression)
                    || TreeTypeUtils.isLiteralTree(expression)
                    || TreeTypeUtils.isFieldAccessTreeOnThis(expression)
                    || TreeTypeUtils.isMethodInvocationTreeOnThis(expression)
                    || TreeTypeUtils.isTernaryOperator(expression)
                    || TreeTypeUtils.isBinaryTree(expression)) {
                symbols.push(expression);
                break;
            } else if (TreeTypeUtils.isArrayAccessTree(expression)) {
                JCTree.JCArrayAccess arrayAccess = (JCTree.JCArrayAccess) expression;
                symbols.push(expression);
                expression = arrayAccess.indexed;
            } else if (TreeTypeUtils.isMethodInvocationTree(expression)) {
                symbols.push(expression);
                expression = TreeUtils.parentForMethodInvocation((JCTree.JCMethodInvocation) expression);
            } else if (TreeTypeUtils.isFieldAccessTree(expression)) {
                symbols.push(expression);
                expression = TreeUtils.parentForFieldAccess((JCTree.JCFieldAccess) expression);
            } else if (TreeTypeUtils.isIdentifierTree(expression)) {
                symbols.push(expression);
                break;
            } else {
                return null;
            }
        }
        return symbols;
    }

    public static JCTree.JCMethodInvocation processSymbols(TreeMakerExtension treeMakerExtension, Deque<JCTree.JCExpression> symbols) {
        JCTree.JCMethodInvocation optionalExpression = treeMakerExtension.buildOptionalStatement(symbols.pop());
        symbols.push(optionalExpression);
        while (symbols.size() >= 2) {
            // First guy will always be method invocation
            // eg map() or ofNullable()
            JCTree.JCMethodInvocation first = (JCTree.JCMethodInvocation) symbols.pop();
            JCTree second = symbols.pop();
            JCTree.JCMethodInvocation mapMethod;
            if (TreeTypeUtils.isMethodInvocationTree(second))
                mapMethod = treeMakerExtension.buildMapStatement(first, (JCTree.JCMethodInvocation) second);
            else if (TreeTypeUtils.isArrayAccessTree(second))
                mapMethod = treeMakerExtension.buildMapStatement(first, (JCTree.JCArrayAccess) second);
            else if (TreeTypeUtils.isFieldAccessTree(second))
                mapMethod = treeMakerExtension.buildMapStatement(first, (JCTree.JCFieldAccess) second);
            else if (TreeTypeUtils.isCast(second))
                mapMethod = treeMakerExtension.buildMapStatement(first, (JCTree.JCTypeCast) second);
            else
                throw new RuntimeException("Unable to apply @NullSafe");
            symbols.push(mapMethod);
        }
        return (JCTree.JCMethodInvocation) symbols.pop();
    }

    public static boolean shouldDoStackProcessing(JCTree.JCExpression initializer) {
        return TreeTypeUtils.isMethodInvocationTree(initializer)
                || TreeTypeUtils.isArrayAccessTree(initializer)
                || TreeTypeUtils.isFieldAccessTree(initializer)
                || TreeTypeUtils.isIdentifierTree(initializer)
                || TreeTypeUtils.isTernaryOperator(initializer);
    }
}
