package chessboard;

import junit.framework.TestCase;
import pieces.Knight;
import utils.Constants;
import utils.PieceColor;

import java.awt.*;

public class TileTest extends TestCase {
    private Tile tile;
    private Tile tile2;

    public void setUp() throws Exception {
        super.setUp();
        tile = new Tile(0,0);
        tile2 = new Tile(2,5);
    }

    public void tearDown() throws Exception {
    }

    public void testResetBackground() throws Exception {
        tile.setBackground(Color.black);
        tile.resetBackground();
        assertEquals(Constants.Colors.darkColor,tile.getBackground());
    }

    public void testEnableHighlight() throws Exception {
        tile.enableHighlight();
        tile2.enableHighlight();
        if ((tile.x%2==0 && tile.y%2==0) || (tile.x%2==1 && tile.y%2==1))
            assertEquals(Constants.Colors.highlightColor2,tile.getBackground());
        else
            assertEquals(Constants.Colors.highlightColor1,tile.getBackground());
    }

    public void testTileSetup() throws Exception {
    }

    public void testSetPiece() throws Exception {
        tile.setTilePiece(new Knight(PieceColor.white()));
        assertNotNull(tile.getTilePiece());
    }

    public void testSetPieceOnTile() throws Exception {
        tile.setPieceOnTile(new Knight(PieceColor.white()));
        assertNotNull(tile.getTilePiece());
        assertNotNull(tile.getTilePiece().getIcon());
    }

    public void testGetTilePiece() throws Exception {
        assertNull(tile.getTilePiece());
        tile.setPieceOnTile(new Knight(PieceColor.white()));
        assertNotNull(tile.getTilePiece());
    }

    public void testHasPiece() throws Exception {
        assertEquals(false,tile.hasPiece());
        tile.setPieceOnTile(new Knight(PieceColor.white()));
        assertEquals(true,tile.hasPiece());
    }

}