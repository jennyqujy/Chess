package chessboard;

import junit.framework.TestCase;
import pieces.Bishop;
import pieces.King;
import pieces.Rook;
import utils.Constants;
import utils.PieceColor;
import utils.Player.*;
import java.awt.*;
import java.util.Random;

public class BoardTest extends TestCase {

    private Board board;

    public void setUp() throws Exception {
        super.setUp();
        board = new Board
                .BoardBuilder()
                .setBoardconfig(new Tile[Constants.boardSize][Constants.boardSize])
                .build();
    }

    public void tearDown() throws Exception {
    }

    public void testBoardComponents() throws Exception {
        assertNotNull(board.getChessBoard());
        assertEquals(Constants.boardSize,board.getTiles().length);
        assertEquals(Constants.boardSize,board.getTiles()[0].length);
    }

    public void testGetTile() throws Exception{
        board = board.setStandardPiecePlacement();
        assertNull(board.getTile(-1,-1));
        Random rand = new Random();
        int x = rand.nextInt(8), y = rand.nextInt(8);
        if (board.isLegalCoordinate(x,y))
            assertNotNull(board.getTile(x,y));
        else
            assertNull(board.getTile(x,y));

    }

    public void testBoardPieces() throws Exception {
        board = board.setStandardPiecePlacement();
        assertNotNull(board.getBlackKing());
        assertNotNull(board.getWhiteKing());
        assertEquals(16,board.getBlacks().size());
        assertEquals(16,board.getWhites().size());
    }

    public void testGetChessBoard() throws Exception {
        assertNotNull(board.getChessBoard());
    }

    public void testGetTiles() throws Exception {
        assertNotNull(board.getTiles());
        assertEquals(Constants.boardSize,board.getTiles().length);
        assertEquals(Constants.boardSize,board.getTiles()[0].length);
    }

    public void testGetBlackKing() throws Exception {
        board = board.setStandardPiecePlacement();
        assertNotNull(board.getBlackKing());
    }

    public void testGetWhiteKing() throws Exception {
        board = board.setStandardPiecePlacement();
        assertNotNull(board.getWhiteKing());
    }

    public void testGetBlacks() throws Exception {
        assertNotNull(board.getBlacks());
    }

    public void testGetWhites() throws Exception {
        assertNotNull(board.getWhites());
    }

    public void testIsLegalCoordinate() throws Exception {
        Random rand = new Random();
        int x = rand.nextInt(8), y = rand.nextInt(8);
        assertEquals(true,board.isLegalCoordinate(x,y));
        assertEquals(false,board.isLegalCoordinate(-1,-1));
        assertEquals(false,board.isLegalCoordinate(8,8));
    }

//    public void testShow() throws Exception {
//        board.show();
//        assertNotNull(board.getFrame());
//        assertEquals(new Dimension(800,822),board.getFrame().getSize());
//        assertTrue(board.getFrame().isVisible());
//        assertFalse(board.getFrame().isResizable());
//        assertTrue(board.getFrame().isDisplayable());
//    }

    public void testIsStaleMate() throws Exception {
        board.addTile(new Tile(0,0, new King(PieceColor.white())));
        board.addTile(new Tile(7,7, new King(PieceColor.black())));
        board.addTile(new Tile(1,7, new Rook(PieceColor.black())));
        board.addTile(new Tile(7,1, new Rook(PieceColor.black())));
        assertTrue(board.isStaleMate());
    }

    public void testIsCheckMate1() throws Exception {
        board.addTile(new Tile(0,0, new King(PieceColor.black())));
        board.addTile(new Tile(7,7, new King(PieceColor.white())));
        assertFalse(board.isCheckMate());
        board.addTile(new Tile(6,6, new Bishop(PieceColor.white())));
        board.addTile(new Tile(7,1, new Rook(PieceColor.white())));
        board.addTile(new Tile(1,7, new Rook(PieceColor.white())));
        assertTrue(board.isCheckMate());
    }

