package FFO.generator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lbq
 * @Date 2025/3/11 0:27
 * @PackageName:generator
 * @ClassName: AnswerCalculator
 * @Description:答案计算模块
 * @Version 1.0
 */
public class AnswerCalculator {
    public List<String> getAnswers(List<String> questions) {
        List<String> answers = new ArrayList<>();
        for (String q : questions) {
            String exprStr = q.replace(" = ", "");
            Expression expr = parseExpression(exprStr);
            answers.add(expr.calculate().toString());
        }
        return answers;
    }
    private Expression parseExpression(String expr) {
        // 实现复杂表达式解析逻辑（此处省略）
        throw new UnsupportedOperationException("Parser not implemented");
    }
}
