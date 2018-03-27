package mathlogic.kripkeModels.expressions;

import java.util.HashSet;

public interface Expression {
    void toStringRec(StringBuilder result);

    String toString();

    int hashCode();

    Expression clone();

    HashSet<Expression> getSubExpressions();

    boolean equals(Object o);
}
