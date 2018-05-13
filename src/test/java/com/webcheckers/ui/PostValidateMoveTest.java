package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.CenterGame;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
class PostValidateMoveTest {

    //CuT component
    private PostValidateMove CuT;

    //friendly objects
    private CenterGame centerGame;

    //mock objects
    private Request request1, request2;
    private Session session1, session2;
    private TemplateEngine templateEngine;
    private Response response;


    /**
     * set up mock and friendly object
     */
    @Before
    public void setup(){

        request1 = mock(Request.class);
        request2 = mock(Request.class);
        session1 = mock(Session.class);
        session2 = mock(Session.class);

        when(request1.body())
                .thenReturn("{\"start\":{\"row\":\"5\",\"cell\":\"0\"},\"end\":{\"row\":\"4\",\"cell\":\"1\"}}");
        when(request2.body())
                .thenReturn("{\"start\":{\"row\":\"2\",\"cell\":\"1\"},\"end\":{\"row\":\"0\",\"cell\":\"0\"}}");


        when(request1.session()).thenReturn(session1);
        when(request2.session()).thenReturn(session2);
        when(session1.attribute("player")).thenReturn("john");
        when(session2.attribute("player")).thenReturn("doe");


        response = mock(Response.class);

        centerGame = new CenterGame();
        centerGame.getPlayerLobby().addPlayer("john",session1.id());
        centerGame.getPlayerLobby().addPlayer("doe",session2.id());

        centerGame.startGame(Board.initializeBoard(),new Player("john",session1.id(), Piece.COLOR.WHITE),
                new Player("doe", session2.id(),Piece.COLOR.RED));

        this.templateEngine = mock(TemplateEngine.class);

        CuT = new PostValidateMove(templateEngine,centerGame);
    }


    /**
     * test whether PostValidateMove requests and responds the
     * Message data type
     */
    @Test
    public void test() throws Exception{

        assertEquals(Message.class, CuT.handle(request1, response).getClass());
        assertEquals(Message.class, CuT.handle(request2, response).getClass());

    }
}