package com.webcheckers.model;

import java.util.*;

/**
 * MoveRule is the class which will have the logics for the moves.
 */
@SuppressWarnings("JavaDoc")
public class MoveRule {


    public static Message moveStatus(Game game, Board board, Move move){

        //checking if a move has already been made
        if( board.getLastMove() != null || board.getLastJump() != null){

            if(board.getLastJump() == null){
                return new Message(Message.Type.error, "You have already moved and no additional jumps " +
                        " are available, please end your turn or backup.");
            }

            Piece piece = board.getRow(move.getStart().getRow())
                    .getSpace(move.getStart().getCell()).getPiece();

            //if the piece is null here it means you are attempting to make a invalid jump
            if( piece == null ){



                //TODO NOTE THIS IS UPSETTING
                //grabbing therhrehgrehgerhgrehugre I'm not even going to comment this i'm so mad
                //if anyone is reading this you already are too far


                //jumps must be ENFORCED
                //also there should always be a piece here ( come at me fool )

                System.out.println("THE MOVE : " + move);

                //Alright so this has to be done

                //jumps must be ENFORCED
                //also there should always be a piece here ( come at me fool )
                HashMap<Move,Board> jumpMoves = getJumpMoves(board,game.getActiveColor());


                if(jumpMoves.size() > 0){

                    //having to iterate because the hashmap is crammed
                    Iterator<Move> keys = jumpMoves.keySet().iterator();
                    while(keys.hasNext()){
                        Move currMove = keys.next();
                        if(currMove.equals(move) ) {
                            //storing the previous board


                            if(game.getActiveColor() == Piece.COLOR.WHITE){
                                game.getBoardStack().addBoard( Board.reverseBoard( jumpMoves.get(currMove) ) );
                            }
                            else{
                                game.getBoardStack().addBoard(  jumpMoves.get(currMove) );
                            }

                            board.setLastJump(move);
                            return new Message(Message.Type.info, "Valid Jump Move!");
                        }

                    }
                    //otherwise just return invalid move, you must make jump
                    return new Message(Message.Type.error,"Invalid move, jump available which must be made!");

                }

//
//                //getting the enemy piece
//                int offsetRow = move.getStart().getRow() - move.getEnd().getRow();
//                int offsetCol = move.getStart().getCell() - move.getEnd().getCell();
//                int enemyrow = move.getStart().getRow() - offsetRow/2;
//                int enemycol = move.getStart().getCell() - offsetCol/2;
//                System.out.println("ENEMY ROW: " + enemyrow + " ENEMY COL: " + enemycol );
//                Space enemySpace = board.getRow(enemyrow).getSpace(enemycol);
//                if( enemySpace.getPiece() != null && enemySpace.getPiece().getColor() != game.getActiveColor()){
//
//                    board.destroyPiece(enemySpace);
//                    board.movePiece(move);
//                    board.setLastJump(move);
//                    if(game.getActiveColor() == Piece.COLOR.WHITE){
//                        game.getBoardStack().addBoard( Board.reverseBoard( board ) );
//                    }
//                    else{
//                        game.getBoardStack().addBoard(  board );
//                    }
//                    return new Message(Message.Type.info, "Additional jump made!");
//                }
//
//                return new Message(Message.Type.error, "You have already jumped and no additional jumps " +
//                            " are available, please end your turn or backup.");

            }

        }

        Piece piece = board.getRow(move.getStart().getRow())
                    .getSpace(move.getStart().getCell()).getPiece();


        //retrieving the player color
        Piece.COLOR color = piece.getColor();

        //jumps must be ENFORCED
        //also there should always be a piece here ( come at me fool )
        HashMap<Move,Board> jumpMoves = getJumpMoves(board,color);


        if(jumpMoves.size() > 0){

            //having to iterate because the hashmap is crammed
            Iterator<Move> keys = jumpMoves.keySet().iterator();
            while(keys.hasNext()){
                Move currMove = keys.next();
                if(currMove.equals(move) ) {
                    //storing the previous board
                    jumpMoves.get(currMove).setLastJump(move);

                    if(color == Piece.COLOR.WHITE){
                        game.getBoardStack().addBoard( Board.reverseBoard( jumpMoves.get(currMove) ) );
                    }
                    else{
                        game.getBoardStack().addBoard(  jumpMoves.get(currMove) );
                    }


                    return new Message(Message.Type.info, "Valid Jump Move!");
                }

            }
            //otherwise just return invalid move, you must make jump
            return new Message(Message.Type.error,"Invalid move, jump available which must be made!");

        }


        //single move or jump move?
        //depends on the row
        int rowMove = Math.abs(move.getStart().getRow() - move.getEnd().getRow());

        Message result = (rowMove == 1) ? isValidSingleMove(board, move) : isValidJumpMove(board, move);

        if(  result.getType() == Message.Type.info ) {

            if(board.getLastJump() != null || board.getLastMove() != null){
                return new Message(Message.Type.error,"Invalid move, you have already moved, no jumps are" +
                        " possible, end your turn or backup.");
            }

            board.movePiece(move);
            board.setLastMove(move);
            if(color == Piece.COLOR.WHITE){
                game.getBoardStack().addBoard( Board.reverseBoard( board ) );
            }
            else{
                game.getBoardStack().addBoard(  board );
            }
        }



        return result;

    }


