package com.webcheckers.ui;

import com.webcheckers.appl.CenterGame;
import com.webcheckers.model.Board;
import com.webcheckers.model.Message;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
class PostSubmitTurnTest {


    //CuT component
    private PostSubmitTurn CuT;


    //friendly objects
    private CenterGame centerGame;
    private static final String PLAYER1 = "john";
    private static final String PLAYER2 = "doe";
    private Player player1, player2;


    private Session session1, session2;
    private Request request;
    private Response response;

    @BeforeEach
    public void setup(){

        session1 = mock(Session.class);
        session2 = mock(Session.class);
        request = mock(Request.class);
        response = mock(Response.class);

        when(session1.attribute("player")).thenReturn(PLAYER1);
        when(session2.attribute("player")).thenReturn(PLAYER2);

        player1 = new Player(PLAYER1, session1.id(), Piece.COLOR.RED);
        player2 = new Player(PLAYER2,session2.id(), Piece.COLOR.WHITE);

        centerGame = new CenterGame();
        centerGame.getPlayerLobby().addPlayer(PLAYER1,session1.id());
        centerGame.getPlayerLobby().addPlayer(PLAYER2,session2.id());


        centerGame.startGame(Board.initializeBoard(),player2, player1);

        when(request.session()).thenReturn(session1);
        when(session1.attribute("player")).thenReturn(player1);

        CuT = new PostSubmitTurn(mock(TemplateEngine.class),centerGame);

    }


    /**
     * Test a submit turn returns a message
     */
    @Test
    public void test() throws Exception{

        assertNotNull(CuT.handle(request,response));
        assertTrue(CuT.handle(request,response) instanceof Message);

    }


}