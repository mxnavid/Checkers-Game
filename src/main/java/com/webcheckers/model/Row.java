package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Row functionality of the board.
 */
public class Row implements Iterable<Space>{

    List<Space> row;

    int index;  //row index of the board (0-7)

    /**
     * Constructor for the row
     * @param index the index of row in terms of an array
     */
    public Row(int index){
        this.index = index;
        this.row = new ArrayList<>();
    }


    /**
     * Initialize a row with spaces
     * @return a list of spaces
     */
    public static List<Space> initializeRow(int index){
        List <Space> row = new ArrayList<>();
        for(int col=0; col<Board.SIZE; col++){
                // initializing whitePlayer's checker pieces
            if(((index==0 || index==2) && col%2==1) || (index == 1 && col%2==0))
                row.add(new Space(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE),true,col));
                //initializing redPlayer's pieces
            else if(((index==5 || index==7) && col%2==0) || (index==6 && col%2==1))
                row.add(new Space(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED), true, col));
                //Middle Area Pieces.
            else if(index == 3 && col%2 ==0){
                row.add(new Space(null, true, col));
            }
            else if(index == 4 && col %2 ==1){
                row.add(new Space(null, true, col));
            }
            else if (index == 3 && col%2 == 1){
                row.add(new Space(null, false, col));
            }
            // index == 4 && col %2 ==1
            else {
                row.add(new Space(null, false,col));
            }
        }
        return row;
    }

    // Get the index Number
    public int getIndex(){
        return this.index;
    }

    public Space getSpace(int index){
        return this.row.get(index);
    }
    /**
     * Iterator for the space
     * @return Space Iterator
     */
    @Override
    public Iterator<Space> iterator() {
        return row.iterator();
    }

    public List<Space> getRow() {
        return row;
    }

    public void setRow(List<Space> row) {
        this.row = row;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder(String.format("Row %d:[", index));
        for( Space space : row ){
            result.append(" ").append(space).append(" ");
        }
        result.append("]");

        return result.toString();

    }
}
