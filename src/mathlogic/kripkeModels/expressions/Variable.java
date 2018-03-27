package mathlogic.kripkeModels.expressions;

public class Variable extends AbstractExpression {
    private final String varName;

    public Variable(String varName) {
        this.varName = varName;
    }

    @Override
    public void toStringRec(StringBuilder result) {
        result.append(varName);
    }

    @Override
    public Expression clone() {
        return new Variable(varName);
    }

}
