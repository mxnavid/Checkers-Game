package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.webcheckers.appl.CenterGame;
import com.webcheckers.model.Player;
import spark.*;


/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
//  static final String PLAYERSERVICES_KEY = "playerServices";
  static final String VIEW_NAME = "home.ftl";
  static final String TITLE_ATTR = "title";
  final static String USERNAME_ATTR = "username";
  final static String PLAYER_KEY = "player";
  final static String PLAYER_LOBBY_ATTR = "playerlobby";
  final static String NUM_PLAYERS_ATTR = "numplayers";
  final static String BUSY_PLAYER = "message";

  private final TemplateEngine templateEngine;
  private final CenterGame centerGame;
//  private ModelAndView modelAndView;



  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /} HTTP request.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final TemplateEngine templateEngine, final CenterGame centerGame) {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.templateEngine = templateEngine;
    //
    LOG.config("GetHomeRoute is initialized.");

    this.centerGame = centerGame;

  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.log(Level.WARNING, "GetHomeRoute -> invoked.");
    //
    Map<String, Object> vm = new HashMap<>();

    //retrieving session
    Session session = request.session();
    if( session.attribute( GetHomeRoute.PLAYER_KEY ) != null ){

      Player player = session.attribute( GetHomeRoute.PLAYER_KEY );

      //checking if the player is in a game
      if( centerGame.getGameFromPlayer( player ) != null ){
        //redirecting to game

        response.redirect( WebServer.GAME_ROOM );
      }

      String username = player.getName();

      //display the new display on home page
      vm.put( GetHomeRoute.TITLE_ATTR , username );
      vm.put( GetHomeRoute.USERNAME_ATTR, username );

      //displaying number of players
      vm.put( GetHomeRoute.NUM_PLAYERS_ATTR, centerGame.getPlayerLobby().getNumPlayersExcludingPlayer());

      //now displaying the player lobby
      vm.put( GetHomeRoute.PLAYER_LOBBY_ATTR, centerGame.getPlayerLobby().getPlayerNames( session.id() ) );



      //vm.put( GetHomeRoute.USERNAME_ATTR, player.getName() );
      return templateEngine.render(new ModelAndView(vm , GetHomeRoute.VIEW_NAME ));
    }

    vm.put("title", "Welcome!");
    //displaying number of players
    vm.put( GetHomeRoute.NUM_PLAYERS_ATTR, centerGame.getPlayerLobby().getNumPlayers());

    return templateEngine.render(new ModelAndView(vm , GetHomeRoute.VIEW_NAME ));
  }

//  public ModelAndView getModelAndView(){
//    return modelAndView;
//  }


}