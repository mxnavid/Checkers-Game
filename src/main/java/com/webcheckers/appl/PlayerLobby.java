package com.webcheckers.appl;

import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class PlayerLobby {

    private HashMap<String,Player> playerBoy;

    /**
     * Constructor of PlayerLobby
     */
    public PlayerLobby(){
        this.playerBoy = new HashMap<>();
        Player aiPlayer = new Player("AI Player", "AIMan", Piece.COLOR.WHITE);
        playerBoy.put("AIMan", aiPlayer);
    }

    /**
     * Add player in the hashmap by using Player Session ID as Key 
     * and Player as a value of that key.
     */
    public void addPlayer(String playerName, String playerSessionID){
        Player playerNew = new Player(playerName, playerSessionID, null );
        playerBoy.put(playerSessionID, playerNew);
    }


    /**
     * Add a AI player to the hashMap
     *
    /**
     * Deletes player in the hashmap by using Player Session ID. 
     */
    public void deletePlayer(String sessionID){
        playerBoy.remove(sessionID);
    }

    /**
     * Returns the players using the provided session ID.
     */
    public Player getPlayerUsingSessionID(String sessionID){
        return playerBoy.get(sessionID);
    }


    /**
     * Returns the player using the provided name.
     */
    public Player getPlayerUsingName(String username){

        //retrieving the list of players
        Collection<Player> players =  playerBoy.values();
        for( Player player : players){

            //ensuring the player is not the player on the client
            if( player.getName().equals(username))
                return player;
        }

        return null;
    }


    /**
     * checks if the session contains a player name, excluding the sessions
     * @param sessionID the current client session id
     * @param username the username to search for
     * @return if the username is already in use
     */
    public boolean containsPlayer( String sessionID, String username ){

        return getPlayerNames(sessionID).contains( username );
    }


    /**
     *
     * @return number of players playing webcheckers
     */
    public int getNumPlayers( ){
        return playerBoy.size()-1;
    }

    /**
     *
     * @return number of players playing webcheckers, excluding the player
     * which is just 1 minus total
     */
    public int getNumPlayersExcludingPlayer( ){
        return playerBoy.size()-2;
    }


    /**
     * Return the names of the players.
     * @param sessionID Uses sessionID to fetch players name
     * @return List of the Players in String.
     */
    public ArrayList<String> getPlayerNames( String sessionID ) {

        //constructing the arraylist of strings we will return
        ArrayList<String> playernames = new ArrayList<>();

        //retrieving the list of players
        Collection<Player> players =  playerBoy.values();
        for( Player player : players){

            //ensuring the player is not the player on the client
            if( ! player.getSessionID().equals( sessionID ))
            playernames.add(player.getName());
        }
        return playernames;
    }




}

