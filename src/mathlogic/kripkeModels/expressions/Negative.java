package mathlogic.kripkeModels.expressions;

public class Negative extends AbstractExpression {
    private Expression A;

    public Negative(Expression A) {
        this.A = A;
    }

    @Override
    public void toStringRec(StringBuilder result) {
        result.append("(!");
        A.toStringRec(result);
        result.append(")");
    }

    @Override
    public Expression clone() {
        return new Negative(A.clone());
    }

    public Expression getA() {
        return A;
    }

}
