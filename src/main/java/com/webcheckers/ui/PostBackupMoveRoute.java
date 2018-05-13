package com.webcheckers.ui;

import com.webcheckers.appl.CenterGame;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;
import com.google.gson.Gson;

import java.util.logging.Logger;


/**
 * This route is called in WebServer when the Player selects
 *  the back button. If the player needs to back up more than one move,
 *  they can select the button again. Fixed
*/
public class PostBackupMoveRoute implements Route{

    private Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());
    private CenterGame centerGame;


   public PostBackupMoveRoute(CenterGame center){
       this.centerGame = center;
       LOG.config("PostBackupMove initialized");
   }


   @Override
   public Object handle(Request request, Response response) {
       LOG.config("PostBackupMove invoked");


       final Session httpSession = request.session();

       //gets the backup Board

       Player currPlayer = centerGame.getPlayerLobby().getPlayerUsingSessionID(httpSession.id());

       Game back = centerGame.getGameFromPlayer(currPlayer);

       //checking if empty state, if so then error
       if(back.isEmptyTurnState()){
           Message message = new Message(Message.Type.error,"No moves made!");
           return new Gson().toJson(message);
       }

       //otherwise valid backup
       Message message = new Message(Message.Type.info,"Valid backup");

       System.out.println("board size " + back.getBoardStack().getSize() );
       //have to pop off this first board state to go back
       back.backup();
       //now peeking at the previous board ;)
       Board backup = back.getBoardStack().peek();


       Game currGame = centerGame.getGameFromPlayer(currPlayer);
       // Set the board to the backup
       currGame.setBoard(backup);

       return new Gson().toJson(message);

   }



}
