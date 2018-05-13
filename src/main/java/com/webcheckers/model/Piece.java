package com.webcheckers.model;

import java.util.Arrays;

/**
 * Representation of a checker piece
 */
public class Piece {

    //type of piece, signed or king
    public enum TYPE {
        SINGLE, KING
    }

    //if the color of the piece is red/white
    public enum COLOR {
        RED, WHITE
    }

    //enum VALIDITY{VALID, INVALID;} //valid or invalid enum

    //variables
    private TYPE type; // type
    private COLOR color; //color
    //private VALIDITY validity; //validity


    /**
     * Constructs a piece with type and color
     *
     * @param type  the type of piece SINGLE/KING
     * @param color the color of piece RED/WHITE
     */
    public Piece(TYPE type, COLOR color) {
        this.type = type;
        this.color = color;
        //this.validity = validity;
    }

    /**
     * gets type of piece
     *
     * @return SINGLE/KING
     */
    public TYPE getType() {
        return type;
    }

    /**
     * gets color of piece
     *
     * @return RED/WHITE
     */
    public COLOR getColor() {
        return color;
    }


    /**
     * Piece reaches end of board for it to be king
     */
    public void kingMe(){
        if(this.type == TYPE.SINGLE || this.type == TYPE.KING)
            this.type = TYPE.KING;
    }

    /**
     * constructs a copy of the piece passed
     *
     * @param piece the piece to copy
     * @return the new piece
     */
    public static Piece copyPiece(Piece piece) {
        return new Piece(piece.getType(), piece.getColor());
    }

    @Override
    public String toString(){

        return color+" "+type;

    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  Piece){
            return this.color == ((Piece) obj).color && this.type == ((Piece) obj).type;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode(new int[]{color.hashCode(),type.hashCode()});
    }
}