    /**
     * Check a piece's single move is valid
     * @param board the board
     * @param move move containing the start and end positions
     * @return an info or error message
     */
    private static Message isValidSingleMove(Board board, Move move){

        Position start = move.getStart();
        Position end = move.getEnd();

        //get color and type of piece
        Piece piece = board.getRow(start.getRow()).getSpace(start.getCell()).getPiece();
        Piece.COLOR color = piece.getColor();
        Piece.TYPE type = piece.getType();

        //error handling for ai player move on another piece
        if(!board.getRow(end.getRow()).getSpace(end.getCell()).isValid()){
            return new Message(Message.Type.error, String.format("%s: invalid move on piece", color));
        }

        //depending on the type
        switch(type){

            case SINGLE:
                //check for backward jump
                if (((move.getStart().getRow()-1) == (move.getEnd().getRow()))){

                    if( Math.abs( move.getEnd().getCell() - move.getStart().getCell() ) > 1){
                        return new Message(Message.Type.error, String.format("%s: too horizontal jump",color));
                    }

                    //single move to the top of the board
                    if(end.getRow() == 0){
                        piece.kingMe();
                        return new Message(Message.Type.info, String.format("CONGRATS! %s: piece is now KING",color));
                    }

                    return new Message(Message.Type.info, String.format("%s: valid single move.",color));
                }
                else{
                    return new Message(Message.Type.error, String.format("%s: backward jump",color));
                }

            case KING:
                //board already marks spaces with pieces as invalid and white spaces as invalid also
                //so what else is there to check for a king doing a single move?
                return new Message(Message.Type.info, String.format("%s KING: valid single move", color));


            default:
                //invalid type???
                return new Message(Message.Type.error, "What type is this???");


        }

    }


    /**
     * Checks the validity of a piece's jump
     * @param board the board
     * @param move the move containing the start and end positions
     * @return an info or error message
     */
    private static Message isValidJumpMove(Board board, Move move){

        Position start = move.getStart();
        Position end = move.getEnd();

        //get color and type of piece
        Piece piece = board.getRow(start.getRow()).getSpace(start.getCell()).getPiece();
        Piece.COLOR color = piece.getColor();
        Piece.TYPE type = piece.getType();


        switch(type) {

            case SINGLE:

                //SE & SW are invalid moves
                if (((start.getRow() - end.getRow() == -2) && (start.getCell() - end.getCell() == 2))
                        || ((start.getRow() - end.getRow() == -2) && (start.getCell() - end.getCell() == -2))) {
                    return new Message(Message.Type.error, String.format("%s: Backward jump",color));
                }

                //NE jump
                if ((start.getRow() - end.getRow() == 2) && (start.getCell() - end.getCell() == -2)) {
                    Space space = board.getRow(start.getRow() - 1).getSpace(start.getCell() + 1);
                    return checkSpaceOverJump(board, move, space, piece);
                }

                //NW jump
                if ((start.getRow() - end.getRow() == 2) && (start.getCell() - end.getCell() == 2)) {
                    Space space = board.getRow(start.getRow() - 1).getSpace(start.getCell() - 1);
                    return checkSpaceOverJump(board, move, space, piece);
                }


            case KING:

                //check one direction jumps
                if(start.getRow() == end.getRow())
                    return new Message(Message.Type.error,String.format("%s KING: Jump is too horizontal",color));
                if(start.getCell() == end.getCell())
                    return new Message(Message.Type.error,String.format("%s KING: Jump is too vertical",color));

                /* check jump over space */
                Space space;

                //checking jump NW
                if((start.getRow()-end.getRow()==2) && (start.getCell()-end.getCell()==2)){

                    space = board.getRow(start.getRow()-1).getSpace(start.getCell()-1);

                    return checkSpaceOverJump(board, move,space,piece);

                }
                //checking jump NE
                else if((start.getRow()-end.getRow()==2) && (start.getCell()-end.getCell()== -2)){

                    space = board.getRow(start.getRow()-1).getSpace(start.getCell()+1);
                    return checkSpaceOverJump(board, move, space,piece);

                }
                //checking jump SW
                else if((start.getRow()-end.getRow()== -2) && (start.getCell()-end.getCell()== 2)){

                    space = board.getRow(start.getRow()+1).getSpace(start.getCell()-1);
                    return checkSpaceOverJump(board, move, space,piece);

                }
                //checking jump SE
                else if((start.getRow()-end.getRow()== -2) && (start.getCell()-end.getCell()== -2)){

                    space = board.getRow(start.getRow()+1).getSpace(start.getCell()+1);
                    return checkSpaceOverJump(board, move, space,piece);

                }
                //jumping over more than two rows or columns
                else{
                    return new Message(Message.Type.error, String.format("%s KING: Jump is too big",color));
                }

        }

        //invalid type
        return new Message(Message.Type.error,"What type is this???");

    }


