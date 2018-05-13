package com.webcheckers.model;

import java.util.Arrays;

/**
 * Move class for the game
 */
public class Move {

    private Position start; // Starting Position
    private Position end; //Ending Position

    /**
     * Constructor for the Move
     * @param start : Starting Position
     * @param end Ending Position
     */
    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }


    /**
     * get started with the start position
     * @return : Position
     */
    public Position getStart() {
        return start;
    }


    /**
     * Get the end position
     * @return Position
     */
    public Position getEnd() {
        return end;
    }


    /**
     * String representation of a Move
     * @return a String
     */
    public String toString(){
        return "Move: [ " + start + ", " + end + " ]";
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof Move ){
            Move move = (Move)obj;
            System.out.println("hello papa");
            return ( this.start.equals(move.start) && this.end.equals(move.end) );
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode(new Object[]{start,end});
    }
}
