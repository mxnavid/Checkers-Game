package com.webcheckers.model;

/**
 * Representation of a space on the game board
 */
public class Space {

    private Piece piece;    //the piece occupying this spot
    private boolean isBlack; //boolean repesenting whether the given position is black or not
    private int cellIdx; //cell index

    /**
     * Constructor for the Space
     * @param piece : Piece of the board
     * @param isBlack : black color or not
     * @param cellIdx : Cell Index
     */
    Space(Piece piece, boolean isBlack, int cellIdx){
        this.piece = piece;
        this.isBlack = isBlack;
        this.cellIdx = cellIdx;
    }

    public boolean isBlack() {
        return isBlack;
    }

    // set piece on tile
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Gets the cellIDX of the space
     * @return the int cellIdx
     */
    public int getCellIdx() {
        return cellIdx;
    }

    /**
     * space is valid if black spot and no piece
     * @return space is black and no piece is on that space
     */
    public boolean isValid(){
        return isBlack && this.piece == null;
    }

    /**
     * Gets the piece at the space
     * @return null if no piece, otherwise the piece
     */
    public Piece getPiece(){
        return piece;
    }

    @Override
    public String toString(){


        return String.format("Space: index[%d], isBlack[%b], Piece[%s], ",cellIdx,isBlack,piece);

    }
}
