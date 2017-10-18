package pieces;

import chessboard.Board;
import chessboard.Tile;
import junit.framework.TestCase;
import utils.Constants;
import utils.PieceColor;

public class QueenTest extends TestCase {

    private Piece queen;
    private Board board;
    private Tile tile;

    public void setUp() throws Exception {
        super.setUp();
        board = new Board
                .BoardBuilder()
                .setBoardconfig(new Tile[Constants.boardSize][Constants.boardSize])
                .build();tile = new Tile(6,6);
        queen = new Queen(PieceColor.white());
        tile.setPieceOnTile(queen);
    }

    public void tearDown() throws Exception {
    }

    public void testGetPieceID() throws Exception {
        assertEquals("q",queen.getPieceID());
    }

    public void testGenerateLegalMoves() throws Exception {
        assertEquals(true, queen.generateLegalMoves(board).contains(board.getTile(6,5)));
        assertEquals(true,queen.generateLegalMoves(board).contains(board.getTile(6,1)));
        assertEquals(false,queen.generateLegalMoves(board).contains(board.getTile(1,3)));
        assertEquals(true,queen.generateLegalMoves(board).contains(board.getTile(4,4)));
    }

}