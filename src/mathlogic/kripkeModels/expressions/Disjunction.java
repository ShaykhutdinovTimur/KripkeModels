package mathlogic.kripkeModels.expressions;

public class Disjunction extends AbstractExpression {
    private Expression A;
    private Expression B;

    public Disjunction(Expression A, Expression B) {
        this.A = A;
        this.B = B;
    }

    @Override
    public Expression clone() {
        return new Disjunction(A.clone(), B.clone());
    }

    @Override
    public void toStringRec(StringBuilder result) {
        result.append("(");
        A.toStringRec(result);
        result.append("|");
        B.toStringRec(result);
        result.append(")");
    }

    public Expression getA() {
        return A;
    }

    public Expression getB() {
        return B;
    }
}
