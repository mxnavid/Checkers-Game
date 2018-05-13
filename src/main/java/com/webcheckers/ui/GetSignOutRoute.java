package com.webcheckers.ui;

import com.webcheckers.appl.CenterGame;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Sign Out Route for the Checker Game
 */
public class GetSignOutRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetSignOutRoute.class.getName());


    private TemplateEngine templateEngine;
    private final CenterGame centerGame;

    GetSignOutRoute(final TemplateEngine templateEngine, final CenterGame centerGame){
        Objects.requireNonNull(templateEngine, "templateEngine should not be null.");
        this.templateEngine = templateEngine;
        this.centerGame = centerGame;

    }


    /**
     * Handles the user attempting to sign out
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the home page
     */
     @Override
     public Object handle(Request request, Response response) {
         LOG.log(Level.WARNING,"GetSignOutRoute -> Invoked");

         // start building the View-Model
         final Map<String, Object> vm = new HashMap<>();

         //retrieving the http session
         Session session = request.session();

         //nulling out the player so it no longer exists as a client side attribute
         session.attribute(GetHomeRoute.PLAYER_KEY, null );

         //removing the player from the player lobby
         centerGame.getPlayerLobby().deletePlayer( request.session().id() );

         //removing the username attribute from ftl page
         vm.put( GetHomeRoute.USERNAME_ATTR, null );

         //for the title attribute
         vm.put( GetHomeRoute.TITLE_ATTR , UserServices.SIGN_OUT_MESSAGE );

         //updating number of players
         vm.put( GetHomeRoute.NUM_PLAYERS_ATTR, centerGame.getPlayerLobby().getNumPlayers());

         //redirecting back to home page, as sign-out page does not exist
         response.redirect( WebServer.HOME_URL );

         return templateEngine.render( new ModelAndView( vm, GetHomeRoute.VIEW_NAME ));
     }

}
