package com.webcheckers.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;


@Tag("Model-tier")
class RowTest {
    private Row row;


    @BeforeEach
    void initializeRow(){
        List <Space> row = new ArrayList<>();
        final Row cut = new Row (2);
        assertTrue(cut.getIndex() == 2);
    }

    @Test
    void getIndex(){
        row = new Row(7);
        assertTrue(row.getIndex() == 7);
    }

    @Test
    // Index 0 Col 0
    void testSpacePlacement_I_0B(){
        row = new Row(0);
        List <Space> row = new ArrayList<>();
        int col = 0;
        assertTrue(row.add(new Space(null, false, col)));
    }
    // Index 1 Col 0
    @Test
    void testSpacePlacement_I_1B(){
        row = new Row(1);
        List <Space> row = new ArrayList<>();
        int col = 0;
        assertTrue(row.add(new Space(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE),true,col)));
    }

    // Index 2 Col 0
    @Test
    void testSpacePlacement_I_2B(){
        row = new Row(2);
        List <Space> row = new ArrayList<>();
        int col = 0;
        assertTrue(row.add(new Space(null, false, col)));
    }

    // Index 3 Col 0
    @Test
    void testSpacePlacement_I_3B(){
        row = new Row(3);
        List <Space> row = new ArrayList<>();
        int col = 0;
        assertTrue(row.add(new Space(null, true, col)));
    }

    // Index 4 Col 0
    @Test
    void testSpacePlacement_I_4B(){
        row = new Row(4);
        List <Space> row = new ArrayList<>();
        int col = 0;
        assertTrue(row.add(new Space(null, false, col)));
    }

    // Index 5 Col 0
    @Test
    void testSpacePlacement_I_5B(){
        row = new Row(5);
        List <Space> row = new ArrayList<>();
        int col = 0;
        assertTrue(row.add(new Space(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED),true,col)));
    }

    // Index 6 Col 0
    @Test
    void testSpacePlacement_I_6B(){
        row = new Row(6);
        List <Space> row = new ArrayList<>();
        int col = 0;
        assertTrue(row.add(new Space(null, false, col)));
    }

    // Index 7 Col 0
    @Test
    void testSpacePlacement_I_7B(){
        row = new Row(7);
        List <Space> row = new ArrayList<>();
        int col = 0;
        assertTrue(row.add(new Space(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED),true,col)));
    }

    // Index 0 Col 1
    @Test
    void testSpacePlacement_I_0(){
        row = new Row(0);
        List <Space> row = new ArrayList<>();
        int col = 1;
        assertTrue(row.add(new Space(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE),true,col)));
    }

    // Index 1 Col 1
    @Test
    void testSpacePlacement_I_1(){
        row = new Row(1);
        List <Space> row = new ArrayList<>();
        int col = 1;
        assertTrue(row.add(new Space(null, false, col)));
    }

    // Index 2 Col 1
    @Test
    void testSpacePlacement_I_2(){
        row = new Row(2);
        List <Space> row = new ArrayList<>();
        int col = 1;
        assertTrue(row.add(new Space(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE),true,col)));
    }

    // Index 3 Col 1
    @Test
    void testSpacePlacement_I_3(){
        row = new Row(3);
        List <Space> row = new ArrayList<>();
        int col = 1;
        assertTrue(row.add(new Space(null, false, col)));
    }

    // Index 4 Col 1
    @Test
    void testSpacePlacement_I_4(){
        row = new Row(4);
        List <Space> row = new ArrayList<>();
        int col = 1;
        assertTrue(row.add(new Space(null, true, col)));
    }

    // Index 5 Col 1
    @Test
    void testSpacePlacement_I_5(){
        row = new Row(5);
        List <Space> row = new ArrayList<>();
        int col = 1;
        assertTrue(row.add(new Space(null, false, col)));
    }

    // Index 6 Col 1
    @Test
    void testSpacePlacement_I_6(){
        row = new Row(6);
        List <Space> row = new ArrayList<>();
        int col = 1;
        assertTrue(row.add(new Space(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED),true,col)));
    }

    // Index 7 Col 1
    @Test
    void testSpacePlacement_I_7(){
        row = new Row(7);
        List <Space> row = new ArrayList<>();
        int col = 1;
        assertTrue(row.add(new Space(null, false, col)));
    }
}
