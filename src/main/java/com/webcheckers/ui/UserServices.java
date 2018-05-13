package com.webcheckers.ui;

import com.webcheckers.appl.CenterGame;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.util.Map;
import java.util.regex.Pattern;


/**
 * Created by Potatoes on 2/28/2018.
 */
public class UserServices {

    static String SIGN_OUT_MESSAGE = "Thank you for playing!";


    //enum for the types of entered username results
    enum UserNameResult{
        INVALID,
        IN_USE,
        VALID
    }

    /**
     *  error message when invalid username given
     */
    static String makeInvalidUsernameMessage( final String username){
        return String.format("The username '%s' contains invalid characters!", username);
    }

    /**
     *  error message when identical  username given
     */
    static String makeExsistingUsernameMessage( final String username){
        return String.format("The username '%s' is already in use!", username);
    }

    /**
     * Method which checks the userName with the existing data of the usernames
     * @param username String of a user's name on app
     * @param sessionID the session ID that the user is on
     * @param centerGame the game center
     * @return UserNameResult
     */
    static UserNameResult checkUsername(final String username, final String sessionID ,CenterGame centerGame){

        //first check if alpha-numerical
        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
        boolean hasSpecialChar = p.matcher( username ).find();
        if( hasSpecialChar ){
            return UserNameResult.INVALID;
        }

        //checks if player already using name
        if( centerGame.getPlayerLobby().containsPlayer( sessionID ,username )){
            return UserNameResult.IN_USE;
        }

        return UserNameResult.VALID;
    }

    static String constructGameView(Game game, Map<String, Object> vm, Player player, TemplateEngine templateEngine){

        //title for the game
        vm.put(GetGameRoute.TITLE_ATTR, GetGameRoute.TITLE);
        //who is starting the game?
        if(player.getSessionID().equals(game.getRedPlayer().getSessionID())) {
            vm.put(GetGameRoute.CURRENT_PLAYER_ATTR, game.getRedPlayer());
            vm.put(GetGameRoute.RED_PLAYER_ATTR, game.getRedPlayer());
            vm.put(GetGameRoute.WHITE_PLAYER_ATTR, game.getWhitePlayer());
            vm.put(GetGameRoute.VIEW_MODE_ATTR, "PLAY");
            vm.put(GetGameRoute.ACTIVE_COLOR_ATTR, game.getActiveColor());
            vm.put(GetGameRoute.BOARD_ATTR, game.getBoard());
        }
        else{
            vm.put(GetGameRoute.CURRENT_PLAYER_ATTR,game.getWhitePlayer());
            vm.put(GetGameRoute.RED_PLAYER_ATTR, game.getRedPlayer());
            vm.put(GetGameRoute.WHITE_PLAYER_ATTR, game.getWhitePlayer());
            vm.put(GetGameRoute.VIEW_MODE_ATTR,"PLAY");
            vm.put(GetGameRoute.ACTIVE_COLOR_ATTR, game.getActiveColor());
            vm.put(GetGameRoute.BOARD_ATTR, game.getWhitePlayerBoard());
        }
        return templateEngine.render(new ModelAndView(vm,GetGameRoute.VIEW_NAME));
    }

    static String renderHomeRouteWithMessage(CenterGame centerGame, Map<String, Object> vm, Player player, TemplateEngine templateEngine, String message){
        vm.put( GetHomeRoute.TITLE_ATTR , player.getName() );
        vm.put( GetHomeRoute.USERNAME_ATTR, player.getName() );

        //displaying number of players
        vm.put( GetHomeRoute.NUM_PLAYERS_ATTR, centerGame.getPlayerLobby().getNumPlayersExcludingPlayer());

        //now displaying the player lobby
        vm.put( GetHomeRoute.PLAYER_LOBBY_ATTR, centerGame.getPlayerLobby().getPlayerNames( player.getSessionID() ) );

        vm.put( GetHomeRoute.BUSY_PLAYER, message);

        return templateEngine.render(new ModelAndView(vm , GetHomeRoute.VIEW_NAME ));
    }

}