    //helper function to check the space over a jump
    private static Message checkSpaceOverJump(Board board, Move move, Space space, Piece piece){

        //jump over nothing
        if(space.isValid())
            return new Message(Message.Type.error, String.format("%s: Jump over nothing", piece.getColor()));

        //jump over same color piece
        if(space.getPiece().getColor() == piece.getColor())
            return new Message(Message.Type.error, String.format("%s: Jump over same color piece", piece.getColor()));

        //valid move

        //king me
        if(move.getEnd().getRow()==0) {
            piece.kingMe();
            board.destroyPiece(space);
            return new Message(Message.Type.info, String.format("CONGRATS! %s: piece is now KING",piece.getColor()));
        }


        board.destroyPiece(space);
        return new Message(Message.Type.info,String.format("%s: Valid jump", piece.getColor()));

    }

    /**
     * This function returns all the possible jump moves by that color.
     * @param board: the player board
     * @param color: the active color for which we are searching for colors
     * @return the Array List of all possible JUMP moves.
     */
    private static HashMap<Move,Board> getJumpMoves(Board board, Piece.COLOR color){
        HashMap<Move,Board> finalArray = new HashMap<>();
        for (int i = 0; i < Board.SIZE; i++){
            for (int j = 0; j<Board.SIZE; j++){
                Space space = board.getRow(i).getSpace(j);
                if ( space.getPiece() != null && space.getPiece().getColor().equals(color)){
                    Position postition = new Position(i, j);
                    HashMap<Move,Board> movesforJump = getJumpMovesPiece(board,postition);
                    finalArray.putAll(movesforJump);
                }

            }

        }
        return finalArray;
    }




