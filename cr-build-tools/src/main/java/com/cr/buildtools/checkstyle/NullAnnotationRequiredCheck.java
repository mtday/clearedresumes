package com.cr.buildtools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * This custom checkstyle check is used to enforce method return types and parameters to include one of the
 * null-checking annotations.
 */
public class NullAnnotationRequiredCheck extends Check {
    private static final Set<String> NULL_CHECKING_ANNOTATIONS =
            new TreeSet<>(Arrays.asList("Nonnull", "Nullable", "CheckForNull"));

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.METHOD_DEF, TokenTypes.PARAMETER_DEF};
    }

    @Override
    public void visitToken(final DetailAST ast) {
        validateNullCheckingAnnotation(ast);
    }

    private boolean validateNullCheckingAnnotation(final DetailAST ast) {
        if (ast == null) {
            return false;
        }

        switch (ast.getType()) {
            case TokenTypes.METHOD_DEF:
                final DetailAST methodIdent = ast.findFirstToken(TokenTypes.IDENT);
                final DetailAST methodModifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
                final DetailAST methodType = ast.findFirstToken(TokenTypes.TYPE);
                final boolean isMethodValid =
                        isPrimitiveType(methodType) || validateNullCheckingAnnotation(methodModifiers);

                if (!isMethodValid) {
                    final String error =
                            String.format("Method %s return value requires one of the null checking annotations (%s).",
                                    methodIdent.getText(), String.join(", ", NULL_CHECKING_ANNOTATIONS));
                    log(methodIdent.getLineNo(), methodIdent.getColumnNo(), error);
                }
                return isMethodValid;

            case TokenTypes.PARAMETER_DEF:
                final DetailAST paramIdent = ast.findFirstToken(TokenTypes.IDENT);
                final DetailAST paramModifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
                final DetailAST paramType = ast.findFirstToken(TokenTypes.TYPE);
                final boolean isParameterValid =
                        inCatch(ast) || isPrimitiveType(paramType) || validateNullCheckingAnnotation(paramModifiers);

                if (!isParameterValid) {
                    final String error =
                            String.format("Parameter %s requires one of the null checking annotations (%s).",
                                    paramIdent.getText(), String.join(", ", NULL_CHECKING_ANNOTATIONS));
                    log(paramIdent.getLineNo(), paramIdent.getColumnNo(), error);
                }
                return isParameterValid;

            case TokenTypes.MODIFIERS:
                DetailAST node = ast.getFirstChild();
                while (node != null) {
                    if (node.getType() == TokenTypes.ANNOTATION && validateNullCheckingAnnotation(node)) {
                        return true;
                    }
                    node = node.getNextSibling();
                }
                break;

            case TokenTypes.ANNOTATION:
                if (isNullCheckingAnnotation(ast.findFirstToken(TokenTypes.IDENT))) {
                    return true;
                }
                break;

            default:
        }

        return false;
    }

    private boolean isNullCheckingAnnotation(final DetailAST annotationIdent) {
        if (annotationIdent == null) {
            return false;
        }
        final String identName = annotationIdent.getText();
        return identName != null && NULL_CHECKING_ANNOTATIONS.contains(identName);
    }

    private boolean isPrimitiveType(final DetailAST ast) {
        if (ast == null) {
            // When the type is not present, treat it as being primitive. This happens for "typeless" parameters,
            // e.g., parameters to Java 8 lambdas.
            return true;
        }

        switch (ast.getType()) {
            case TokenTypes.TYPE:
                return isPrimitiveType(ast.getFirstChild());

            case TokenTypes.LITERAL_BOOLEAN:
            case TokenTypes.LITERAL_BYTE:
            case TokenTypes.LITERAL_CHAR:
            case TokenTypes.LITERAL_DOUBLE:
            case TokenTypes.LITERAL_FLOAT:
            case TokenTypes.LITERAL_INT:
            case TokenTypes.LITERAL_LONG:
            case TokenTypes.LITERAL_SHORT:
            case TokenTypes.LITERAL_VOID:
                return true;

            default:
        }

        return false;
    }

    private boolean inCatch(final DetailAST ast) {
        DetailAST node = ast;
        while (node != null) {
            if (node.getType() == TokenTypes.LITERAL_CATCH) {
                return true;
            }
            node = node.getParent();
        }
        return false;
    }
}
