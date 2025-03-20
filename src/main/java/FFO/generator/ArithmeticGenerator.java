package FFO.generator;

import java.util.*;

/**
 * @author lbq
 * @Date 2025/3/11 0:27
 * @PackageName:generator
 * @ClassName: ArithmeticGenerator
 * @Description:题目生成模块
 * @Version 1.0
 */

public class ArithmeticGenerator {

    private static Random random = new Random();
    private static Set<String> generated = new HashSet<>();

    /**
     *
     * @param count 生成数量
     * @param opsLeft 运算符数量
     * @return
     */
    public static List<String> generate(int count, int opsLeft) {
        List<String> questions = new ArrayList<>();
        while (questions.size() < count) {
            Expression expr = generateExpression(opsLeft);
            String canonical = expr.toCanonicalString();
            if (!generated.contains(canonical)) {
                generated.add(canonical);
                questions.add(expr.toString() + " = ");
            }
        }
        return questions;
    }

    private static Expression generateExpression(int opsLeft) {
        if (opsLeft == 0 || random.nextDouble() < 0.3) {
            return new NumberExpression(generateNumber());
        } else {
            char op = "+-×÷".charAt(random.nextInt(4));
            Expression right = generateExpression(opsLeft - 1);
            Fraction rightVal = right.calculate();
            Expression left;
            do {
                left = generateExpression(opsLeft - 1);
                Fraction leftVal = left.calculate();
                if (op == '-' && leftVal.subtract(rightVal).whole < 0) {
                    continue;
                }
                if (op == '÷' && !isProperFraction(leftVal.divide(rightVal))) {
                    continue;
                }
                break;
            } while (true);
            return new OperatorExpression(op, left, right);
        }
    }

    private static Fraction generateNumber() {
        if (random.nextBoolean()) {
            return new Fraction(random.nextInt(10), 1);
        } else {
            int den = random.nextInt(8) + 2;
            return new Fraction(0, random.nextInt(den - 1) + 1, den);
        }
    }

    private static boolean isProperFraction(Fraction f) {
        return f.whole == 0 && f.numerator < f.denominator;
    }
}
