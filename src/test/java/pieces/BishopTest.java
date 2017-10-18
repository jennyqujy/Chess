package pieces;

import chessboard.Board;
import chessboard.Tile;
import junit.framework.TestCase;
import utils.Constants;
import utils.PieceColor;
import utils.PieceType;

public class BishopTest extends TestCase {
    private Piece piece;
    private Board board;
    private Tile tile;

    public void setUp() throws Exception {
        super.setUp();
        board = new Board
                .BoardBuilder()
                .setBoardconfig(new Tile[Constants.boardSize][Constants.boardSize])
                .build();
        tile = new Tile(0,6);
        piece = new Bishop(PieceColor.white());
        tile.setPieceOnTile(piece);
    }

    public void testGetPieceType() throws Exception {
        assertEquals(PieceType.BISHOP,piece.getPieceType());
    }

    public void testGenerateLegalMoves() throws Exception {
        assertEquals(true, piece.generateLegalMoves(board).contains(board.getTile(1,5)));
        assertEquals(true,piece.generateLegalMoves(board).contains(board.getTile(2,4)));
        assertEquals(false,piece.generateLegalMoves(board).contains(board.getTile(1,3)));
        assertEquals(false,piece.generateLegalMoves(board).contains(board.getTile(4,4)));
    }

    public void testGetPieceID() throws Exception {
        assertEquals("b",piece.getPieceID());
    }

}