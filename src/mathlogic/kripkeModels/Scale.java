package mathlogic.kripkeModels;

import mathlogic.kripkeModels.expressions.Expression;
import mathlogic.kripkeModels.expressions.Variable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Scale {
    private final List<HashSet<Variable>> variables;
    private final List<FullPair> pairs;
    private final List<List<Integer>> children;
    private final List<Boolean> prove;
    private final List<Boolean> redundant;
    private boolean containsProve;

    Scale(List<FullPair> pairs, List<List<Integer>> children, Expression task) {
        this.children = children;
        this.variables = new ArrayList<>();
        this.prove = new ArrayList<>();
        this.redundant = new ArrayList<>();
        this.pairs = pairs;
        this.containsProve = false;
        for (FullPair pair : pairs) {
            if (pair != null) {
                HashSet<Variable> variableHashSet = new HashSet<>();
                for (Expression expression : pair.getR()) {
                    if (expression instanceof Variable) {
                        variableHashSet.add((Variable) expression);
                    }
                }
                variables.add(variableHashSet);
                prove.add(pair.getS().contains(task));
                containsProve |= pair.getS().contains(task);
                redundant.add(false);
            } else {
                variables.add(new HashSet<>());
                prove.add(false);
                redundant.add(true);
            }
        }
    }

    public boolean isContainsProve() {
        return containsProve;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < variables.size(); i++) {
            if (!redundant.get(i)) {
                if (prove.get(i)) {
                    result.append("(").append(i).append(")");
                } else {
                    result.append(" ").append(i).append(" ");
                }
                result.append("  Vars: ");
                for (Variable variable : variables.get(i)) {
                    result.append(variable.toString()).append(",");
                }
                result.append(" Children: ");
                for (Integer j : children.get(i)) {
                    result.append(j).append(" ");
                }

                result.append("R { ");
                for (Expression e : pairs.get(i).getR()) {
                    result.append(e.toString()).append(" ");
                }
                result.append("} S { ");
                for (Expression e : pairs.get(i).getS()) {
                    result.append(e.toString()).append(" ");
                }

                result.append("}\n");
            }
        }
        return result.toString();
    }

}