package mathlogic.kripkeModels;

import mathlogic.kripkeModels.expressions.*;

import java.util.HashSet;
import java.util.List;

public class FullPair {
    private final HashSet<Expression> R, S;

    FullPair(List<Expression> F, int bitmask) {
        R = new HashSet<>();
        S = new HashSet<>();
        for (int i = 0; i < F.size(); i++) {
            if ((bitmask & (1 << i)) != 0) {
                R.add(F.get(i));
            } else {
                S.add(F.get(i));
            }
        }
    }

    boolean isWeakCorrect() {
        for (Expression expression : R) {
            if (expression instanceof Conjunction) {
                if (S.contains(((Conjunction) expression).getA()) || S.contains(((Conjunction) expression).getB())) {
                    return false;
                }
            }
            if (expression instanceof Disjunction) {
                if (S.contains(((Disjunction) expression).getA()) && S.contains(((Disjunction) expression).getB())) {
                    return false;
                }
            }
        }
        for (Expression expression : S) {
            if (expression instanceof Conjunction) {
                if (R.contains(((Conjunction) expression).getA()) && R.contains(((Conjunction) expression).getB())) {
                    return false;
                }
            }
            if (expression instanceof Disjunction) {
                if (R.contains(((Disjunction) expression).getA()) || R.contains(((Disjunction) expression).getB())) {
                    return false;
                }
            }
        }
        return isDescendantOf(this);
    }

    boolean isDescendantOf(FullPair ancestor) {
        for (Expression expression : ancestor.R) {
            if (!R.contains(expression)) {
                return false;
            }
            if (expression instanceof Negative) {
                if (R.contains(((Negative) expression).getA())) {
                    return false;
                }
            }
            if (expression instanceof Implication) {
                Implication implication = (Implication) expression;
                if (R.contains(implication.getA()) && S.contains(implication.getB())) {
                    return false;
                }
            }
        }
        return true;
    }

    public HashSet<Expression> getR() {
        return R;
    }

    public HashSet<Expression> getS() {
        return S;
    }

}
