package pieces;

import chessboard.Board;
import chessboard.Tile;
import junit.framework.TestCase;
import utils.Constants;
import utils.PieceColor;

public class PieceTest extends TestCase {
    private Piece piece;
    private Board board;
    private Tile tile;

    public void setUp() throws Exception {
        super.setUp();
        board = new Board
                .BoardBuilder()
                .setBoardconfig(new Tile[Constants.boardSize][Constants.boardSize])
                .build();
        piece = new King(PieceColor.black());
        tile = new Tile(0,0, piece);
        board.addTile(tile);
        tile.setPieceOnTile(piece);
    }

    public void tearDown() throws Exception {
    }

    public void testGetTile() throws Exception{
        assertNotNull(piece.getTile());
        assertEquals(0,piece.getTile().x);
        assertEquals(0,piece.getTile().y);
    }

    public void testGetPieceID() throws Exception {
        assertEquals("k",piece.getPieceID());
    }

    public void testGenerateLegalMoves() throws Exception {
        assertEquals(true, piece.generateLegalMoves(board).contains(board.getTile(0,1)));
        assertEquals(true, piece.generateLegalMoves(board).contains(board.getTile(1,0)));
        assertEquals(false, piece.generateLegalMoves(board).contains(board.getTile(0,3)));
        assertEquals(false, piece.generateLegalMoves(board).contains(board.getTile(0,6)));
    }

    public void testGetFirstMove() throws Exception {
        assertEquals(true,piece.getFirstMove());
        piece.moveTo(new Tile(1,0));
        assertEquals(false,piece.getFirstMove());
        piece.moveTo(new Tile(1,1));
        assertEquals(false,piece.getFirstMove());
    }

    public void testGetColor() throws Exception {
        assertNotNull(piece.getColor());
    }

    public void testIsBlack() throws Exception {
        assertTrue(piece.isBlack());
        assertEquals(PieceColor.ColorType.BLACK, piece.getColor().getColorType());
    }

    public void testIsOpponent() throws Exception {
        Piece op1 = new Queen(PieceColor.black());
        Piece op2 = new King(PieceColor.white());
        assertFalse(piece.isOpponent(op1));
        assertTrue(piece.isOpponent(op2));
    }

    public void testIsLegalMove() throws Exception {
        assertEquals(true, piece.isLegalMove(0,5,board));
        assertEquals(true, piece.isLegalMove(0,4,board));
        assertEquals(false, piece.isLegalMove(-1,3,board));
    }

    public void testMoveTo() throws Exception {
        piece.moveTo(board.getTile(0,5));
        assertNotNull(board.getTile(0,5).getTilePiece());
    }

    public void testSetTile() throws Exception {
        board = board.setStandardPiecePlacement();
        assertFalse(board.getTile(1,2).hasPiece());
        assertTrue(tile.hasPiece());
    }

    public void testGetLegalMoves() throws Exception {
        assertTrue(piece.getLegalMoves(Constants.ChessDirection.DOWN,board).contains(board.getTile(0,1)));
        assertFalse(piece.getLegalMoves(Constants.ChessDirection.UP,board).contains(board.getTile(0,0)));
        assertTrue(piece.getLegalMoves(Constants.ChessDirection.RIGHT,board).contains(board.getTile(1,0)));
        assertTrue(piece.getLegalMoves(Constants.ChessDirection.DOWNRIGHT,board).contains(board.getTile(1,1)));
    }

    public void testGetImageID() throws Exception {
        assertEquals("bk.png",piece.getImageID());
    }

    public void testGetIcon() throws Exception {
        assertNotNull(piece.getIcon());
    }

}