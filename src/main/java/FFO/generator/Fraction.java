package FFO.generator;

/**
 * @author lbq
 * 处理分数的存储、运算和格式化输出，支持带分数和约分
 */
public class Fraction {
    public int numerator, denominator, whole;
    public Fraction(int num, int den) {
        this(0, num, den);
    }
    public Fraction(int whole, int num, int den) {
        this.whole = whole; this.numerator = num; this.denominator = den;
        normalize();
    }
    private void normalize() {
        if (denominator < 0) { numerator *= -1; denominator *= -1; }
        int gcd = gcd(Math.abs(numerator), denominator);
        numerator /= gcd; denominator /= gcd;
        if (numerator >= denominator) {
            whole += numerator / denominator; numerator %= denominator;
        }
    }
    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public Fraction add(Fraction other) {  int num = (this.whole * this.denominator + this.numerator) * other.denominator +
            (other.whole * other.denominator + other.numerator) * this.denominator;
        int den = this.denominator * other.denominator;
        return new Fraction(0, num, den);
    }

    public Fraction subtract(Fraction other) {  int num = (this.whole * this.denominator + this.numerator) * other.denominator -
            (other.whole * other.denominator + other.numerator) * this.denominator;
        int den = this.denominator * other.denominator;
        return new Fraction(0, num, den);
    }

    public Fraction multiply(Fraction other) { int num = (this.whole * this.denominator + this.numerator) *
            (other.whole * other.denominator + other.numerator);
        int den = this.denominator * other.denominator;
        return new Fraction(0, num, den);
    }

    public Fraction divide(Fraction other) { int num = (this.whole * this.denominator + this.numerator) * other.denominator;
        int den = this.denominator * (other.whole * other.denominator + other.numerator);
        return new Fraction(0, num, den);
    }

    @Override public String toString() { if (whole > 0) {
        if (numerator == 0) {
            return Integer.toString(whole);
        } else {
            return whole + "'" + numerator + "/" + denominator;
        }
    } else {
        if (numerator == 0) {
            return "0";
        } else {
            return numerator + "/" + denominator;
        }
    } }
}
