package mathlogic.kripkeModels.expressions;

import java.util.HashSet;

public abstract class AbstractExpression implements Expression {

    public HashSet<Expression> getSubExpressions() {
        HashSet<Expression> subExpressions = new HashSet<>();
        if (this instanceof Implication) {
            subExpressions.addAll(((Implication) this).getA().getSubExpressions());
            subExpressions.addAll(((Implication) this).getB().getSubExpressions());
        } else if (this instanceof Disjunction) {
            subExpressions.addAll(((Disjunction) this).getA().getSubExpressions());
            subExpressions.addAll(((Disjunction) this).getB().getSubExpressions());
        } else if (this instanceof Conjunction) {
            subExpressions.addAll(((Conjunction) this).getA().getSubExpressions());
            subExpressions.addAll(((Conjunction) this).getB().getSubExpressions());
        } else if (this instanceof Negative) {
            subExpressions.addAll(((Negative) this).getA().getSubExpressions());
        }
        subExpressions.add(this);
        return subExpressions;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        toStringRec(result);
        return result.toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public abstract Expression clone();

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }
}
