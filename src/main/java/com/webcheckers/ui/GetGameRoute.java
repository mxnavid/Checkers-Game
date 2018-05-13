package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.appl.CenterGame;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import java.util.*;
import java.util.logging.Logger;

/**
 * GameRoute of the game
 */
@SuppressWarnings("JavaDoc")
public class GetGameRoute implements Route {

    static final String TITLE = "Game";

    static final String CURRENT_PLAYER_ATTR = "currentPlayer";
    static final String TITLE_ATTR = "title";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String VIEW_MODE_ATTR = "viewMode";
    static final String ACTIVE_COLOR_ATTR = "activeColor";
    static final String BOARD_ATTR = "board";
    static final String VIEW_NAME = "game.ftl";
    private static final Logger LOG = Logger.getLogger(Application.class.getName());
    private final TemplateEngine templateEngine;
    private final CenterGame centerGame;

    Map<String, Object> vm = new HashMap<>();

    /**
     * Constructor for the GameRoute
     * @param templateEngine the engine for the game route class
     * @param centerGame the game center that handles the application side to user
     */
    GetGameRoute(final TemplateEngine templateEngine, final CenterGame centerGame){
        Objects.requireNonNull(templateEngine, "template should not be null");
        this.templateEngine = templateEngine;
        this.centerGame = centerGame;
    }

    /**
     * Handles the request for the game route
     * @param request request to server
     * @param response response from server
     * @return String
     */
    public String handle(Request request, Response response){
        final Session httpSession = request.session();



        //constructing the game board
        Board board = Board.initializeBoard();

        //get the current player
        Player currPlayer = centerGame.getPlayerLobby().getPlayerUsingSessionID(httpSession.id());

        //getting the opponent
        String opponentName = request.queryParams("opponent");
        Player opponent = centerGame.getPlayerLobby().getPlayerUsingName(opponentName);



        //LOG.config("your opponent is : " + opponent.getName() );

        //This code is in specific for starting a game

        if (opponent!=null && !currPlayer.isInGame() && opponent.getSessionID().equals("AIMan")){

            //this code is for displaying messages to the home route, for things like winning, resigning ect...
            //currPlayer.setMessage(null);
            //opponent.setMessage(null);

            Game game = centerGame.startGame(board, currPlayer, opponent);
            game.getBoardStack().addBoard(board);
            LOG.config("Game Initialized!");
        }
        if( ( opponent != null && !currPlayer.isInGame() && !opponent.getSessionID().equals("AIMan")) ) {
            Game game = centerGame.startGame(board, currPlayer, opponent);

            //adding the first board state to the board stack
            game.getBoardStack().addBoard(board);

            if ( opponent.isInGame() ){
                vm.put(GetHomeRoute.BUSY_PLAYER, "The player you choose is busy right now");
                vm.put( GetHomeRoute.NUM_PLAYERS_ATTR, centerGame.getPlayerLobby().getNumPlayersExcludingPlayer());
                vm.put( GetHomeRoute.PLAYER_LOBBY_ATTR, centerGame.getPlayerLobby().getPlayerNames( httpSession.id() ) );
                vm.put( GetHomeRoute.TITLE_ATTR , currPlayer );
                vm.put( GetHomeRoute.USERNAME_ATTR, currPlayer );
                return templateEngine.render(new ModelAndView(vm , GetHomeRoute.VIEW_NAME ));
            }
            opponent.inGame();

            LOG.config("Game Initialized!");

        }


        //this will execute once a game has started
        Game game = centerGame.getGameFromPlayer(currPlayer);

        //if the game is over redirect back to the home page
        if(game == null){

            //TODO fix this message for winning and losing
            return UserServices.renderHomeRouteWithMessage(centerGame, vm, currPlayer, templateEngine, "You opponent has quit the game.");

        }
        else if(game.hasResignedPlayer()) {
            //end the game
            centerGame.endGame(game);

            return UserServices.renderHomeRouteWithMessage(centerGame, vm, currPlayer, templateEngine, "You have successfully resigned.");



        }
        else if(game.hasWinner()){


            if(game.getWinner().equals(currPlayer)) {
                return UserServices.renderHomeRouteWithMessage(centerGame, vm, currPlayer, templateEngine, "You have won!");
            }
            else{
                centerGame.endGame(game);
                return UserServices.renderHomeRouteWithMessage(centerGame, vm, currPlayer, templateEngine, "You have lost!");
            }

        }


        //otherwise we want to ensure that our player is considered in a game
        else {
            currPlayer.inGame();
        }

        return UserServices.constructGameView(game,vm,currPlayer,templateEngine);
    }
}

