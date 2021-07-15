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

    public static Deque<JCTree> buildSymbolStack(final JCTree treeForWhichSymbolStackToBeBuilt) {
        JCTree treeTemp = treeForWhichSymbolStackToBeBuilt;
        Deque<JCTree> symbolStack = new ArrayDeque<>();
        while (true) {
            if (treeTemp == null) {
                break;
            } else if (TreeTypeUtils.isConstructor(treeTemp)
                    || TreeTypeUtils.isLiteralTree(treeTemp)
                    || TreeTypeUtils.isFieldAccessTreeOnThis(treeTemp)
                    || TreeTypeUtils.isMethodInvocationTreeOnThis(treeTemp)) {
                symbolStack.push(treeTemp);
                break;
            } else if (TreeTypeUtils.isArrayAccessTree(treeTemp)) {
                JCTree.JCArrayAccess arrayAccess = (JCTree.JCArrayAccess) treeTemp;
                symbolStack.push(treeTemp);
                treeTemp = arrayAccess.indexed;
            } else if (TreeTypeUtils.isMethodInvocationTree(treeTemp)) {
                symbolStack.push(treeTemp);
                treeTemp = TreeUtils.parentForMethodInvocation((JCTree.JCMethodInvocation) treeTemp);
            } else if (TreeTypeUtils.isFieldAccessTree(treeTemp)) {
                symbolStack.push(treeTemp);
                treeTemp = TreeUtils.parentForFieldAccess((JCTree.JCFieldAccess) treeTemp);
            } else if (TreeTypeUtils.isIdentifierTree(treeTemp)) {
                // TODO bug
                symbolStack.push(treeTemp);
                break;
            } else {
                return null;
            }
        }
        return symbolStack;
    }

    public static JCTree.JCMethodInvocation processSymbolStack(TreeMakerExtension treeMakerExtension, Deque<JCTree> symbolStack) {
        JCTree firstElement = symbolStack.pop();
        JCTree.JCMethodInvocation optionalStatement = treeMakerExtension.buildOptionalStatement((JCTree.JCExpression) firstElement);
        symbolStack.push(optionalStatement);
        while (symbolStack.size() >= 2) {
            // First guy will always be method invocation
            // eg map() or ofNullable()
            JCTree.JCMethodInvocation first = (JCTree.JCMethodInvocation) symbolStack.pop();
            JCTree second = symbolStack.pop();
            if (TreeTypeUtils.isMethodInvocationTree(second)) {
                JCTree.JCMethodInvocation mapMethod = treeMakerExtension.buildMapStatement(first, (JCTree.JCMethodInvocation) second);
                symbolStack.push(mapMethod);
            } else if (TreeTypeUtils.isArrayAccessTree(second)) {
                JCTree.JCMethodInvocation mapMethod = treeMakerExtension.buildMapStatement(first, (JCTree.JCArrayAccess) second);
                symbolStack.push(mapMethod);
            } else if (TreeTypeUtils.isFieldAccessTree(second)) {
                JCTree.JCMethodInvocation mapMethod = treeMakerExtension.buildMapStatement(first, (JCTree.JCFieldAccess) second);
                symbolStack.push(mapMethod);
            }

            // TODO casting symbol
        }
        return (JCTree.JCMethodInvocation) symbolStack.pop();
    }
}
