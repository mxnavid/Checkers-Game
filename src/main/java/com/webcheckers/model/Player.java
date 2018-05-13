package com.webcheckers.model;

public class Player {

    private String name;
    private String sessionID;
    private boolean inGame;
    private Piece.COLOR color;


    /**
     * Constructor
     * @param name name of Player
     */
    public Player(String name, String sessionID, Piece.COLOR color){
        this.name = name;
        this.sessionID = sessionID;
        this.inGame = false;
        this.color = color;

    }
    
	/**
     *  Retrieve the name of the player
     * @return the name of player
     */
    public String getName(){ return this.name; }

    public Piece.COLOR getActiveColor(){
        return this.color;
    }
    /**
     *Retrive the sessionID of the Player
     */
    public String getSessionID(){return this.sessionID;}


    /**
     * Set the player to be in a game
     */
    public void inGame(){ this.inGame = true; }

    /**
     * Set the player to not be in a game
     */
    public void notInGame(){ this.inGame = false; }

    /**
     * Check whether the player is in a game
     * @return inGame
     */
    public boolean isInGame() { return this.inGame; }

    //Check if we need to check the sessionID in the equals method too??

    /**
     * Check whether object passed in is the same player as this.
     * @param obj an object
     * @return true if obj equals this object; false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Player){
            Player that = (Player) obj;
            return this.sessionID.equals(that.sessionID);
        }
        return false;
    }
    
    /**
     * Hash code of Player object. The name is unique for every
     * Player object
     * @return the hash of this object
     */
    @Override
    public int hashCode(){ return this.name.hashCode(); }

    /**
     * Name representation of Player object
     * @return string representation of a player
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * set color to a player
     * @param color the color set to player
     */
    public void setColor(Piece.COLOR color) {
        this.color = color;
    }

}
