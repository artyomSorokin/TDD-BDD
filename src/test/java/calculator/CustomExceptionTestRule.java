package calculator;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static org.junit.Assert.assertEquals;

public class CustomExceptionTestRule implements TestRule {

    private Exception expectedException;

    @Override
    public Statement apply(Statement base, Description description) {
       return new Statement() {
           @Override
           public void evaluate() throws Throwable {
               try {
                   base.evaluate();
               } catch (Exception e) {
                   checkException(e);
               }
           }
       };
    }

    private void checkException(Exception e) throws Exception {
        if (e.getClass().getSimpleName().equals(expectedException.getClass().getSimpleName())) {
            assertEquals(expectedException.getMessage(), e.getMessage());
        }
        else {
            throw e;
        }
    }

    public Exception getExpectedException() {
        return expectedException;
    }

    public void setExpectedException(Exception expectedException) {
        this.expectedException = expectedException;
    }
}
