package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.CenterGame;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.logging.Logger;

/**
 * POST Resign Game for a player to resign a game
 */
public class PostResignGameRoute implements Route{

    private Logger LOG = Logger.getLogger(PostResignGameRoute.class.getName());
    private CenterGame centerGame;

    public PostResignGameRoute(CenterGame centerGame){

        this.centerGame = centerGame;

        LOG.config("PostResignGameRoute initialized");

    }


    /**
     * Gets invoked when player resigns from a game
     * @param request request to webserver
     * @param response response from webserver
     * @return Gson message
     */
    @Override
    public Object handle(Request request, Response response){

        LOG.config("PostResignGameRoute invoked");

        final Session httpSession = request.session();



        //get the player who resigned from the current game and assign him as the resigned player
        //of the Game object
        Player currPlayer = centerGame.getPlayerLobby().getPlayerUsingSessionID(httpSession.id());
        Game currGame = centerGame.getGameFromPlayer(currPlayer);
        if(currGame.isEmptyTurnState() || currGame.getActiveColor() != currPlayer.getActiveColor()){

            LOG.config("Player " + currPlayer + " resigned from game #" + currGame+".");

            Message message = new Message(Message.Type.info,"Successfully resigned.");
            currGame.resign(currPlayer);
            //return message saying the player is resigned
            return new Gson().toJson(message);
        }
        //otherwise error
        else{

            LOG.config("Player " + currPlayer + " failed to resign from game #" + currGame+".");
            Message message = new Message(Message.Type.error,"Failed to resign, backup to start to resign.");
            //return message saying the player is resigned
            return new Gson().toJson(message);
        }



    }

}
