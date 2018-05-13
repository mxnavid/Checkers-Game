package com.webcheckers.model;

import java.util.Iterator;

/**
 * Created by Dank memes on 3/3/2018.
 */
@SuppressWarnings("JavaDoc")
public class Game {


    private Board board; //Board for the game

    private Player redPlayer; //Red Player of the Game
    private Player whitePlayer; //White Player of the Game
    private Player resignedPlayer;
    private Player winner; // winner of the game

    private Piece.COLOR activeColor; // Active Color

    private BoardStack boardStack;


    /**
     * Constructor for the Game
     * @param board : Board for the game
     * @param redPlayer : Red Player of the game
     * @param whitePlayer : White Player of the Game
     */
    public Game(Board board, Player redPlayer, Player whitePlayer){

        this.board = board;
        //setting the player as the red player
        this.redPlayer = redPlayer;

        this.redPlayer.setColor(Piece.COLOR.RED);
        //setting the player as the white player
        this.whitePlayer = whitePlayer;
        this.redPlayer.setColor(Piece.COLOR.WHITE);

        this.activeColor = Piece.COLOR.RED;
        this.boardStack = new BoardStack();

        this.resignedPlayer = null;

    }

    /**
     * checks if a player is in the game
     * @param player the player to check if they are in a game
     * @return true if in game; false otherwise
     */
    public boolean containsPlayer( Player player ){
        if (player.equals(redPlayer)) return true;
        return player.equals(whitePlayer);
    }

    /**
     * Get the red player
     * @return Player
     */
    public Player getRedPlayer() {return redPlayer;}


    /**
     * Get the White Player
     * @return : Player
     */
    public Player getWhitePlayer() {return whitePlayer;}


    /**
     * Assign the resigned player
     */
    public void resign(Player player){
        this.resignedPlayer = player;
    }


    /**
     * Get the board for the game
     * @return : Board
     */
    public Board getBoard() {return this.board;}


    /**
     * Get the active color for the game.
     * @return Piece.COLOR
     */
    public Piece.COLOR getActiveColor() {return activeColor;}

//    /**
//     * set the active color for the game.
//     */
//    public void setActiveColor(Piece.COLOR color) {activeColor = color;}

    /**
     * Get the board for the white player
     * @return : board for the white player.
     */
    public Board getWhitePlayerBoard(){return Board.orientBoard(this.board);}


    public BoardStack getBoardStack(){
        return this.boardStack;
    }


     /**
     * Gets the previous board stored in BoardStack
     * @return the last board
     */
    @SuppressWarnings("StatementWithEmptyBody")
    public void backup(){

        if(!this.boardStack.isMoveStackEmpty())
            this.boardStack.returnLastBoard();
    }

//    /**
//     * Given a player returns the color they are
//     * @param player the player in the game to search for
//     * @return That player's color
//     */
//    public Piece.COLOR getPlayerColor(Player player){
//        if(redPlayer.equals(player)){
//            return Piece.COLOR.RED;
//        }
//        if(whitePlayer.equals(player)){
//            return Piece.COLOR.WHITE;
//        }
//
//        //otherwise invalid player for game!
//        return null;
//    }

    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Gets the games active player
     * @return the player making the moves
     */
    public Player getActivePlayer(){
        if( activeColor == Piece.COLOR.RED ){
            return redPlayer;
        }
        else{
            return whitePlayer;
        }
    }

    public void switchActiveColor(){
        //if red active player switch to white
        if( activeColor == Piece.COLOR.RED){
            activeColor = Piece.COLOR.WHITE;
        }
        //otherwise switch to red
        else{
            activeColor = Piece.COLOR.RED;
        }
    }

    /**
     * checks if the board state is empty turn state
     * @return true if empty
     */
    public boolean isEmptyTurnState(){
        //if the board stack size is 1, this is a empty turn state
        //as the board stack will be size 1 if it is only storing the starting state
        return ( board.getBoard().size() == 1 );
    }

    /**
     * this basically just kings pieces for now
     * ensure to call before switching up the board
     * this is called at the end of the turn
     */
    public void updateBoard(){
        board.setLastJump(null);
        board.setLastMove(null);
        for (Iterator<Space> it = board.getRow(0).iterator(); it.hasNext(); ) {
            Space space = it.next();
            if(space.getPiece() != null && space.getPiece().getColor() == Piece.COLOR.RED && space.getPiece().getType()
                    != Piece.TYPE.KING){
                space.getPiece().kingMe();
            }

        }

        //for the other side of the board
        for (Iterator<Space> it = board.getRow(Board.SIZE - 1).iterator(); it.hasNext(); ) {
            Space space = it.next();
            if(space.getPiece() != null && space.getPiece().getColor() == Piece.COLOR.WHITE && space.getPiece().getType()
                    != Piece.TYPE.KING){
                space.getPiece().kingMe();
            }

        }

    }

    /**
     * Checks if the game has a winner
     * @return true if winner != null
     */
    public boolean hasWinner(){

        boolean hasWhite = false;
        boolean hasRed = false;

        //checking red player
        for( Row row : board.getBoard()){
            for( Space space: row.getRow()){
                if(space.getPiece()!= null) {
                    if (space.getPiece().getColor().equals(Piece.COLOR.WHITE)) {
                        hasWhite = true;
                        break;
                    }
                    if (space.getPiece().getColor().equals(Piece.COLOR.RED)) {
                        hasRed = true;
                        break;
                    }
                }
            }
        }

        if(!hasWhite){
            winner = redPlayer;
            return true;
        }
        if(!hasRed){
            winner = whitePlayer;
            return true;
        }

        return false;
    }

    /**
     * Gets winner of game
     * @return the player who won
     */
    public Player getWinner() {
        return winner;
    }

//    /**
//     * Sets the winner of the game
//     * @param winner The winner of the game
//     */
//    public void setWinner(Player winner) {
//        this.winner = winner;
//    }

    public boolean hasResignedPlayer(){
        return this.resignedPlayer != null;
    }

//    public Player getResignedPlayer() {
//        return resignedPlayer;
//    }


}
