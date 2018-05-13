package com.webcheckers.ui;

import com.webcheckers.appl.CenterGame;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.mockito.internal.matchers.Null;
import spark.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 *  Test class for PostSignInRoute
 *  @author Alexander Wall
 */
@Tag("UI-tier")
public class PostSignInRouteTest {

    /**
     * The component-under-test CuT
     */
    private PostSignInRoute CuT;

    /**
     * friendly objects
     */
    private CenterGame cg;

    /**
     * Mock objects
     */
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Player player;


    /**
     * Test names
     */
    private static final String user1 = "Jeff";
    private static final String user2 = "praysun2883";
    private static final String user3 = "Redline2!";
    private static final String user4 = "!@#$%^&(*)|}{";
    private static final String user5 = "";
    private static final String user6 = " ";


    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        session = mock(Session.class);
        player = mock(Player.class);

        when(request.session()).thenReturn(session);
        when(request.queryParams(GetSignInRoute.USERNAME_FIELD)).thenReturn(user1);
        when(request.queryParams(GetSignInRoute.USERNAME_FIELD)).thenReturn(user2);



        cg = new CenterGame();
        CuT = new PostSignInRoute(templateEngine, cg);
    }

    @Test//(expected= NullPointerException.class)
    public void checkUser(){
        assertEquals(UserServices.UserNameResult.VALID,UserServices.checkUsername(user1,session.id(),cg));
        assertEquals(UserServices.UserNameResult.VALID,UserServices.checkUsername(user2,session.id(),cg));
        assertEquals(UserServices.UserNameResult.INVALID,UserServices.checkUsername(user3,session.id(),cg));
        assertEquals(UserServices.UserNameResult.INVALID,UserServices.checkUsername(user4,session.id(),cg));
        assertEquals(UserServices.UserNameResult.INVALID,UserServices.checkUsername(user5,session.id(),cg));
        assertEquals(UserServices.UserNameResult.INVALID,UserServices.checkUsername(user6,session.id(),cg));
    }


    @Test//(expected= NullPointerException.class)
    public void loginSuccess() throws Exception {
        when(request.queryParams(GetSignInRoute.USERNAME_FIELD)).thenReturn(user1);
        cg.getPlayerLobby().addPlayer(user1,request.session().id());
        when(request.queryParams((GetSignInRoute.USERNAME_FIELD))).thenReturn(user2);
        cg.getPlayerLobby().addPlayer(user2,request.session().id());
        assertNotNull(CuT.handle(request,response));

    }

    @Test//(expected= NullPointerException.class)
    public void loginFail() throws Exception{
        when(request.queryParams(GetSignInRoute.USERNAME_FIELD)).thenReturn(user3);
        cg.getPlayerLobby().addPlayer(user1,request.session().id());
        when(request.queryParams((GetSignInRoute.USERNAME_FIELD))).thenReturn(user4);
        cg.getPlayerLobby().addPlayer(user2,request.session().id());
        assertNotNull(CuT.handle(request,response));
    }

    @Test//(expected= NullPointerException.class)
    public void userExists() throws Exception{
        cg.getPlayerLobby().addPlayer(user1,session.id());
        cg.getPlayerLobby().addPlayer(user2,session.id());
        when(request.queryParams(GetSignInRoute.USERNAME_FIELD)).thenReturn(user1);
        cg.getPlayerLobby().addPlayer(user1,session.id());
        when(request.queryParams((GetSignInRoute.USERNAME_FIELD))).thenReturn(user2);
        cg.getPlayerLobby().addPlayer(user2,request.session().id());
        assertNotNull(CuT.handle(request,response));

    }



}