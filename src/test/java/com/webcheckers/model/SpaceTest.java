package com.webcheckers.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;


/**
 * Created by Potatoes on 3/20/2018.
 */
public class SpaceTest {

    //the test space
    private Space CuT;
    //the test piece
    private Piece testPiece;

    //constructing a mock of the piece class
    @Before
    public void initializePiece() throws Exception{
        this.testPiece = mock(Piece.class);
    }

    /**
     * Constructs a space and checks if it is a space, pretty barebones I know
     */
    @Test
    public void checkSpace(){
        //passing some arbitray information
        CuT = new Space(null,true,4);
        assertTrue( CuT instanceof Space);
    }

    /**
     * checks the CellID is constructed properly
     */
    @Test
    public void checkCellID(){
        //some arbitrary cell ID
        int cellID = 2;
        CuT = new Space(null,true,cellID);
        assertEquals(CuT.getCellIdx(), cellID);
    }

    /**
     * checks the piece is stored properly
     */
    @Test
    public void checkPiece(){
        CuT = new Space(testPiece,true,4);
        assertEquals(testPiece, CuT.getPiece());
        CuT.setPiece(testPiece);
        assertEquals(testPiece, CuT.getPiece());
    }


    /**
     * checks the piece is removed properly ( will be set to null )
     */
    @Test
    public void removePiece(){
        CuT = new Space(testPiece,true,4);
        assertNotNull(null, CuT.getPiece());
    }

    @Test
    public void checkValidMove(){
        //first checking if a white space will be considered valid
        CuT = new Space(null,false,5);
        assertFalse( CuT.isValid() );
        //now checking if is valid with a piece on the space
        CuT = new Space(testPiece,true,4);
        assertFalse( CuT.isValid() );
    }

}