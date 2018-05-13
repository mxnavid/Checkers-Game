package com.webcheckers.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Representation of a checker board
 */
public class    Board implements Iterable<Row>{

    public static final int SIZE = 8;

    private List<Row> board;
    private Move lastMove;
    private Move lastJump;

    /**
     * Initializes the Game Board
     */
    public Board(){
        this.board = new ArrayList<>();
    }

    /**
     * Initialize board with rows
     * @return list of rows
     */
    public static Board initializeBoard(){
        Board newboard = new Board();

        for(int row=0; row<SIZE; row++) {
            Row currRow = new Row(row);
            currRow.setRow(Row.initializeRow(row));
            newboard.board.add(currRow);
        }
        return newboard;

    }

    /**
     * Orient the board in the player's view.
     * @param b: takes the Board as Input
     * @return : Return the oriented board to the player
     */
    public static Board orientBoard(Board b){
        Board newBoard = new Board();

        for(int r=SIZE-1; r>=0; r--){
            newBoard.board.add(new Row(7-r));
        }

        for(int r=SIZE-1; r>=0; r--){
            for(int s=SIZE-1; s>=0; s--){
                Space space = b.board.get(r).row.get(s);
                newBoard.board.get(7-r).row.add(space);
            }
        }

        return newBoard;
    }


    /**
     * Reverse the rows of the board for white player's moves
     * @param b the current board
     * @return new board with reversed rows
     */
    public static Board reverseBoard(Board b){
        Board newBoard = Board.copyBoard(b);
        Collections.reverse(newBoard.board);
        return newBoard;

    }

    /**
     * Iterator for the board
     * @return Row Iterator
     */
    public Iterator<Row> iterator(){
        return board.iterator();
    }


    /**
     * grabs the board
     * @return the list of list of spaces
     */
    public List<Row> getBoard() {
        return board;
    }

    private void addRow(Row row ){
        board.add(row);
    }


    public Row getRow(int number){
        return this.board.get(number);
    }

    /**
     * Creates a copy of a board
     * @param board The board to copy
     * @return A copy of the board with a different memory address
     */
    public static Board copyBoard(Board board){

        Board boardCopy = new Board();
        for( Row row : board.getBoard() ){
            Row rowCopy = new Row(row.getIndex());
            rowCopy.setRow( new ArrayList<>() );
            for( Space space : row.getRow()) {
                //System.out.println("in loop");
                if(space.getPiece() != null) {
                    Piece pieceCopy = Piece.copyPiece(space.getPiece());
                    Space spaceCopy = new Space(pieceCopy, space.isBlack(), space.getCellIdx());
                    rowCopy.getRow().add(spaceCopy);
                }
                //otherwise initialize piece as null
                else {
                    Space spaceCopy = new Space(null, space.isBlack(), space.getCellIdx());
                    rowCopy.getRow().add(spaceCopy);
                }

            }

            boardCopy.addRow(rowCopy);
        }

        boardCopy.setLastMove(board.lastMove);
        boardCopy.setLastJump(board.lastJump);
        System.out.println();
        return boardCopy;
    }

    public void movePiece( Move move ){
        //setting the piece to null at the start position
        Piece piece = board.get(move.getStart().getRow()).getSpace(move.getStart().getCell()).getPiece();
        //now setting the piece at that location to null
        board.get(move.getStart().getRow()).getSpace(move.getStart().getCell()).setPiece(null);
        //setting the space to contain the piece at it's new destination
        board.get(move.getEnd().getRow()).getSpace(move.getEnd().getCell()).setPiece(piece);
    }

    public void destroyPiece( Space space ){
        space.setPiece(null);
    }

    public Move getLastMove() {
        return lastMove;
    }

    public void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }

    public Move getLastJump() {
        return lastJump;
    }

    public void setLastJump(Move lastJump) {
        this.lastJump = lastJump;
    }

}
