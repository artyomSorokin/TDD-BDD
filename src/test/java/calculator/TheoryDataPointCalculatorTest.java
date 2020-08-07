package calculator;

import calculator.impl.ReversePolishNotationCalculator;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TheoryDataPointCalculatorTest {

    private static final int SCALE = 1;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    private Calculator calculatorRPN;

    @Before
    public void init() {
        calculatorRPN = new ReversePolishNotationCalculator(SCALE, ROUNDING_MODE);
    }

    @DataPoints
    public static String[] inputExpression() {
        return new String[]{"1+5", "8/2", "7*4+4", "32+5*2"};
    }

    @Theory
    public void calculatorTest(String inputExpression) {
        BigDecimal actual = calculatorRPN.calculate(inputExpression);
        assertNotNull(actual);
        assertTrue(actual.doubleValue() > 0);
        assertEquals(actual.doubleValue()%2, 0.0, 0);
    }
}
