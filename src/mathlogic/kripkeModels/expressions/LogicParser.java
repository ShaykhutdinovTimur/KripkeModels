package mathlogic.kripkeModels.expressions;


public class LogicParser {
    private static LogicParser ourInstance = new LogicParser();
    private String s;
    private int next;

    private LogicParser() {
    }

    public static LogicParser getInstance() {
        return ourInstance;
    }

    private char getChar() {
        if (next < 0 || next >= s.length()) {
            next++;
            return '>';
        }
        return s.charAt(next++);
    }

    private void returnChar() {
        next--;
    }

    private Variable getVariable(char c) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(c);
            c = getChar();
        } while (Character.isDigit(c));
        returnChar();
        return new Variable(sb.toString());
    }

    private Expression parseFactor() throws IllegalArgumentException {
        char c = getChar();

        if (Character.isUpperCase(c)) {
            return getVariable(c);
        }

        Expression result;
        switch (c) {
            case '!':
                return new Negative(parseFactor());
            case '(':
                result = parseFormula();
                if (getChar() == ')') {
                    return result;
                }
            default:
                throw new IllegalArgumentException("Unexpected symbol on " + (next - 1));
        }
    }

    private Expression parseConjunction(Expression left) throws IllegalArgumentException {
        char c = getChar();

        switch (c) {
            case '&':
                return parseConjunction(new Conjunction(left, parseFactor()));
            default:
                returnChar();
                return left;
        }
    }

    private Expression parseDisjunction(Expression left) throws IllegalArgumentException {
        char c = getChar();
        switch (c) {
            case '|':
                return parseDisjunction(new Disjunction(left, parseConjunction(parseFactor())));
            default:
                returnChar();
                return left;
        }
    }

    private Expression parseImplication(Expression left) throws IllegalArgumentException {
        char c = getChar();
        switch (c) {
            case '-':
                if (getChar() == '>') {
                    return new Implication(left, parseImplication(parseDisjunction(parseConjunction(parseFactor()))));
                } else {
                    throw new IllegalArgumentException("Unexpected symbol on " + (next - 1));
                }
            default:
                returnChar();
                return left;
        }
    }

    private Expression parseFormula() throws IllegalArgumentException {
        return parseImplication(parseDisjunction(parseConjunction(parseFactor())));
    }

    public Expression parse(String str) throws IllegalArgumentException {
        s = str;
        s = s.replaceAll("\\s+", "");
        next = 0;
        Expression expression = parseFormula();
        if (next < s.length()) {
            throw new IllegalArgumentException("Unexpected symbol on " + next);
        }
        return expression;
    }
}
