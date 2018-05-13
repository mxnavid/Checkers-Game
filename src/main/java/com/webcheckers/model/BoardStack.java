package com.webcheckers.model;

import java.util.Stack;

public class BoardStack {
    private Stack<Board> boardStack;

    public BoardStack(){
        this.boardStack = new Stack<>();
    }

    public void addBoard(Board board){
        boardStack.push(board);
    }

    public Board returnLastBoard(){
        if (boardStack.empty()){
            return null;
        }
        else{
            return boardStack.pop();
        }

    }

    public boolean isMoveStackEmpty(){
        return this.boardStack.empty();
    }

    public void clear(){
        boardStack.clear();
    }

    public int getSize(){ return boardStack.size(); }

    public Board peek(){return boardStack.peek();}
}
