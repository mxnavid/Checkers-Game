package com.webcheckers.model;

import java.util.Arrays;

/**
 * Position class is concerned class about position
 */
public class Position {

    private int row; //row
    private int cell; //cell /column

    /**
     * Constructor for the position
     * @param row : Row of the board
     * @param col : cell of the board
     */
    public Position(int row, int col){
        this.row = row;
        this.cell = col;
    }

    /**
     * Getter for the row
     * @return : integer value of the row
     */
    public int getRow() {
        return row;
    }


    /**
     * Getter for the cell
     * @return : integer value for the cell position
     */
    public int getCell() {
        return cell;
    }


    /**
     * Equals method to check if the position are equal
     * @param obj : Passed object
     * @return boolean - True or false
     */
    @Override
    public boolean equals(Object obj){
        if( obj instanceof Position){
            Position that = (Position) obj;
            return that.row == this.row && this.cell == that.cell;
        }
        return false;
    }

    /**
     * ToString for the position
     * @return : String
     */
    @Override
    public String toString(){
        return String.format("(Row: %d, Cell: %d)\n",this.row,this.cell);
    }


    @Override
    public int hashCode(){
        return Arrays.hashCode(new int[]{row,cell});
    }
}
