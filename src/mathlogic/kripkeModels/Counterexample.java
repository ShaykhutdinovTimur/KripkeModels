package mathlogic.kripkeModels;


import mathlogic.kripkeModels.expressions.Expression;
import mathlogic.kripkeModels.expressions.LogicParser;

import java.io.*;

public class Counterexample {
    private final String inputFile;
    private final String outputFile;

    private Counterexample(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public static void main(String[] args) {
        new Counterexample("tin.in", "tout.out").run();
    }

    private void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String formula = reader.readLine();
            Expression expr = LogicParser.getInstance().parse(formula);
            Scale model = (new KripkeBuilder(expr)).tryToBuild();
            if (model == null || !model.isContainsProve()) {
                writer.write("Формула общезначима");
            } else {
                writer.write(model.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
