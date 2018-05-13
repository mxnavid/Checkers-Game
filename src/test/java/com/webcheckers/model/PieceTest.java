package com.webcheckers.model;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Navid Nafiuzzaman <mxn4459@rit.edu> in 3/21/18
 */
class PieceTest {
    private Piece place;

    /**
     * Initialize the mock piece.
     * @throws Exception
     */
    @Before
    public void initializaPiece() throws Exception{
        this.place = mock(Piece.class);

    }

    /**
     * Test the Type functionality of the Piece
     */
    @Test
    void getType() {
        place = new Piece(Piece.TYPE.KING, Piece.COLOR.RED);
        assertEquals(place.getType(), Piece.TYPE.KING);
    }

    /**
     * Test the color funcitonality of the piece.
     */
    @Test
    void getColor() {
        place = new Piece(Piece.TYPE.KING, Piece.COLOR.RED);
        assertEquals(place.getColor(), Piece.COLOR.RED);
    }

    /**
     * Tests the Validity functionality of the Piece
     */
    @Test
    void getValidity() {
        place = new Piece(Piece.TYPE.KING, Piece.COLOR.RED);
        assertEquals(place.getColor(), Piece.COLOR.RED);
    }

    /**
     * Test a piece gets converted to a King
     */
    @Test
    public void testKingMe(){

        place = new Piece(Piece.TYPE.SINGLE,Piece.COLOR.RED);
        place.kingMe();
        assertEquals(place.getType(),Piece.TYPE.KING);

    }
}