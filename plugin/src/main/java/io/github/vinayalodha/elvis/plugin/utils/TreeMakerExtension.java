package io.github.vinayalodha.elvis.plugin.utils;

import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class TreeMakerExtension {

    public final TreeMaker treeMaker;
    public final Names names;

    public TreeMakerExtension(Context context) {
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    public JCTree.JCMethodInvocation buildOrElse(JCTree.JCMethodInvocation methodInvocationOnWhichOrElseToBeBuilt,
                                                 JCTree.JCExpression originalExpressionBeforeAstTransformation) {
        JCTree.JCExpression elvisProxyIdent = buildFullyQualifiedClassNameExpression("io.github.vinayalodha.elvis.plugin.utils.ElvisProxy");
        Name dummyCallName = buildName("dummyCall");
        JCTree.JCFieldAccess dummyCallFieldAccess = treeMaker.Select(elvisProxyIdent, dummyCallName);
        JCTree.JCMethodInvocation orElseArgument = treeMaker.Apply(List.nil(), dummyCallFieldAccess,
                List.of(originalExpressionBeforeAstTransformation, treeMaker.Literal(TypeTag.BOT, null)));

        Name orElseName = buildName("orElse");
        JCTree.JCFieldAccess orElseFieldAccess = treeMaker.Select(methodInvocationOnWhichOrElseToBeBuilt, orElseName);
        return treeMaker.Apply(List.nil(), orElseFieldAccess, List.of(orElseArgument));
    }

    private JCTree.JCExpression buildFullyQualifiedClassNameExpression(String className) {
        String[] tokens = className.split("\\.");
        String firstToken = tokens[0];
        JCTree.JCExpression retVal = treeMaker.Ident(buildName(firstToken));
        for (int index = 1; index < tokens.length; index++) {
            retVal = treeMaker.Select(retVal, buildName(tokens[index]));
        }
        return retVal;
    }

    private Name buildName(String symbolName) {
        return names.fromString(symbolName);
    }

    public JCTree.JCMethodInvocation buildMapStatement(JCTree.JCMethodInvocation methodInvocationChain, JCTree.JCMethodInvocation mapMethod) {

        Name itName = buildName("it");
        JCTree.JCVariableDecl itVariableDef = treeMaker.Param(itName, null, null);
        JCTree.JCFieldAccess itFieldAccess = treeMaker.Select(treeMaker.Ident(itName), TreeUtils.getMethodName(mapMethod));
        JCTree.JCMethodInvocation lambdaBody = treeMaker.Apply(List.nil(), itFieldAccess, mapMethod.args);
        JCTree.JCLambda mapLambda = treeMaker.Lambda(List.of(itVariableDef), lambdaBody);

        return buildMapStatement(methodInvocationChain, mapLambda);
    }

    public JCTree.JCMethodInvocation buildMapStatement(JCTree.JCMethodInvocation previousMethodChain, JCTree.JCLambda lambdaToPassInMapMethod) {
        Name mapName = buildName("map");
        JCTree.JCFieldAccess mapFieldAccess = treeMaker.Select(previousMethodChain, mapName);
        return treeMaker.Apply(List.nil(), mapFieldAccess, List.of(lambdaToPassInMapMethod));
    }

    public JCTree.JCMethodInvocation buildMapStatement(JCTree.JCMethodInvocation methodInvocationChain, JCTree.JCArrayAccess jcArrayAccess) {

        Name itName = buildName("it");
        JCTree.JCVariableDecl itVariableDef = treeMaker.Param(itName, null, null);
        JCTree.JCArrayAccess lambdaBody = treeMaker.Indexed(treeMaker.Ident(itName), jcArrayAccess.index);
        JCTree.JCLambda mapLambda = treeMaker.Lambda(List.of(itVariableDef), lambdaBody);

        return buildMapStatement(methodInvocationChain, mapLambda);
    }

    public JCTree.JCMethodInvocation buildMapStatement(JCTree.JCMethodInvocation methodInvocationChain, JCTree.JCFieldAccess fieldToAccess) {
        Name itName = buildName("it");
        JCTree.JCVariableDecl itVariableDef = treeMaker.Param(itName, null, null);
        JCTree.JCFieldAccess itFieldAccess = treeMaker.Select(treeMaker.Ident(itName), (fieldToAccess).name);
        JCTree.JCLambda mapLambda = treeMaker.Lambda(List.of(itVariableDef), itFieldAccess);

        return buildMapStatement(methodInvocationChain, mapLambda);
    }

    public JCTree.JCMethodInvocation buildOptionalStatement(JCTree.JCExpression optionalArgument) {
        Name ofNullableName = buildName("ofNullable");
        JCTree.JCExpression optionalExpression = buildFullyQualifiedClassNameExpression("java.util.Optional");
        JCTree.JCFieldAccess ofNullableFieldAccess = treeMaker.Select(optionalExpression, ofNullableName);
        return treeMaker.Apply(List.nil(), ofNullableFieldAccess, List.of(optionalArgument));
    }
}
