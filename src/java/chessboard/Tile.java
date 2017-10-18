package chessboard;

import pieces.*;
import utils.Constants;

import javax.swing.*;
import java.awt.*;

public class Tile extends JButton {
    public int x;
    public int y;
    private Piece tilePiece;

    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        this.tilePiece = null;
        tileSetup();
    }

    public Tile(int x, int y, Piece piece){
        this.x = x;
        this.y = y;
        tileSetup();
        this.setPieceOnTile(piece);
    }

    public void resetBackground() {
        if ( (x%2==0 && y%2==0) || (x%2==1 && y%2==1))
            this.setBackground(Constants.Colors.darkColor);
        else
            this.setBackground(Constants.Colors.lightColor);

    }

    public void enableHighlight() {
        if ((x%2==0 && y%2==0) || (x%2==1 && y%2==1))
            this.setBackground(Constants.Colors.highlightColor2);
        else
            this.setBackground(Constants.Colors.highlightColor1);
    }

    public final void tileSetup() {
        this.setMargin(new Insets(0,0,0,0));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setBorderPainted(false);
        this.setFocusPainted(true);
        this.setContentAreaFilled(true);
        this.setOpaque(true);
    }

    public void setTilePiece(Piece tilePiece) {
        this.tilePiece = tilePiece;
    }

    public void setPieceOnTile(Piece tilePiece){
        assert tilePiece != null;
        this.tilePiece = tilePiece;
        this.tilePiece.setTile(this);
        ImageIcon icon = tilePiece.getIcon();
        this.setIcon(icon);
    }

    public Piece getTilePiece() {
        return this.tilePiece;
    }

    public boolean hasPiece() {
        return this.tilePiece != null;
    }
}


