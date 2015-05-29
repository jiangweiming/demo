package org.skywing.code.demo.unittest.stage2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.skywing.code.demo.unittest.calc.Calculator;


/**
 * My unit test stage 2 with using junit, a unit test framework.
 * @author Jwm
 *
 */
public class CalculatorTest {
    private Calculator calc;
    
    @BeforeClass
    public static void setUpClass() {
        // We can initialize some expensive resource in here.
        System.out.println("Init for test class ...");
    }
    
    @Before
    public void setUp() {
        System.out.println("#################### setUp called ####################");
        this.calc = new Calculator();
    }
    
    @Ignore
    @Test
    public void testAdd() {
        assertEquals(1, this.calc.add(1, 0));
        assertEquals(2, this.calc.add(1, 1));
        assertEquals(0, this.calc.add(1, -1));
    }
    
    @Test
    public void testMinus() {
        assertEquals(1, this.calc.minus(1, 0));
        assertEquals(0, this.calc.minus(1, 1));
        assertEquals(2, this.calc.minus(1, -1));
    }
    
    @Test
    public void testMultiply() {
        assertEquals(0, this.calc.multiply(1, 0));
        assertEquals(1, this.calc.multiply(1, 1));
        assertEquals(-1, this.calc.multiply(1, -1));
    }
    
    @Test
    public void testDivide() {
        assertEquals(1, this.calc.divide(1, 1));
        assertEquals(0, this.calc.divide(1, 2));
        assertEquals(-1, this.calc.divide(1, -1));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testDivideEx() {
        this.calc.divide(1, 0);
    }
    
    @After
    public void tearDown() {
        this.calc = null;
        System.out.println("#################### tearDown called ####################");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("destory for test class ...");
    }
}
