package calculator;

import calculator.impl.ReversePolishNotationCalculatorService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CalculatorServiceTest {

    private static final String FILE_NAME = "src/main/resources/TestExecution.txt";
    private static final String READ_EXPRESSION = "2+7+8+3*11";
    private static final BigDecimal RESULT = new BigDecimal(1);

    @Rule
    public OutputExecutionToFileTestRule outTestRule = new OutputExecutionToFileTestRule(FILE_NAME);

    @Mock
    private CalculatorReader calculatorReader;
    @InjectMocks
    private ReversePolishNotationCalculatorService calculatorService;
    @Mock
    private Calculator calculator;
    @Mock
    private CalculatorWriter calculatorWriter;

    @Test
    public void testCalculateAmdWrite() {
        when(calculatorReader.readExpression()).thenReturn(READ_EXPRESSION);
        when(calculator.calculate(READ_EXPRESSION)).thenReturn(RESULT);
        calculatorService.calculateAndWriteResult();
        verify(calculatorReader, times(1)).readExpression();
        verify(calculatorWriter, times(1)).writeResult(String .valueOf(RESULT));
        verify(calculator, times(1)).calculate(READ_EXPRESSION);
    }

    @Test(expected = RuntimeException.class)
    public void testCalculateAmdWriteException() {
        when(calculatorReader.readExpression()).thenThrow(RuntimeException.class);
        calculatorService.calculateAndWriteResult();
        verify(calculatorReader, times(1)).readExpression();
    }
}
