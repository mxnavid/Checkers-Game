package com.webcheckers.model;

import org.junit.Before;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@Tag("Model-tier")
class MoveTest {

    private Position start;
    private Position end;
    private Move move;

    @Test
    /**
     * Test that a move has been instantiated correctly
     */
    public void testMoveInstantiation(){

        start = new Position(1,2);
        end = new Position(2,3);
        move = new Move(start,end);

        assertNotNull(move);

    }

    /**
     * Test a simple move on a red piece
     */
    @Test
    public void testSimpleMoveREDPass(){

        start = new Position(5,0);
        end = new Position(4,1);
        move = new Move(start,end);

        Board board = Board.initializeBoard();
        assertEquals(new Message (Message.Type.info, String.format("%s: valid single move.",Piece.COLOR.RED)),
                MoveRule.moveStatus(mock(Game.class),board,move));


    }

    /**
     * Test a simple move on a white piece
     */
    @Test
    public void testSimpleMoveWHITEPass(){

        start = new Position(5,3);
        end = new Position(4,2);
        move = new Move(start,end);

        Board board = Board.reverseBoard(Board.initializeBoard());
        assertEquals(new Message (Message.Type.info, String.format("%s: valid single move.",Piece.COLOR.WHITE)),
                MoveRule.moveStatus(mock(Game.class),board,move));

    }


    /**
     * Test a jump forward move -> fail
     */
    @Test
    public void testJumpMoveREDFail(){

        start = new Position(5,0);
        end = new Position(3,2);
        move = new Move(start,end);

        Board board = Board.initializeBoard();
        assertEquals(Message.Type.error,MoveRule.moveStatus(mock(Game.class),board,new Move(start,end)).getType());

    }


    /**
     * Test a jump forward move -> fail
     */
    @Test
    public void testJumpMoveWHITEFail(){

        start = new Position(5,3);
        end = new Position(3,1);

        Board board = Board.reverseBoard(Board.initializeBoard());
        assertEquals(Message.Type.error, MoveRule.moveStatus(mock(Game.class),board, new Move(start,end)).getType());


    }

}