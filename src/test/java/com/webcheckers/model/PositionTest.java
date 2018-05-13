package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Position class
 */
class PositionTest {

    private int row = 4;
    private int cell = 5;

    Position pos;

    @BeforeEach
    void setup(){

        pos = new Position(row,cell);
    }


    @Test
    void testPosition(){

        assertNotNull(pos,"Position should not be null.");
    }

    @Test
    void testRow() {

        assertEquals(row,pos.getRow());
    }

    @Test
    void testCell() {

        assertEquals(cell,pos.getCell());
    }

    @Test
    void testEquals(){

        Position pos2 = new Position(4,5);
        assertTrue(pos.equals(pos2));
        pos2 = new Position(1,2);
        assertFalse(pos.equals(pos2));
    }

    @Test
    void testToString(){
        assertEquals(pos.toString(),"(Row: 4, Cell: 5)\n");
    }

}