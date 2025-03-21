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
     * @param num 数据范围
     * @return
     */
    public static List<String> generate(int count, int num) {
        List<String> questions = new ArrayList<>();
        while (questions.size() < count) {
            Expression expr = generateExpression(3, num);
            String canonical = expr.toCanonicalString();
            if (!generated.contains(canonical)) {
                generated.add(canonical);
                questions.add(expr + " = ");
            }
        }
        return questions;
    }

    private static Expression generateExpression(int opsLeft, int num) {
        if (opsLeft == 0 || random.nextDouble() < 0.3 - 0.1 * opsLeft) {
            return new NumberExpression(generateNumber(num));
        } else {
            char op = "+-×÷".charAt(random.nextInt(4));
            int remainingOps = opsLeft - 1;

            // 动态分配剩余配额给左右子树
            int leftOps = random.nextInt(remainingOps + 1);
            int rightOps = remainingOps - leftOps;

            Expression right = generateExpression(rightOps, num);
            Expression left ;
            Fraction rightVal = right.calculate();
            do {
                left = generateExpression(leftOps, num);
                Fraction leftVal = left.calculate();
                if (op == '-' && leftVal.subtract(rightVal).whole < 0) {
                    continue;
                }
                if (op == '÷') {
                    //循环生成直到右子树非零
                    do {
                        right = generateExpression(rightOps, num);
                        rightVal = right.calculate();
                    } while (isZero(rightVal));

                    // 继续验证是否为真分数
                    Fraction result = leftVal.divide(rightVal);
                    if (!isProperFraction(result)) {
                        continue;
                    }
                }
                break;
            } while (true);
            return new OperatorExpression(op, left, right);
        }
    }

    private static Fraction generateNumber(int num) {
        if (random.nextBoolean()) {
            return new Fraction(random.nextInt(num-1)+1, 1);
        } else {
            int den = random.nextInt(8) + 2;
            return new Fraction(0, random.nextInt(den - 1) + 1, den);
        }
    }

    private static boolean isProperFraction(Fraction f) {
        return f.whole == 0 && f.numerator < f.denominator;
    }

    private static boolean isZero(Fraction f) {
        return f.whole == 0 && f.numerator == 0;
    }
}
