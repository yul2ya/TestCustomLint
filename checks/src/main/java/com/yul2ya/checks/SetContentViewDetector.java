package com.yul2ya.checks;

import com.android.annotations.Nullable;
import com.android.tools.lint.detector.api.*;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UCallExpression;

import java.util.Arrays;
import java.util.List;

public class SetContentViewDetector
        extends Detector
        implements SourceCodeScanner {
    @Nullable
    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("setContentView");
    }

    @Override
    public void visitMethodCall(@NotNull JavaContext context, @NotNull UCallExpression node, @NotNull PsiMethod method) {
        if (context.getEvaluator().isMemberInClass(method, "androidx.databinding.DataBindingUtil")) {
            return;
        }
        context.report(
                ISSUE,
                node,
                context.getLocation(node),
                "Use DataBindingUtil.setContentView() instead"
        );
    }

    public static final Issue ISSUE = Issue.create(
            SetContentViewDetector.class.getSimpleName(),
            "Prohibits usages of setContentView()",
            "Prohibits usages of setContentView(), use DataBindingUtil.setContentView() instead",
            Category.CORRECTNESS,
            5,
            Severity.ERROR,
            new Implementation(SetContentViewDetector.class, Scope.JAVA_FILE_SCOPE)
    );
}