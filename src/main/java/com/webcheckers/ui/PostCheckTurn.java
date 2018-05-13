package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Application;
import com.webcheckers.appl.CenterGame;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import freemarker.log.Logger;
import spark.*;

import java.util.Objects;

public class PostCheckTurn implements Route {

    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    private final CenterGame centerGame;



    /**
     * Constructor for PostSignInRoute
     * @param templateEngine engine for app
     * @param centerGame the game center running the application to user side
     */
    PostCheckTurn(final TemplateEngine templateEngine, final CenterGame centerGame){
        Objects.requireNonNull(templateEngine, "templateEngine should not be null.");
        this.centerGame = centerGame;
    }


    /**
     * Handles the user attempting to sign in
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the sign-in page if failed to sign in
     *   or the modified homepage, with new information given
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.info("PostCheckTurn -> Invoked");

        //get the current player
        Player currPlayer = centerGame.getPlayerLobby().getPlayerUsingSessionID(request.session().id());
        Game game = centerGame.getGameFromPlayer(currPlayer);

        Message message;

        //this means the game has been resigned
        if(game == null){
            //constructing the message object to return
            message = new Message( Message.Type.info, "true" );
            return ( new Gson().toJson(message) );
        }


        //this means it is not the players turn
        if( game.getActivePlayer().equals(currPlayer)){

            //constructing the message object to return
             message = new Message( Message.Type.info, "true" );

            System.out.println(" Player " + currPlayer.getName() + " says it's my turn");


    }
        //otherwise it's not their turn
        else{


            message = new Message(Message.Type.info, "false" );
            //System.out.println(" Player " + currPlayer.getName() + " says Not my turn");


        }


        return ( new Gson().toJson(message) );

    }


}
