package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Application;
import com.webcheckers.appl.CenterGame;
import com.webcheckers.model.*;
import spark.*;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostValidateMove implements Route{
    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    private final CenterGame centerGame;



    /**
     * Constructor for PostSignInRoute
     * @param templateEngine the engine for app
     * @param centerGame the game center handling player services
     */
    PostValidateMove(final TemplateEngine templateEngine, final CenterGame centerGame){
        Objects.requireNonNull(templateEngine, "templateEngine should not be null.");
        this.centerGame = centerGame;
        LOG.finer("contrs");
    }

    public Object handle(Request request, Response response) {
        LOG.log(Level.WARNING,"PostValidateMove --> Invoked" + request.body());

        String moveString = request.body();
        Gson gson = new Gson();

        Message message;
        Move move = gson.fromJson(moveString,Move.class);

        Player currPlayer = centerGame.getPlayerLobby().getPlayerUsingSessionID(request.session().id());

        Game game = centerGame.getGameFromPlayer(currPlayer);

        //constructing a new board copy
        Board boardCopy = Board.copyBoard(game.getBoardStack().peek());

        System.out.println(game.getActiveColor());

        if (game.getActiveColor() == Piece.COLOR.RED) {
            message = MoveRule.moveStatus(game,boardCopy, move);
        }
        //white player's move
        else{

            Board whiteBoard = Board.reverseBoard(boardCopy);
            message = MoveRule.moveStatus(game,whiteBoard, move);
        }

        System.out.println(gson.toJson(message));

        return gson.toJson(message);

    }

}
