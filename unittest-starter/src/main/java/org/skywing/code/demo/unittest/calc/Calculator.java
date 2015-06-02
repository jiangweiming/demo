package org.skywing.code.demo.unittest.calc;

import com.google.common.base.Preconditions;

/**
 * A simple calculator emulator.
 * @author Jwm
 * @since 1.7
 */
public class Calculator {    
    public int add(int first, int second) {
        return first + second;
    }
    
    public int minus(int first, int second) {
        return first - second;
    }
    
    public int multiply(int first, int second) {
        return first * second;
    }
    
    public int divide(int first, int second) {
        Preconditions.checkArgument(second != 0, "Parameter 'second' can not be zero.");
        
        return first / second;
    }
    
    /**
     * My unit test stage 1.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("####################################################");
        System.out.println("#            My unit test first stage              #");
        System.out.println("####################################################");
        
        Calculator calc = new Calculator();

        // test add
        System.out.println("calc.add(1 + 0)=" + calc.add(1, 0));
        System.out.println("calc.add(1 + 1)=" + calc.add(1, 1));
        System.out.println("calc.add(1 + (-1))=" + calc.add(1, -1));
        
        // test minus
        System.out.println("calc.minus(1 - 0)=" + calc.minus(1, 0));
        System.out.println("calc.minus(1 - 1)=" + calc.minus(1, 1));
        System.out.println("calc.minus(1 - (-1))=" + calc.minus(1, -1));
        
        // test multiply
        System.out.println("calc.multiply(1 * 0)=" + calc.multiply(1, 0));
        System.out.println("calc.multiply(1 * 1)=" + calc.multiply(1, 1));
        System.out.println("calc.multiply(1 * (-1))=" + calc.multiply(1, -1));
        
        // test divide
        System.out.println("calc.divide(1 / 1)=" + calc.divide(1, 1));
        System.out.println("calc.divide(1 / (-1))=" + calc.divide(1, -1));
        System.out.println("calc.divide(1 / 0)=" + calc.divide(1, 0));
        
        System.out.println("\n");
    }
}
