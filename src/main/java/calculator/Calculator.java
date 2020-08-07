package calculator;

import java.math.BigDecimal;

/**
 * Calculator interface for implementing a concrete calculator.
 */
public interface Calculator {

    /**
     * Use this method to calculate an expression.
     *
     * @param expression as a string.
     * @return result as BigDecimal for specified expression.
     * @throws ArithmeticException if expression contains errors.
     * @throws IllegalArgumentException if expression contains unsupported operations or non-mathematical symbols.
     */
    BigDecimal calculate(String expression);

}
