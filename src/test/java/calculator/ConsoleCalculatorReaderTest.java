package calculator;

import calculator.impl.ConsoleCalculatorReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleCalculatorReaderTest {

    private static final String FILE_NAME = "src/main/resources/TestExecution.txt";

    private static final String expression = "5*3+8/2+(8-3)";

    @Test
    public void testReadFromConsole() {
        ConsoleCalculatorReader consoleCalculatorReader = mock(ConsoleCalculatorReader.class);
        when(consoleCalculatorReader.readExpression()).thenCallRealMethod();
        System.setIn(new ByteArrayInputStream(expression.getBytes()));
        String actual = consoleCalculatorReader.readExpression();
        verify(consoleCalculatorReader, times(1)).readExpression();
        assertEquals(expression, actual);
    }
}
