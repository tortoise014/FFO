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
    // 核心答案计算逻辑
    public static List<String> getAnswers(List<String> questions) {
        List<String> answers = new ArrayList<>();
        for (String question : questions) {
            try {
                String exprStr = question.replace(" = ", "").trim();
                Expression expr = parseExpression(exprStr);
                answers.add(expr.calculate().toString());
            } catch (Exception e) {
                answers.add("无效题目");
            }
        }
        return answers;
    }

    // 表达式解析入口
    private static Expression parseExpression(String input) {
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        return parser.parse();
    }

    // 词法分析器
    private static class Lexer {
        private final String input;
        private int pos;
        private Token currentToken;

        public Lexer(String input) {
            this.input = input.replaceAll("\\s+", " ").trim();
            this.pos = 0;
            advance();
        }

        private void advance() {
            if (pos >= input.length()) {
                currentToken = new Token(TokenType.EOF, "");
                return;
            }

            char ch = input.charAt(pos++);
            while (Character.isWhitespace(ch)){
                if (pos >= input.length()) {
                    currentToken = new Token(TokenType.EOF, "");
                    return;
                }
                ch = input.charAt(pos++);
            }

            switch (ch) {
                case '+': currentToken = new Token(TokenType.PLUS, "+"); break;
                case '-': currentToken = new Token(TokenType.MINUS, "-"); break;
                case '×': currentToken = new Token(TokenType.MULTIPLY, "×"); break;
                case '÷': currentToken = new Token(TokenType.DIVIDE, "÷"); break;
                case '(': currentToken = new Token(TokenType.LPAREN, "("); break;
                case ')': currentToken = new Token(TokenType.RPAREN, ")"); break;
                default:
                    if (Character.isDigit(ch) || ch == '\'' || ch == '/') {
                        StringBuilder sb = new StringBuilder();
                        sb.append(ch);
                        while (pos < input.length()) {
                            ch = input.charAt(pos);
                            if (Character.isDigit(ch) || ch == '\'' || ch == '/') {
                                sb.append(ch);
                                pos++;
                            } else {
                                break;
                            }
                        }
                        currentToken = new Token(TokenType.NUMBER, sb.toString());
                    } else {
                        throw new IllegalArgumentException("非法字符: " + ch);
                    }
            }
        }

        public Token getCurrentToken() {
            return currentToken;
        }

        public void eat(TokenType type) {
            if (currentToken.type == type) {
                advance();
            } else {
                throw new IllegalArgumentException("语法错误，期望: " + type + "，实际: " + currentToken.type);
            }
        }
    }

    // 语法分析器
    private static class Parser {
        private final Lexer lexer;

        public Parser(Lexer lexer) {
            this.lexer = lexer;
        }

        public Expression parse() {
            Expression expr = parseExpression();
            if (lexer.getCurrentToken().type != TokenType.EOF) {
                throw new IllegalArgumentException("未预期的结束");
            }
            return expr;
        }

        private Expression parseExpression() {
            Expression expr = parseTerm();
            while (true) {
                Token token = lexer.getCurrentToken();
                if (token.type == TokenType.PLUS || token.type == TokenType.MINUS) {
                    lexer.eat(token.type);
                    Expression right = parseTerm();
                    expr = new OperatorExpression(
                            token.value.charAt(0),
                            expr,
                            right
                    );
                } else {
                    break;
                }
            }
            return expr;
        }

        private Expression parseTerm() {
            Expression expr = parseFactor();
            while (true) {
                Token token = lexer.getCurrentToken();
                if (token.type == TokenType.MULTIPLY || token.type == TokenType.DIVIDE) {
                    lexer.eat(token.type);
                    Expression right = parseFactor();
                    expr = new OperatorExpression(
                            token.value.charAt(0),
                            expr,
                            right
                    );
                } else {
                    break;
                }
            }
            return expr;
        }

        private Expression parseFactor() {
            Token token = lexer.getCurrentToken();
            if (token.type == TokenType.NUMBER) {
                lexer.eat(TokenType.NUMBER);
                return new NumberExpression(parseNumber(token.value));
            } else if (token.type == TokenType.LPAREN) {
                lexer.eat(TokenType.LPAREN);
                Expression expr = parseExpression();
                lexer.eat(TokenType.RPAREN);
                return expr;
            } else {
                throw new IllegalArgumentException("语法错误，非法的因子: " + token.value);
            }
        }

        private Fraction parseNumber(String str) {
            try {
                // 处理带分数 2'3/8
                if (str.contains("'")) {
                    String[] parts = str.split("['/]");
                    int whole = Integer.parseInt(parts[0]);
                    int numerator = Integer.parseInt(parts[1]);
                    int denominator = Integer.parseInt(parts[2]);
                    return new Fraction(whole, numerator, denominator);
                }

                // 处理分数 3/5
                if (str.contains("/")) {
                    String[] parts = str.split("/");
                    return new Fraction(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1])
                    );
                }

                // 处理整数
                return new Fraction(Integer.parseInt(str), 1);
            } catch (Exception e) {
                throw new IllegalArgumentException("数字格式错误: " + str);
            }
        }
    }

    // 词法单元定义
    private enum TokenType {
        NUMBER, PLUS, MINUS, MULTIPLY, DIVIDE, LPAREN, RPAREN, EOF
    }

    private static class Token {
        public final TokenType type;
        public final String value;

        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }
    }
}
