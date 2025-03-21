package FFO.generator;

import FFO.file.FileHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class generate {
    @Test
    public void testAg() {
        List<String> list = ArithmeticGenerator.generate(10000,10);
        List<String> answer = AnswerCalculator.getAnswers(list);
        FileHandler.writeFile("C:\\Users\\lbq\\Desktop\\problem.txt", list);

        FileHandler.writeFile("C:\\Users\\lbq\\Desktop\\answer.txt", answer);
    }

}
