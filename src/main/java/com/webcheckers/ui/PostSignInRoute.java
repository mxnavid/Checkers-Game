package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.appl.CenterGame;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Post Sign In Route
 */
public class PostSignInRoute implements Route {

    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    private final CenterGame centerGame;
    private TemplateEngine templateEngine;


    /**
     * Constructor for PostSignInRoute
     * @param templateEngine the engine for app
     * @param centerGame the game center
     */
    PostSignInRoute(final TemplateEngine templateEngine, final CenterGame centerGame){
        Objects.requireNonNull(templateEngine, "templateEngine should not be null.");
        this.templateEngine = templateEngine;
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
        LOG.log(Level.INFO,"PostSignInRoute -> Invoked");
        // retrieving username from request
        String username = request.queryParams( GetSignInRoute.USERNAME_FIELD );

        //retrieving the http session
        Session session = request.session();
        

        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        UserServices.UserNameResult checkUser = UserServices.checkUsername( username, session.id() ,centerGame );

        //if the username is invalid
        if( checkUser == UserServices.UserNameResult.INVALID ){

            vm.put( GetSignInRoute.TITLE_ATTR, "Error!" );
            vm.put( GetSignInRoute.ERROR_MESSAGE, UserServices.makeInvalidUsernameMessage( username ) );
            return templateEngine.render( new ModelAndView( vm, GetSignInRoute.VIEW_NAME ));

        }
        //otherwise check if the username is in use
        else if( checkUser.equals(UserServices.UserNameResult.IN_USE)){

            vm.put( GetSignInRoute.TITLE_ATTR, "Error!" );
            vm.put( GetSignInRoute.ERROR_MESSAGE, UserServices.makeExsistingUsernameMessage( username ) );
            return templateEngine.render( new ModelAndView( vm, GetSignInRoute.VIEW_NAME ));
        }

        //otherwise the username is valid!

        //adding the player to the server player lobby
        //the player that has signed in
        Player player = new Player(username, session.id(), null);
        centerGame.getPlayerLobby().addPlayer( username, session.id() );

        //storing the player as a attribute on the clients session
        session.attribute(GetHomeRoute.PLAYER_KEY, player);

        //display the new display on home page
        vm.put( GetHomeRoute.TITLE_ATTR , username );
        vm.put( GetHomeRoute.USERNAME_ATTR, username );

        //now displaying the player lobby
        vm.put( GetHomeRoute.PLAYER_LOBBY_ATTR, centerGame.getPlayerLobby().getPlayerNames( session.id() ) );
        //displaying number of players
        vm.put( GetHomeRoute.NUM_PLAYERS_ATTR, centerGame.getPlayerLobby().getNumPlayersExcludingPlayer());

        return templateEngine.render( new ModelAndView( vm, GetHomeRoute.VIEW_NAME ));

    }


}
