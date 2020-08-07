package calculator;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OutputExecutionToFileTestRule implements TestRule {

    private static final String SUCCESSFUL_WORK = "Test full passed from %s class";
    private static final String FAIL_WORK = "Test didn't passed from %s class, cause : %s";
    private final String fileName;

    public OutputExecutionToFileTestRule(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                String result = String.format(SUCCESSFUL_WORK, description.getClassName());
                try {
                    base.evaluate();
                    writeToFile(fileName, result);
                } catch (Exception e) {
                    result = String.format(FAIL_WORK, description.getClassName(), e.getMessage());
                    writeToFile(fileName, result);
                }
            }
        };
    }

    private void writeToFile(String filename, String result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(result + "\n");
        } catch(IOException e) {
            System.err.println("Didn't write result into log file");
        }
    }
}
