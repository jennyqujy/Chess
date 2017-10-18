package pieces;

import chessboard.Board;
import chessboard.Tile;
import junit.framework.TestCase;
import utils.Constants;
import utils.PieceColor;
import utils.PieceType;

public class PrimeTest extends TestCase {
    private Piece piece;
    private Board board;
    private Tile tile;

    public void setUp() throws Exception {
        super.setUp();
        board = new Board
                .BoardBuilder()
                .setBoardconfig(new Tile[Constants.boardSize][Constants.boardSize])
                .build();
        tile = new Tile(3,3);
        piece = new Prime(PieceColor.white());
        tile.setPieceOnTile(piece);
    }

    public void testGetPieceType() throws Exception {
        assertEquals(PieceType.PRIME,piece.getPieceType());
    }

    public void testGetPieceID() throws Exception {
        assertEquals("m",piece.getPieceID());
    }

    public void testGenerateLegalMoves() throws Exception {
        assertEquals(true, piece.generateLegalMoves(board).contains(board.getTile(4,4)));
        assertEquals(true,piece.generateLegalMoves(board).contains(board.getTile(2,4)));
        assertEquals(false,piece.generateLegalMoves(board).contains(board.getTile(1,3)));
        assertEquals(false,piece.generateLegalMoves(board).contains(board.getTile(3,4)));
    }

}