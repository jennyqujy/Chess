package pieces;

import chessboard.Board;
import chessboard.Tile;
import junit.framework.TestCase;
import utils.Constants;
import utils.PieceColor;

public class RookTest extends TestCase {
    private Rook rook;
    private Board board;
    private Tile tile;

    public void setUp() throws Exception {
        super.setUp();
        board = new Board
                .BoardBuilder()
                .setBoardconfig(new Tile[Constants.boardSize][Constants.boardSize])
                .build();
        rook = new Rook(PieceColor.black());
        tile = new Tile(0,0, rook);
        board.addTile(tile);
        tile.setPieceOnTile(rook);
    }

    public void tearDown() throws Exception {
    }

    public void testGenerateLegalMoves() throws Exception {
        assertEquals(false, rook.generateLegalMoves(board).contains(board.getTile(3,6)));
        assertEquals(true, rook.generateLegalMoves(board).contains(board.getTile(0,3)));
        assertEquals(false, rook.generateLegalMoves(board).contains(board.getTile(1,3)));
        assertEquals(false, rook.generateLegalMoves(board).contains(board.getTile(4,4)));
    }

    public void testGetPieceID() throws Exception {
        assertEquals("r",rook.getPieceID());
    }

}