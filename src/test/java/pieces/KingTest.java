package pieces;

import chessboard.Board;
import chessboard.Tile;
import junit.framework.TestCase;
import utils.Constants;
import utils.PieceColor;

import java.util.List;

public class KingTest extends TestCase {

    private King king;
    private Board board;
    private Tile tile;

    public void setUp() throws Exception {
        super.setUp();
        board = new Board
                .BoardBuilder()
                .setBoardconfig(new Tile[Constants.boardSize][Constants.boardSize])
                .build();
        king = new King(PieceColor.black());
        tile = new Tile(0,0, king);
        board.addTile(tile);
        tile.setPieceOnTile(king);
    }

    public void tearDown() throws Exception {
    }

    public void testGenerateLegalMoves() throws Exception {
        assertTrue(king.generateLegalMoves(board).contains(board.getTile(0,1)));
        assertTrue(king.generateLegalMoves(board).contains(board.getTile(1,0)));
        assertTrue(king.generateLegalMoves(board).contains(board.getTile(1,1)));
        assertFalse(king.generateLegalMoves(board).contains(board.getTile(0,0)));
        assertFalse(king.generateLegalMoves(board).contains(board.getTile(2,2)));
    }

    public void testIsInCheckMate1() throws Exception {
        board.addTile(new Tile(7,7, new King(PieceColor.white())));
        board.addTile(new Tile(6,6, new Bishop(PieceColor.white())));
        Rook r1 = new Rook(PieceColor.white());
        Rook r2 = new Rook(PieceColor.white());
        board.addTile(new Tile(7,1,r1));
        board.addTile(new Tile(1,7, r2));
        assertTrue(king.isInCheckMate(board,r1,board.getBlacks(),board.getWhites()));
        assertTrue(king.isInCheckMate(board,r2,board.getBlacks(),board.getWhites()));
    }

    public void testIsInCheckMate2() throws Exception {
        King whiteKing = new King(PieceColor.white());
        board.addTile(new Tile(7,7, whiteKing));
        board.addTile(new Tile(1,1, new Bishop(PieceColor.black())));
        Rook r1 = new Rook(PieceColor.black());
        board.addTile(new Tile(6,1,r1));
        assertFalse(whiteKing.isInCheckMate(board,r1,board.getWhites(),board.getBlacks()));
        Rook r2 = new Rook(PieceColor.black());
        board.addTile(new Tile(1,6, r2));
        assertTrue(whiteKing.isInCheckMate(board,r1,board.getWhites(),board.getBlacks()));
        assertTrue(whiteKing.isInCheckMate(board,r2,board.getWhites(),board.getBlacks()));
    }

    public void testIsInStaleMate() throws Exception{
        board.addTile(new Tile(7,7, new King(PieceColor.white())));
        board.addTile(new Tile(7,1, new Rook(PieceColor.white())));
        board.addTile(new Tile(1,7, new Rook(PieceColor.white())));
        assertTrue(king.isInStaleMate(board,board.getBlacks(),board.getWhites()));
    }

    public void testSimulateDefense() throws Exception{
        board.addTile(new Tile(7,7, new King(PieceColor.white())));
        Queen q = new Queen(PieceColor.black());
        board.addTile(new Tile(3,5,q));
        Rook r1 = new Rook(PieceColor.white());
        board.addTile(new Tile(7,1,r1));
        Rook r2 = new Rook(PieceColor.white());
        board.addTile(new Tile(1,7, r2));
        assertTrue(king.simulateDefense(board,board.getWhites(),q));
    }

    public void testGetPieceID() throws Exception {
        assertEquals("k",king.getPieceID());
    }

}