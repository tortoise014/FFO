package FFO.generator;

/**
 * @author lbq
 * 表示表达式节点，支持计算和规范化字符串生成
 */
abstract class Expression {
    public abstract Fraction calculate();
    public abstract String toCanonicalString();
    @Override
    public abstract String toString();
}

class NumberExpression extends FFO.generator.Expression {
    private final Fraction value;
    public NumberExpression(Fraction value) { this.value = value; }
    @Override public Fraction calculate() { return value; }
    @Override public String toCanonicalString() { return value.toString(); }
    @Override public String toString() { return value.toString(); }
}

class OperatorExpression extends FFO.generator.Expression {
    private final char operator;
    private final FFO.generator.Expression left;
    private final FFO.generator.Expression right;
    public OperatorExpression(char op, FFO.generator.Expression l, FFO.generator.Expression r) {
        operator = op; left = l; right = r;
    }
    @Override public Fraction calculate() {
        Fraction lVal = left.calculate(), rVal = right.calculate();
        return switch (operator) {
            case '+' -> lVal.add(rVal);
            case '-' -> lVal.subtract(rVal);
            case '×' -> lVal.multiply(rVal);
            case '÷' -> lVal.divide(rVal);
            default -> throw new IllegalArgumentException("Invalid operator");
        };
    }
    @Override public String toCanonicalString() {
        String lStr = left.toCanonicalString(), rStr = right.toCanonicalString();
        if (operator == '+' || operator == '×') {
            if (lStr.compareTo(rStr) > 0) {
                return "(" + rStr +  operator +  lStr + ")";
            }
        }
        return "(" + lStr +  operator +  rStr + ")";
    }
    @Override public String toString() {
        String lStr = left.toString(), rStr = right.toString();
        if (left instanceof OperatorExpression) {
            lStr = "(" + lStr + ")";
        }
        if (right instanceof OperatorExpression) {
            rStr = "(" + rStr + ")";
        }
        return lStr +  operator +  rStr;
    }
}