    /**
     * gets all valid moves made for a piece
     * @param board checkers board
     * @param start starting position of a move
     * @return arraylist of all possible jumps made by the piece
     */
    private static HashMap<Move,Board> getJumpMovesPiece(Board board, Position start){


        ArrayList<Move> possibleMoves = new ArrayList<>();
        HashMap<Move,Board> returnObject = new HashMap<>();

        //piece at start position
        Piece atPiece = board.getRow(start.getRow()).getSpace(start.getCell()).getPiece();



        switch(atPiece.getType()){

            case SINGLE:

                if( isWithinBoardBounds( start.getRow() - 2 ) ){
                    //top right jump
                    if( isWithinBoardBounds( start.getCell() + 2) &&
                            //ensuring the destination space does not contain a piece
                            board.getRow(start.getRow() - 2).getSpace(start.getCell() + 2).getPiece() == null){
                        //checking if there is a enemy piece in between
                        Space space = board.getRow(start.getRow()-1).getSpace(start.getCell()+1);
                        if( space.getPiece() != null &&  space.getPiece().getColor() != atPiece.getColor() ) {
                            Move move = new Move(start, new Position(start.getRow() - 2, start.getCell() + 2));
                            Board newBoard = Board.copyBoard(board);
                            newBoard.movePiece(move);
                            //destroying the piece in the previous spot
                            newBoard.getRow(start.getRow() - 1).getSpace(start.getCell()+1).setPiece(null);
                            returnObject.put(move,newBoard);
                        }
                    }
                    //top left jump
                    if( isWithinBoardBounds( start.getCell() - 2 ) &&
                            board.getRow(start.getRow() - 2).getSpace(start.getCell() - 2).getPiece() == null){
                        Space space = board.getRow(start.getRow()-1).getSpace(start.getCell()-1);
                        if( space.getPiece() != null &&  space.getPiece().getColor() != atPiece.getColor() ) {
                            Move move = new Move(start, new Position(start.getRow() - 2, start.getCell() - 2));
                            Board newBoard = Board.copyBoard(board);
                            newBoard.movePiece(move);
                            //destroying the piece in the previous spot
                            newBoard.getRow(start.getRow() - 1).getSpace(start.getCell()-1).setPiece(null);
                            returnObject.put(move,newBoard);
                        }

                    }
                }

                //border col 0
                if(start.getCell() == 0){

                    if(start.getRow() == 3){
                        Space space = board.getRow(1).getSpace(2);
                        Piece piece = board.getRow(2).getSpace(1).getPiece();
                        if (space.isValid() && piece != null && piece.getColor() != atPiece.getColor()){
                            possibleMoves.add(new Move(start, new Position(1,2)));
                        }

                    }
                    if(start.getRow() == 5){
                        Space space = board.getRow(3).getSpace(2);
                        Piece piece = board.getRow(4).getSpace(1).getPiece();
                        if(space.isValid() && piece != null && piece.getColor() != atPiece.getColor()){
                            possibleMoves.add(new Move(start, new Position(3,2)));
                        }
                    }
                    if(start.getRow() == 7){
                        Space space = board.getRow(5).getSpace(2);
                        Piece piece = board.getRow(6).getSpace(1).getPiece();
                        if(space.isValid() && piece != null && piece.getColor() != atPiece.getColor()){
                            possibleMoves.add(new Move(start, new Position(5,2)));
                        }
                    }

                }
                //border col 7
                else if(start.getCell() == 7){

                    if(start.getRow() == 2){
                        Space space = board.getRow(0).getSpace(5);
                        Piece piece = board.getRow(1).getSpace(6).getPiece();
                        if (space.isValid() && piece != null && piece.getColor() != atPiece.getColor()){
                            possibleMoves.add(new Move(start, new Position(0,5)));
                        }

                    }
                    else if(start.getRow() == 4){
                        Space space = board.getRow(2).getSpace(5);
                        Piece piece = board.getRow(3).getSpace(6).getPiece();
                        if(space.isValid() && piece != null && piece.getColor() != atPiece.getColor()){
                            possibleMoves.add(new Move(start, new Position(2,5)));
                        }
                    }
                    else if(start.getRow() == 6){
                        Space space = board.getRow(4).getSpace(5);
                        Piece piece = board.getRow(5).getSpace(6).getPiece();
                        if(space.isValid() && piece != null && piece.getColor() != atPiece.getColor()){
                            possibleMoves.add(new Move(start, new Position(4,5)));
                        }
                    }
                }

                else if (start.getCell() == 1){

                    if(start.getRow() == 2){
                        Space space = board.getRow(0).getSpace(3);
                        Piece piece = board.getRow(1).getSpace(2).getPiece();
                        if (space.isValid() && piece != null && piece.getColor() != atPiece.getColor()){
                            possibleMoves.add(new Move(start, new Position(0,3)));
                        }

                    }
                    else if(start.getRow() == 4){
                        Space space = board.getRow(2).getSpace(3);
                        Piece piece = board.getRow(3).getSpace(2).getPiece();
                        if(space.isValid() && piece != null && piece.getColor() != atPiece.getColor()){
                            possibleMoves.add(new Move(start, new Position(2,3)));
                        }
                    }
                    else if(start.getRow() == 6){
                        Space space = board.getRow(4).getSpace(3);
                        Piece piece = board.getRow(5).getSpace(2).getPiece();
                        if(space.isValid() && piece != null && piece.getColor() != atPiece.getColor()){
                            possibleMoves.add(new Move(start, new Position(4,3)));
                        }
                    }


                }

                else if(start.getCell() == 6){

                    if(start.getRow() == 3){
                        Space space = board.getRow(1).getSpace(4);
                        Piece piece = board.getRow(2).getSpace(5).getPiece();
                        if (space.isValid() && piece != null && piece.getColor() != atPiece.getColor()){
                            possibleMoves.add(new Move(start, new Position(1,4)));
                        }

                    }
                    if(start.getRow() == 5){
                        Space space = board.getRow(3).getSpace(4);
                        Piece piece = board.getRow(4).getSpace(5).getPiece();
                        if(space.isValid() && piece != null && piece.getColor() != atPiece.getColor()){
                            possibleMoves.add(new Move(start, new Position(3,4)));
                        }
                    }
                    if(start.getRow() == 7){
                        Space space = board.getRow(5).getSpace(4);
                        Piece piece = board.getRow(6).getSpace(5).getPiece();
                        if(space.isValid() && piece != null && piece.getColor() != atPiece.getColor()){
                            possibleMoves.add(new Move(start, new Position(5,4)));
                        }
                    }

                }

                // cols 2 and 4
                else if(start.getCell()%2 == 0){

                    int index = start.getRow()%2;
                    if( index == 1 && start.getRow() >= 3){

                        Space space1 = board.getRow(start.getRow()-2).getSpace(start.getCell()-2);
                        Space space2 = board.getRow(start.getRow()-2).getSpace(start.getCell()+2);

                        Piece piece1 = board.getRow(start.getRow()-1).getSpace(start.getCell()-1).getPiece();
                        Piece piece2 = board.getRow(start.getRow()-1).getSpace(start.getCell()+1).getPiece();

                        if(space1.isValid() && piece1 != null && piece1.getColor() != atPiece.getColor()){
                            possibleMoves.add(new Move(start, new Position(start.getRow()-2,start.getCell()-2)));
                        }
                        if(space2.isValid() && piece2 != null && piece2.getColor() != atPiece.getColor()){
                            possibleMoves.add(new Move(start, new Position(start.getRow()-2,start.getCell()+2)));
                        }


                    }


                }
                //cols 3 and 5
                else{

                    int index = start.getRow()%2;
                    if( index == 0 && start.getRow() >= 2){

                        Space space1 = board.getRow(start.getRow()-2).getSpace(start.getCell()-2);
                        Space space2 = board.getRow(start.getRow()-2).getSpace(start.getCell()+2);

                        Piece piece1 = board.getRow(start.getRow()-1).getSpace(start.getCell()-1).getPiece();
                        Piece piece2 = board.getRow(start.getRow()-1).getSpace(start.getCell()+1).getPiece();

                        if(space1.isValid() && piece1 != null && piece1.getColor() != atPiece.getColor()){
                            possibleMoves.add(new Move(start, new Position(start.getRow()-2,start.getCell()-2)));
                        }
                        if(space2.isValid() && piece2 != null && piece2.getColor() != atPiece.getColor()){
                            possibleMoves.add(new Move(start, new Position(start.getRow()-2,start.getCell()+2)));
                        }

                    }

                }

                break;

            case KING:

                if( isWithinBoardBounds( start.getRow() - 2 ) ){
                    //top right jump
                    if( isWithinBoardBounds( start.getCell() + 2) &&
                            //ensuring the destination space does not contain a piece
                            board.getRow(start.getRow() - 2).getSpace(start.getCell() + 2).getPiece() == null){
                        //checking if there is a enemy piece in between
                        Space space = board.getRow(start.getRow()-1).getSpace(start.getCell()+1);
                        if( space.getPiece() != null &&  space.getPiece().getColor() != atPiece.getColor() ) {
                            Move move = new Move(start, new Position(start.getRow() - 2, start.getCell() + 2));
                            Board newBoard = Board.copyBoard(board);
                            newBoard.movePiece(move);
                            //destroying the piece in the previous spot
                            newBoard.getRow(start.getRow() - 1).getSpace(start.getCell()+1).setPiece(null);
                            returnObject.put(move,newBoard);
                        }
                    }
                    //top left jump
                    if( isWithinBoardBounds( start.getCell() - 2 ) &&
                            board.getRow(start.getRow() - 2).getSpace(start.getCell() - 2).getPiece() == null){
                        Space space = board.getRow(start.getRow()-1).getSpace(start.getCell()-1);
                        if( space.getPiece() != null &&  space.getPiece().getColor() != atPiece.getColor() ) {
                            Move move = new Move(start, new Position(start.getRow() - 2, start.getCell() - 2));
                            Board newBoard = Board.copyBoard(board);
                            newBoard.movePiece(move);
                            //destroying the piece in the previous spot
                            newBoard.getRow(start.getRow() - 1).getSpace(start.getCell()-1).setPiece(null);
                            returnObject.put(move,newBoard);
                        }

                    }
                }



                //bottom
                if( isWithinBoardBounds( start.getRow() + 2 ) ){
                    //top right jump
                    if( isWithinBoardBounds( start.getCell() + 2) &&
                            //ensuring the destination space does not contain a piece
                            board.getRow(start.getRow() + 2).getSpace(start.getCell() + 2).getPiece() == null){
                        //checking if there is a enemy piece in between
                        Space space = board.getRow(start.getRow()+1).getSpace(start.getCell()+1);
                        if( space.getPiece() != null &&  space.getPiece().getColor() != atPiece.getColor() ) {
                            Move move = new Move(start, new Position(start.getRow() + 2, start.getCell() + 2));
                            Board newBoard = Board.copyBoard(board);
                            newBoard.movePiece(move);
                            //destroying the piece in the previous spot
                            newBoard.getRow(start.getRow() + 1).getSpace(start.getCell()+1).setPiece(null);
                            returnObject.put(move,newBoard);
                        }
                    }
                    //top left jump
                    if( isWithinBoardBounds( start.getCell() - 2 ) &&
                            board.getRow(start.getRow() + 2).getSpace(start.getCell() - 2).getPiece() == null){
                        Space space = board.getRow(start.getRow()+1).getSpace(start.getCell()-1);
                        if( space.getPiece() != null &&  space.getPiece().getColor() != atPiece.getColor() ) {
                            Move move = new Move(start, new Position(start.getRow() + 2, start.getCell() - 2));
                            Board newBoard = Board.copyBoard(board);
                            newBoard.movePiece(move);
                            //destroying the piece in the previous spot
                            newBoard.getRow(start.getRow() + 1).getSpace(start.getCell()-1).setPiece(null);
                            returnObject.put(move,newBoard);
                        }

                    }
                }

                if( isWithinBoardBounds(Board.SIZE + start.getCell())) {

                    // column checking for king jumps
                    switch (start.getCell()) {

                        //from col 0 and row n
                        case 0:
                            if (start.getRow() == 1) {

                                Space space = board.getRow(3).getSpace(2);
                                Piece piece = board.getRow(2).getSpace(1).getPiece();

                                if (space.isValid() && piece != null && piece.getColor() != atPiece.getColor()) {
                                    possibleMoves.add(new Move(start, new Position(start.getRow() + 2, start.getCell() + 2)));
                                }

                            } else if (start.getRow() == 7) {

                                Space space = board.getRow(5).getSpace(2);
                                Piece piece = board.getRow(6).getSpace(1).getPiece();

                                if (space.isValid() && piece != null && piece.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(start.getRow() - 2, start.getCell() + 2)));

                            } else {

                                Space space1 = board.getRow(start.getRow() - 2).getSpace(start.getCell() + 2);
                                Piece piece1 = board.getRow(start.getRow() - 1).getSpace(start.getCell() - 1).getPiece();

                                Space space2 = board.getRow(start.getRow() + 2).getSpace(start.getCell() + 2);
                                Piece piece2 = board.getRow(start.getRow() + 1).getSpace(start.getCell() + 1).getPiece();

                                if (space1.isValid() && piece1 != null && piece1.getColor() != atPiece.getColor()) {
                                    possibleMoves.add(new Move(start, new Position(start.getRow() - 2, start.getCell() + 2)));
                                }
                                if (space2.isValid() && piece2 != null && piece2.getColor() != atPiece.getColor()) {
                                    possibleMoves.add(new Move(start, new Position(start.getRow() + 2, start.getCell() + 2)));
                                }

                            }

                            break;

                        case 7:

                            if (start.getRow() == 0) {
                                Space space = board.getRow(2).getSpace(5);
                                Piece piece = board.getRow(1).getSpace(6).getPiece();

                                if (space.isValid() && piece != null && piece.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(2, 5)));

                            } else if (start.getRow() == 6) {

                                Space space = board.getRow(4).getSpace(5);
                                Piece piece = board.getRow(5).getSpace(6).getPiece();

                                if (space.isValid() && piece != null && piece.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(4, 5)));

                            } else {

                                Space space1 = board.getRow(start.getRow() - 2).getSpace(start.getCell() - 2);
                                Space space2 = board.getRow(start.getRow() + 2).getSpace(start.getCell() - 2);

                                Piece piece1 = board.getRow(start.getRow() - 1).getSpace(start.getCell() - 1).getPiece();
                                Piece piece2 = board.getRow(start.getRow() + 1).getSpace(start.getCell() - 1).getPiece();

                                if (space1.isValid() && piece1 != null && piece1.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(start.getRow() - 2, start.getCell() - 2)));

                                if (space2.isValid() && piece2 != null && piece2.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(start.getRow() + 2, start.getCell() - 2)));

                            }

                            break;

                        case 1:

                            if (start.getRow() == 0) {

                                Space space = board.getRow(2).getSpace(3);
                                Piece piece = board.getRow(1).getSpace(2).getPiece();

                                if (space.isValid() && piece != null && piece.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(2, 3)));

                            } else if (start.getRow() == 6) {

                                Space space = board.getRow(4).getSpace(3);
                                Piece piece = board.getRow(5).getSpace(2).getPiece();

                                if (space.isValid() && piece != null && piece.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(4, 3)));

                            } else {

                                Space space1 = board.getRow(start.getRow() - 2).getSpace(start.getCell() + 2);
                                Piece piece1 = board.getRow(start.getRow() - 1).getSpace(start.getCell() + 1).getPiece();

                                Space space2 = board.getRow(start.getRow() + 2).getSpace(start.getCell() + 2);
                                Piece piece2 = board.getRow(start.getRow() + 1).getSpace(start.getCell() + 1).getPiece();

                                if (space1.isValid() && piece1 != null && piece1.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(start.getRow() - 2, start.getCell() + 2)));

                                if (space2.isValid() && piece2 != null && piece2.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(start.getRow() + 2, start.getCell() + 2)));

                            }

                            break;

                        case 6:

                            if (start.getRow() == 1) {

                                Space space = board.getRow(3).getSpace(4);
                                Piece piece = board.getRow(2).getSpace(5).getPiece();

                                if (space.isValid() && piece != null && piece.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(3, 4)));

                            } else if (start.getRow() == 7) {

                                Space space = board.getRow(5).getSpace(4);
                                Piece piece = board.getRow(6).getSpace(5).getPiece();

                                if (space.isValid() && piece != null && piece.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(5, 4)));

                            } else {

                                Space space1 = board.getRow(start.getRow() - 2).getSpace(start.getCell() - 2);
                                Piece piece1 = board.getRow(start.getRow() - 1).getSpace(start.getCell() - 1).getPiece();

                                Space space2 = board.getRow(start.getRow() + 2).getSpace(start.getCell() - 2);
                                Piece piece2 = board.getRow(start.getRow() + 1).getSpace(start.getCell() - 1).getPiece();

                                if (space1.isValid() && piece1 != null && piece1.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(start.getRow() - 2, start.getCell() - 2)));

                                if (space2.isValid() && piece2 != null && piece2.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(start.getRow() + 2, start.getCell() - 2)));
                            }
                            break;
                        case 2:
                        case 4:

                            if (start.getRow() == 1) {
                                Space space1 = board.getRow(start.getRow() + 2).getSpace(start.getCell() - 2);
                                Space space2 = board.getRow(start.getRow() + 2).getSpace(start.getCell() + 2);

                                Piece piece1 = board.getRow(start.getRow() + 1).getSpace(start.getCell() - 1).getPiece();
                                Piece piece2 = board.getRow(start.getRow() + 1).getSpace(start.getCell() + 1).getPiece();

                                if (space1.isValid() && piece1 != null && piece1.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(start.getRow() + 2, start.getCell() - 2)));

                                if (space2.isValid() && piece2 != null && piece2.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(start.getRow() + 2, start.getCell() + 2)));


                            } else if (start.getRow() == 7) {

                                Space space1 = board.getRow(start.getRow() - 2).getSpace(start.getCell() - 2);
                                Space space2 = board.getRow(start.getRow() - 2).getSpace(start.getCell() + 2);

                                Piece piece1 = board.getRow(start.getRow() - 1).getSpace(start.getCell() - 1).getPiece();
                                Piece piece2 = board.getRow(start.getRow() - 1).getSpace(start.getCell() + 1).getPiece();

                                if (space1.isValid() && piece1 != null && piece1.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(start.getRow() - 2, start.getCell() - 2)));

                                if (space2.isValid() && piece2 != null && piece2.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(start.getRow() - 2, start.getCell() + 2)));

                            } else {
                                int row = start.getRow();
                                int col = start.getCell();
                                Space space1 = board.getRow(row - 2).getSpace(col - 2);
                                Space space2 = board.getRow(row - 2).getSpace(col + 2);
                                Space space3 = board.getRow(row + 2).getSpace(col - 2);
                                Space space4 = board.getRow(row + 2).getSpace(col + 2);


                                Piece piece1, piece2, piece3, piece4;
                                piece1 = board.getRow(row - 1).getSpace(col - 1).getPiece();
                                piece2 = board.getRow(row - 1).getSpace(col + 1).getPiece();
                                piece3 = board.getRow(row + 1).getSpace(col - 1).getPiece();
                                piece4 = board.getRow(row + 1).getSpace(col + 1).getPiece();


                                if (space1.isValid() && piece1 != null && piece1.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(row - 2, col - 2)));

                                if (space2.isValid() && piece2 != null && piece2.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(row - 2, col + 2)));

                                if (space3.isValid() && piece3 != null && piece3.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(row + 2, col - 2)));

                                if (space4.isValid() && piece4 != null && piece4.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(row + 2, col + 2)));

                            }

                            break;
                        case 3:
                        case 5:

                            int row = start.getRow();
                            int col = start.getCell();

                            if (row == 0) {

                                Space space1 = board.getRow(start.getRow() + 2).getSpace(start.getCell() - 2);
                                Space space2 = board.getRow(start.getRow() + 2).getSpace(start.getCell() + 2);

                                Piece piece1 = board.getRow(start.getRow() + 1).getSpace(start.getCell() - 1).getPiece();
                                Piece piece2 = board.getRow(start.getRow() + 1).getSpace(start.getCell() + 1).getPiece();

                                if (space1.isValid() && piece1 != null && piece1.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(start.getRow() + 2, start.getCell() - 2)));

                                if (space2.isValid() && piece2 != null && piece2.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(start.getRow() + 2, start.getCell() + 2)));


                            } else if (row == 6) {

                                Space space1 = board.getRow(start.getRow() - 2).getSpace(start.getCell() - 2);
                                Space space2 = board.getRow(start.getRow() - 2).getSpace(start.getCell() + 2);

                                Piece piece1 = board.getRow(start.getRow() - 1).getSpace(start.getCell() - 1).getPiece();
                                Piece piece2 = board.getRow(start.getRow() - 1).getSpace(start.getCell() + 1).getPiece();

                                if (space1.isValid() && piece1 != null && piece1.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(start.getRow() - 2, start.getCell() - 2)));

                                if (space2.isValid() && piece2 != null && piece2.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(start.getRow() - 2, start.getCell() + 2)));


                            } else {

                                Space space1 = board.getRow(row - 2).getSpace(col - 2);
                                Space space2 = board.getRow(row - 2).getSpace(col + 2);
                                Space space3 = board.getRow(row + 2).getSpace(col - 2);
                                Space space4 = board.getRow(row + 2).getSpace(col + 2);


                                Piece piece1, piece2, piece3, piece4;
                                piece1 = board.getRow(row - 1).getSpace(col - 1).getPiece();
                                piece2 = board.getRow(row - 1).getSpace(col + 1).getPiece();
                                piece3 = board.getRow(row + 1).getSpace(col - 1).getPiece();
                                piece4 = board.getRow(row + 1).getSpace(col + 1).getPiece();


                                if (space1.isValid() && piece1 != null && piece1.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(row - 2, col - 2)));

                                if (space2.isValid() && piece2 != null && piece2.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(row - 2, col + 2)));

                                if (space3.isValid() && piece3 != null && piece3.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(row + 2, col - 2)));

                                if (space4.isValid() && piece4 != null && piece4.getColor() != atPiece.getColor())
                                    possibleMoves.add(new Move(start, new Position(row + 2, col + 2)));


                            }
                            break;

                        default:
                            System.out.println("ERROR: NOT A VALID POSITION FOR PIECE: " + atPiece);


                    }
                }



        }
        return returnObject ;

    }


    /**
     * Generating end positions for the specific start position.
     * @param startPosition: The start position for which we want to find out the end positions
     * @return List of the positions which can end pos.
     */
    public static ArrayList<Position> genarateEndPosition(Position startPosition){
        ArrayList<Position> allEndPositions = new ArrayList<>();
        if( isWithinBoardBounds( startPosition.getRow() - 1 ) ){
            if( isWithinBoardBounds( startPosition.getCell() + 1)){
                allEndPositions.add( new Position(startPosition.getRow()-1,startPosition.getCell()+1) );
            }
            if( isWithinBoardBounds( startPosition.getCell() - 1 )){
                allEndPositions.add( new Position(startPosition.getRow()-1,startPosition.getCell()-1) );
            }
        }
        //if its a king it can go backwards
        if( isWithinBoardBounds( startPosition.getRow() + 1 ) ){
            if( isWithinBoardBounds( startPosition.getCell() + 1)){
                allEndPositions.add( new Position(startPosition.getRow()+1,startPosition.getCell()+1) );
            }
            if( isWithinBoardBounds( startPosition.getCell() - 1 )){
                allEndPositions.add( new Position(startPosition.getRow()+1,startPosition.getCell()-1) );
            }
        }


        return allEndPositions;

    }

    /**
     * checks if a number is within the board range
     * @param bound the bounded range
     * @return if within the range
     */
    private static boolean isWithinBoardBounds( int bound ){
        return ( bound >= 0 && bound < Board.SIZE );
    }

    /**
     * ArrayList of the valid moves that can be made by that specific position.
     * @param startPos: Start position
     * @param endPos: the end position
     * @param board: the board for which we want to generate the AI Moves.
     * @return Return the arrayList of the moves that can be made by the AI player.
     */
    public static ArrayList<Move> generateAiMoves(Position startPos, Position endPos, Board board){
        Move moveMan = new Move(startPos,endPos);
        ArrayList<Move> moveList = new ArrayList<>();

        if (( isValidSingleMove(board,moveMan  ).getType() != Message.Type.error) ){
            moveList.add(moveMan);
        }
        return moveList;

    }


    /**
     * Functions that generate the board with the move made by their
     * @param board the game board
     * @return Return the board which will have the move that is being already made by the AI Player
     */
    public static Board aiMove(Board board){
        Board editedBoard = Board.copyBoard(board);

        //first checking for jump moves ( which must be made )
        HashMap<Move,Board> jumps = getJumpMoves(board, Piece.COLOR.WHITE);
        if(jumps.size() > 0){
            //then just select the first, ai is dumb :)
            return jumps.get( jumps.keySet().iterator().next());

        }

        //gets all the AI player pieces
        ArrayList<Position> allStartingPos = new ArrayList<>();
        for (int i = 0; i < Board.SIZE; i++){
            for (int j = 0; j< Board.SIZE; j++){
                Space space = board.getRow(i).getSpace(j);
                if (space.getPiece() != null && space.getPiece().getColor().equals(Piece.COLOR.WHITE)){
                    Position position  = new Position(i, j);
                    allStartingPos.add(position);
                }

            }
        }

        //TODO Generate end Position for that specifiec position and then check if its a valid Move
        //TODO if it is a valid move, add it to the list;

        ArrayList<Move> allPossibleMoves = new ArrayList<>();

        for (int x = 0; x < allStartingPos.size(); x++){
            Position startPos = allStartingPos.get(x);
            ArrayList<Position> endPositionList = genarateEndPosition(startPos);
            for (int l = 0; l<endPositionList.size(); l++){
                ArrayList<Move> possibleMoveListFromStartandEnd = generateAiMoves(startPos, endPositionList.get(l), board );
                allPossibleMoves.addAll(possibleMoveListFromStartandEnd);
            }

        }

        Random rand = new Random();
        System.out.println(allPossibleMoves.size());

        //just return the board if no moves
        if(allPossibleMoves.size() == 0 ){
            return board;
        }
        Move nextMove = allPossibleMoves.get(rand.nextInt(allPossibleMoves.size()));

        editedBoard.movePiece(nextMove);

        return editedBoard;
    }



}