    public void testIsCheckMate2() throws Exception {
        King whiteKing = new King(PieceColor.white());
        board.addTile(new Tile(0,0, new King(PieceColor.black())));
        board.addTile(new Tile(7,7, whiteKing));
        assertFalse(board.isCheckMate());
        board.addTile(new Tile(1,1, new Bishop(PieceColor.black())));
        board.addTile(new Tile(6,0, new Rook(PieceColor.black())));
        board.addTile(new Tile(0,6, new Rook(PieceColor.black())));
        assertTrue(board.isCheckMate());
    }


    public void testSwitchPlayerTurn() throws Exception {
        assertEquals(PlayerName.PLAYER_WHITE,board.getPlayerName());
        board.switchPlayerTurn();
        assertEquals(PlayerName.PLAYER_BLACK,board.getPlayerName());
        board.switchPlayerTurn();
        assertEquals(PlayerName.PLAYER_WHITE,board.getPlayerName());
    }

    public void testRemoveColoredPieces() throws Exception {
        Tile tile = new Tile(0,0, new King(PieceColor.black()));
        board.addTile(tile);
        assertEquals(1,board.getBlacks().size());
        board.removeColorPieces(tile);
        assertEquals(0,board.getBlacks().size());

        Tile tile1 = new Tile(0,0, new King(PieceColor.white()));
        board.addTile(tile1);
        assertEquals(1,board.getWhites().size());
        board.removeColorPieces(tile1);
        assertEquals(0,board.getWhites().size());
    }

    public void testSelectPiece() throws Exception {
        assertEquals(PlayerAction.SELECT, board.getPlayerAction());
        Tile tile = new Tile(0,0, new King(PieceColor.black()));
        board.selectPiece(tile);
        assertEquals(PlayerAction.MOVE, board.getPlayerAction());
    }

    public void testIsEndGame() throws Exception {
        board.addTile(new Tile(0,0, new King(PieceColor.black())));
        board.addTile(new Tile(7,7, new King(PieceColor.white())));
        board.addTile(new Tile(6,6, new Bishop(PieceColor.white())));
        board.addTile(new Tile(7,1, new Rook(PieceColor.white())));
        board.addTile(new Tile(1,7, new Rook(PieceColor.white())));
        assertTrue(board.isEndGame());
    }

    public void testSelectPlayer() throws Exception{
        Tile tile = new Tile(0,0, new King(PieceColor.white()));
        board.addTile(tile);
        assertTrue(tile.hasPiece());

        assertEquals(PlayerAction.SELECT,board.getPlayerAction());
        if ((tile.x%2==0 && tile.y%2==0) || (tile.x%2==1 && tile.y%2==1))
            assertEquals(Constants.Colors.darkColor,tile.getBackground());
        else
            assertEquals(Constants.Colors.lightColor,tile.getBackground());

        board.selectPlayer(tile);
        assertEquals(PlayerAction.MOVE,board.getPlayerAction());
        if ((tile.x%2==0 && tile.y%2==0) || (tile.x%2==1 && tile.y%2==1))
            assertEquals(Constants.Colors.highlightColor2,tile.getBackground());
        else
            assertEquals(Constants.Colors.highlightColor1,tile.getBackground());
    }

    public void testMovePlayer() throws Exception{
        Tile tile1 = new Tile(0,0, new King(PieceColor.white()));
        Tile tile2 = new Tile(0,1);
        board.addTile(new Tile(7,7, new King(PieceColor.black())));
        board.addTile(tile1);
        board.addTile(tile2);
        assertTrue(tile1.hasPiece());
        assertFalse(tile2.hasPiece());
        assertEquals(PlayerAction.SELECT,board.getPlayerAction());
        board.movePlayer(tile1,tile2);
        assertEquals(PlayerAction.SELECT,board.getPlayerAction());
        if ((tile1.x%2==0 && tile1.y%2==0) || (tile1.x%2==1 && tile1.y%2==1))
            assertEquals(Constants.Colors.darkColor,tile1.getBackground());
        else
            assertEquals(Constants.Colors.lightColor,tile1.getBackground());
    }

    public void testMouseClicked() throws Exception {
    }

    public void testMousePressed() throws Exception {
    }

    public void testMouseReleased() throws Exception {
    }

    public void testMouseEntered() throws Exception {
    }

    public void testMouseExited() throws Exception {
    }

    public void testGetFrame() throws Exception {
        assertNotNull(board.getFrame());
    }

}