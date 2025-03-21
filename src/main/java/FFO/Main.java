package FFO;

import FFO.cli.CommandLineParser;
import FFO.file.FileHandler;
import FFO.generator.AnswerCalculator;
import FFO.generator.ArithmeticGenerator;
import FFO.grader.AnswerGrader;

import java.util.List;

/**
 * @Author tortoise
 * @Date 2025/3/11 0:29
 * @PackageName:PACKAGE_NAME
 * @ClassName: FFO.Main
 * @Description:
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {

        CommandLineParser parser = new CommandLineParser(args);
        if (parser.hasError()) {
            System.out.println(parser.getHelpMessage());
            return;
        }

        if (parser.isGenerateMode()) {
            // 生成模式
            List<String> questions = ArithmeticGenerator.generate(parser.getNumQuestions(), parser.getRange());
            List<String> answers = AnswerCalculator.getAnswers(questions);

            FileHandler.writeFile("Exercises.txt", questions);
            FileHandler.writeFile("Answers.txt", answers);
        } else if (parser.isGradeMode()) {
            // 评分模式
            List<String> questions = FileHandler.readFile(parser.getExerciseFile());
            List<String> answers = FileHandler.readFile(parser.getAnswerFile());

            AnswerGrader grader = new AnswerGrader();
            grader.grade(questions, answers);
        }
    }
}

