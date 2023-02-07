package com.shashankbhat.notesapp.test;


import junit.framework.TestCase;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertSame;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by SHASHANK BHAT on 11-Sep-20.
 */


public class UnitTest {

    @Test
    public void test_AddMethod(){

        assertEquals(2, Calculator.add(1, 1));
        assertNotEquals(0, Calculator.add(1, 1));

        assertSame(Calculator.add(2, 8), Calculator.add(3, 7));
        assertNotSame(new String("ABC"), new String("ABC"));

        assertFalse(false);
        assertTrue(true);

        assertNotNull(0);
        assertNull(null);
    }
}