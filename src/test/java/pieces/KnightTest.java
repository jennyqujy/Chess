package pieces;

import chessboard.Board;
import chessboard.Tile;
import junit.framework.TestCase;
import utils.Constants;
import utils.PieceColor;

public class KnightTest extends TestCase {
    private Piece knight;
    private Board board;
    private Tile tile;

    public void setUp() throws Exception {
        super.setUp();
        board = new Board
                .BoardBuilder()
                .setBoardconfig(new Tile[Constants.boardSize][Constants.boardSize])
                .build();
        tile = new Tile(6,6);
        knight = new Knight(PieceColor.white());
        tile.setPieceOnTile(knight);
    }

    public void tearDown() throws Exception {
    }

    public void testGenerateLegalMoves() throws Exception {
        assertEquals(true, knight.generateLegalMoves(board).contains(board.getTile(4,5)));
        assertEquals(true,knight.generateLegalMoves(board).contains(board.getTile(5,4)));
        assertEquals(false,knight.generateLegalMoves(board).contains(board.getTile(1,3)));
        assertEquals(false,knight.generateLegalMoves(board).contains(board.getTile(4,4)));
    }

    public void testGetPieceID() throws Exception {
        assertEquals("n",knight.getPieceID());
    }

}