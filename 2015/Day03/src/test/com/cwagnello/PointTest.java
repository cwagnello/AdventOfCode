package com.cwagnello;

import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cwagnello
 */
public class PointTest {
  
    @Before
    public void setUp() {
    }
    
    /**
     * Test of moveUp method, of class Point.
     */
    @Test
    public void testMoveUp() {
        System.out.println("moveUp");
        Point instance = new Point();
        Point expResult = new Point(0, 1);
        Point result = instance.moveUp();
        assertEquals(expResult, result);
    }

    /**
     * Test of moveDown method, of class Point.
     */
    @Test
    public void testMoveDown() {
        System.out.println("moveDown");
        Point instance = new Point();
        Point expResult = null;
        Point result = instance.moveDown();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveLeft method, of class Point.
     */
    @Test
    public void testMoveLeft() {
        System.out.println("moveLeft");
        Point instance = new Point();
        Point expResult = null;
        Point result = instance.moveLeft();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveRight method, of class Point.
     */
    @Test
    public void testMoveRight() {
        System.out.println("moveRight");
        Point instance = new Point();
        Point expResult = null;
        Point result = instance.moveRight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Point.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Point instance = new Point(1,-2);
        Point result = new Point(1, -2);
        assertTrue(instance.equals(result));
    }

    /**
     * Test of hashCode method, of class Point.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Point instance = new Point(1,-21);
        Point result = new Point(61, -21);
        assertTrue(instance.hashCode() == result.hashCode());
    }
    
    @Test
    public void testAddingSamePointToSet() {
        Point instance = new Point(2, 52);
        Point instance2 = new Point(2, 52);
        Set<Point> hash = new HashSet<>();
        hash.add(instance);
        hash.add(instance);
        hash.add(instance2);
        hash.add(instance2);
        assertTrue(hash.size() == 1);
    }
}
