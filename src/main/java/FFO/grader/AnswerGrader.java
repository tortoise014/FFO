package FFO.grader;
import FFO.file.FileHandler;
import FFO.generator.AnswerCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author tortoise
 * @Date 2025/3/11 0:28
 * @PackageName:grader
 * @ClassName: AnswerGrader
 * @Description:答案判定模块
 * @Version 1.0
 */
public class AnswerGrader {
    /**
     * 判定题目和答案的对错，并统计结果。
     *
     * @param questions 题目列表
     * @param answers   答案列表
     */
    public void grade(List<String> questions, List<String> answers) {
        List<Integer> correctIndices = new ArrayList<>(); // 正确的题目编号
        List<Integer> wrongIndices = new ArrayList<>();   // 错误的题目编号
        List<String> answers1 = AnswerCalculator.getAnswers(questions);
        for (int i = 0; i < questions.size(); i++) {
            String question = questions.get(i);
            String expectedAnswer = answers.get(i);

            // 计算题目的正确答案
            // 比较答案
            if (answers1.get(i).equals(expectedAnswer)) {
                correctIndices.add(i + 1); // 题目编号从 1 开始
            } else {
                wrongIndices.add(i + 1);
            }
        }

        // 输出统计结果
        saveResult(correctIndices, wrongIndices);
    }

    /**
     * 将统计结果保存到文件。
     *
     * @param correctIndices 正确的题目编号
     * @param wrongIndices   错误的题目编号
     */
    private void saveResult(List<Integer> correctIndices, List<Integer> wrongIndices) {
        StringBuilder result = new StringBuilder();
        result.append("Correct: ").append(correctIndices.size()).append(" (");
        for (int i = 0; i < correctIndices.size(); i++) {
            result.append(correctIndices.get(i));
            if (i < correctIndices.size() - 1) {
                result.append(", ");
            }
        }
        result.append(")\n");

        result.append("Wrong: ").append(wrongIndices.size()).append(" (");
        for (int i = 0; i < wrongIndices.size(); i++) {
            result.append(wrongIndices.get(i));
            if (i < wrongIndices.size() - 1) {
                result.append(", ");
            }
        }
        result.append(")");

        // 写入文件
        FileHandler.writeFile("Grade.txt", List.of(result.toString()));
        System.out.println("统计结果已保存到 Grade.txt");
    }
}
