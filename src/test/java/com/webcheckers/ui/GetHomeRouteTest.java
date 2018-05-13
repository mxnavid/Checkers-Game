package com.webcheckers.ui;
import com.webcheckers.appl.CenterGame;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.eclipse.jetty.server.Authentication;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import spark.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Navid Nafiuzzaman <mxn4459@rit.edu> on 3/21/18
 */
public class GetHomeRouteTest {
    //All the variables required for the test
    private GetHomeRoute home;
    private Request request;
    private Response response;
    private TemplateEngine templateEngine;
    private CenterGame centerGame;
    private PlayerLobby playerLobby;


    /**
     * Setup for the HomeRoute
     */
    @Before
    public void setup(){
        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        home = new GetHomeRoute(templateEngine, centerGame);
    }

    /**
     * Test the home route
     */
    @Test
    public void testHomeRoute(){
        final Session session = request.session();
        final Response response = mock(Response.class);
        final ModelAndView capt =mock(ModelAndView.class);
        //final Player samplePlayer = new Player("SampleFam", session.id() );
        Map<String, Object> vm = (Map<String, Object>)capt;
        assertEquals("Welcome!", vm.get(GetHomeRoute.TITLE_ATTR));
        assertEquals(playerLobby.getNumPlayers(), vm.get(GetHomeRoute.NUM_PLAYERS_ATTR));
        assertNotNull(vm.get(GetHomeRoute.USERNAME_ATTR));
        assertNotNull(vm.get(GetHomeRoute.VIEW_NAME));
        //assertEquals(samplePlayer, vm.get(GetHomeRoute.USERNAME_ATTR));

    }

}
