package io.github.vinayalodha.elvis.plugin.utils;

import com.sun.source.tree.ImportTree;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Name;
import io.github.vinayalodha.elvis.annotation.NullSafe;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class TreeUtils {

    public static Optional<JCTree.JCAnnotation> findNotNullAnnotation(List<JCTree.JCAnnotation> annotations, Set<String> imports) {
        boolean notNullIsInImport = imports.contains(NullSafe.class.getName());
        return annotations
                .stream()
                .filter(jcAnnotation -> {
                    JCTree annotationType = jcAnnotation.getAnnotationType();
                    if (NullSafe.class.getName().equals(annotationType.toString()))
                        return true;
                    if (!notNullIsInImport)
                        return false;

                    return NullSafe.class.getSimpleName().equals(annotationType.toString());
                }).findAny();
    }

    public static JCTree parentForMethodInvocation(JCTree.JCMethodInvocation tree) {
        if (!TreeTypeUtils.isMethodInvocationTree(tree))
            return null;

        JCTree.JCExpression expression = tree.getMethodSelect();

        if (TreeTypeUtils.isFieldAccessTree(expression))
            return ((JCTree.JCFieldAccess) expression).getExpression();
        else if (TreeTypeUtils.isIdentifierTree(expression))
            return null;

        return null;
    }

    public static Name getMethodName(JCTree.JCMethodInvocation methodInvocation) {
        JCTree.JCExpression methodSelect = methodInvocation.getMethodSelect();
        if (TreeTypeUtils.isFieldAccessTree(methodSelect))
            return ((JCTree.JCFieldAccess) methodSelect).name;
        if (TreeTypeUtils.isIdentifierTree(methodSelect))
            return ((JCTree.JCIdent) methodSelect).name;
        return null;
    }

    public static JCTree parentForFieldAccess(JCTree.JCFieldAccess tree) {
        return tree.getExpression();
    }

    public static JCTree.JCLiteral extractNotNullAnnotationValue(JCTree.JCAnnotation notNullAnnotation) {
        com.sun.tools.javac.util.List<JCTree.JCExpression> arguments = notNullAnnotation.getArguments();
        if (arguments == null || arguments.isEmpty())
            return null;

        if (arguments.size() == 1 && arguments.get(0).getKind() == Tree.Kind.STRING_LITERAL)
            return (JCTree.JCLiteral) arguments.get(0);

        return arguments.stream()
                .filter(obj -> obj.getKind() == Tree.Kind.ASSIGNMENT)
                .map(obj -> (JCTree.JCAssign) obj)
                .filter(obj -> "value".equals(obj.lhs.toString()))
                .map(obj -> obj.rhs)
                .map(obj -> (JCTree.JCLiteral) obj)
                .findAny().orElse(null);
    }

    public static Set<String> getImports(JCTree.JCCompilationUnit compilationUnitTree) {
        return compilationUnitTree.getImports()
                .stream()
                .map(ImportTree::getQualifiedIdentifier)
                .map(Object::toString)
                .collect(Collectors.toSet());
    }

    public static com.sun.tools.javac.util.List<JCTree.JCAnnotation> getAnnotations(JCTree.JCVariableDecl tree) {
        return Optional.ofNullable(tree)
                .map(JCTree.JCVariableDecl::getModifiers)
                .map(JCTree.JCModifiers::getAnnotations)
                .orElse(com.sun.tools.javac.util.List.nil());
    }
}
