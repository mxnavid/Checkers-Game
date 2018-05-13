package com.webcheckers.appl;

import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.ArrayList;

@SuppressWarnings("JavaDoc")
public class CenterGame {

    //The player lobby for the CenterGame
    private PlayerLobby playerLobby;
    //The arraylist of games
    private ArrayList<Game> games;

    public CenterGame(){
        //constructing the player Lobby
        playerLobby = new PlayerLobby();
        games = new ArrayList<>();
    }

    /**
     * Gets the game a player is in
     * @param player a player
     * @return game that player is contained in
     */
    public Game getGameFromPlayer( Player player ){
        for( Game game : games ){
            if( game.containsPlayer( player ) )
                return game;
        }
        return null;
    }

    /**
     * gets the player lobby
     * @return The player lobby for the CenterGame
     */
    public PlayerLobby getPlayerLobby() {
        return playerLobby;
    }

    /**
     * Starts up a checkers game on the server
     * @param board the board game
     * @param whitePlayer the white player
     * @param redPlayer the red player
     */
    public Game startGame(Board board, Player whitePlayer, Player redPlayer ){
        Game game = new Game(board, whitePlayer, redPlayer);
        games.add( game );
        return game;
    }

    /**
     * Removes the ongoing game
     * @param game game to be removed
     */
    public void endGame(Game game){
        games.remove(game);
        game.getRedPlayer().notInGame();
        game.getWhitePlayer().notInGame();
    }

}
