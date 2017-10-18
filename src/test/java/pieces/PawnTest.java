package pieces;

import chessboard.Board;
import chessboard.Tile;
import junit.framework.TestCase;
import utils.Constants;
import utils.PieceColor;

public class PawnTest extends TestCase {

    private Pawn piece;
    private Board board;
    private Tile tile;

    public void setUp() throws Exception {
        super.setUp();
        board = new Board
                .BoardBuilder()
                .setBoardconfig(new Tile[Constants.boardSize][Constants.boardSize])
                .build();
        piece = new Pawn(PieceColor.black());
        tile = new Tile(0,0,piece);
        board.addTile(tile);
    }

    public void tearDown() throws Exception {
    }

    public void testGenerateLegalMoves() throws Exception {
        for (int i=1;i<3;i++) {
            board.addTile(new Tile(0, i));
        }
        assertTrue(piece.generateLegalMoves(board).contains(board.getTile(0, 1)));
        if (piece.getFirstMove()) {
            assertTrue(piece.generateLegalMoves(board).contains(board.getTile(0, 2)));
            assertFalse(piece.generateLegalMoves(board).size() <= 0);
        }
        Tile tile1 = new Tile(1, 1, new Pawn(PieceColor.white()));
        board.addTile(tile1);
        assertTrue(piece.generateLegalMoves(board).contains(board.getTile(1, 1)));
        Tile tile2 = new Tile(0, 1, new Pawn(PieceColor.black()));
        piece.moveTo(tile2);
        assertFalse(piece.generateLegalMoves(board).contains(board.getTile(0, 3)));
    }

    public void testGetPieceID() throws Exception {
        assertEquals("p",piece.getPieceID());
    }

}