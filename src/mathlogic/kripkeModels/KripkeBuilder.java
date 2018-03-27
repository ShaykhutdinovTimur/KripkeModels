package mathlogic.kripkeModels;

import mathlogic.kripkeModels.expressions.*;

import java.util.*;

class KripkeBuilder {
    private final Expression expr;
    private final int subExpressionsCount;
    private final List<Expression> subExpressions;
    private final List<FullPair> pairs;
    private final List<List<Integer>> children;

    KripkeBuilder(Expression expr) {
        this.expr = expr;
        subExpressions = new ArrayList<>();
        subExpressions.addAll(expr.getSubExpressions());
        subExpressionsCount = subExpressions.size();
        pairs = new ArrayList<>();
        children = new ArrayList<>();
    }

    Scale tryToBuild() {
        for (int mask = 0; mask < (1 << subExpressionsCount); mask++) {
            FullPair pair = new FullPair(subExpressions, mask);
            if (pair.isWeakCorrect()) {
                pairs.add(pair);
            }
        }
        for (int i = 0; i < pairs.size(); i++) {
            children.add(new ArrayList<>());
        }

        for (int i = 0; i < pairs.size(); i++) {
            for (int j = 0; j < pairs.size(); j++) {
                if (i != j) {
                    if (pairs.get(i).isDescendantOf(pairs.get(j))) {
                        children.get(j).add(i);
                    }
                }
            }
        }
        Scale result = null;
        if (checkCorrectness()) {
            result = new Scale(pairs, children, expr);
        }
        return result;
    }

    private boolean checkCorrectness() {
        int pairCount = pairs.size();
        int newLeaf = checkChildren();
        while ((newLeaf != -1) && pairCount > 0) {
            int finalNewLeaf = newLeaf;
            for (int i = 0; i < pairs.size(); i++) {
                children.get(i).removeIf(x -> x == finalNewLeaf);
            }
            pairs.set(newLeaf, null);
            newLeaf = checkChildren();
            pairCount--;
        }
        return (pairCount > 0);
    }

    private int checkChildren() {
        int result = -1;
        for (int i = 0; i < pairs.size(); i++) {
            if (pairs.get(i) != null && !checkChildCorrectness(i)) {
                result = i;
                break;
            }
        }
        return result;
    }

    private boolean checkChildCorrectness(int i) {
        for (Expression expression : pairs.get(i).getS()) {
            boolean sAdditionalCorrectness = false;
            if (expression instanceof Negative) {
                Negative negative = (Negative) expression;
                for (int j = 0; j < children.get(i).size(); j++) {
                    if (pairs.get(children.get(i).get(j)).getR().contains(negative.getA())) {
                        sAdditionalCorrectness = true;
                        break;
                    }
                }
                if (pairs.get(i).getR().contains(negative.getA())) {
                    sAdditionalCorrectness = true;
                }
                if (!sAdditionalCorrectness) {
                    return false;
                }
            }
            if (expression instanceof Implication) {
                Implication implication = (Implication) expression;
                for (int j = 0; j < children.get(i).size(); j++) {
                    if (pairs.get(children.get(i).get(j)).getR().contains(implication.getA()) &&
                            pairs.get(children.get(i).get(j)).getS().contains(implication.getB())) {
                        sAdditionalCorrectness = true;
                        break;
                    }
                }
                if (pairs.get(i).getR().contains(implication.getA()) &&
                        pairs.get(i).getS().contains(implication.getB())) {
                    sAdditionalCorrectness = true;
                }
                if (!sAdditionalCorrectness) {
                    return false;
                }
            }
        }
        return true;
    }
}


