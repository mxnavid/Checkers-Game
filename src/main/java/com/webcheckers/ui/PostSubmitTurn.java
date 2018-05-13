package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Application;
import com.webcheckers.appl.CenterGame;
import com.webcheckers.model.*;
import freemarker.log.Logger;
import spark.*;

import java.util.Iterator;
import java.util.Objects;

public class PostSubmitTurn implements Route {

    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    private final CenterGame centerGame;


    /**
     * Constructor for PostSignInRoute
     * @param templateEngine the engine for app
     * @param centerGame the game center handling player services
     */
    PostSubmitTurn(final TemplateEngine templateEngine, final CenterGame centerGame){
        Objects.requireNonNull(templateEngine, "templateEngine should not be null.");
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
        LOG.info("PostSubmitTurn -> Invoked");

        Player currPlayer = centerGame.getPlayerLobby().getPlayerUsingSessionID(request.session().id());
        Game game = centerGame.getGameFromPlayer(currPlayer);

        //storing the red and white move stacks
        BoardStack boardStack = game.getBoardStack();


        //first checking if there have been any moves made
        if(game.isEmptyTurnState()){
            //if so return a error, invalid move made
            Message message = new Message(Message.Type.error, "unprocessed");
            return new Gson().toJson(message);

        }

        //now getting the last board state from the boardStack
        Board lastBoard = boardStack.returnLastBoard();

        //clearing the remaining boards, no longer can backup
        boardStack.clear();



        if(game.getWhitePlayer().getName().equals("AI Player")){
            //TODO pass last board to your helper function it will return a board
            LOG.info("AiMan--> Invoked");
            Board aiBoard = MoveRule.aiMove(Board.reverseBoard( lastBoard ));
            aiBoard.setLastJump(null);
            aiBoard.setLastMove(null);
            for (Iterator<Space> it = aiBoard.getRow(Board.SIZE-1).iterator(); it.hasNext(); ) {
                Space space = it.next();
                if(space.getPiece() != null && space.getPiece().getColor() == Piece.COLOR.RED && space.getPiece().getType()
                        != Piece.TYPE.KING){
                    space.getPiece().kingMe();
                }

            }

            //for the other side of the board
            for (Iterator<Space> it = aiBoard.getRow(0).iterator(); it.hasNext(); ) {
                Space space = it.next();
                if(space.getPiece() != null && space.getPiece().getColor() == Piece.COLOR.WHITE && space.getPiece().getType()
                        != Piece.TYPE.KING){
                    space.getPiece().kingMe();
                }

            }
            game.getBoardStack().addBoard(Board.reverseBoard( aiBoard ));
            game.setBoard(Board.reverseBoard(aiBoard));
            Message message = new Message(Message.Type.info, "processed");
            return new Gson().toJson(message);
        }


        //now adding the initial board state to the stack, this way we can backup to this move
        //if(currPlayer.getActiveColor() == Piece.COLOR.RED) {
            boardStack.addBoard(lastBoard);
        //}


        //else{
            //Board whiteBoard = Board.reverseBoard(lastBoard);
            //Board whiteBoard = lastBoard;
            //game.getBoardStack().addBoard(Board.reverseBoard(whiteBoard));
        //}

        //the player has no longer jumped, nullifying the jump piece


        game.setBoard(lastBoard);
        //updating the game state now
        game.updateBoard();
        Message message = new Message(Message.Type.info, "processed");
        //changing the active player
        game.switchActiveColor();
        
        return new Gson().toJson(message);

    }


}
