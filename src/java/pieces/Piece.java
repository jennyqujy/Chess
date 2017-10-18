package pieces;

import chessboard.*;
import utils.*;

import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.util.List;

public abstract class Piece {

    protected Tile tile;
    protected final PieceColor color;
    private boolean isFirstMove;
    private boolean isInSimulation;

    public Piece(final PieceColor color)
    {
        this.color = color;
        this.isFirstMove= true;
        this.isInSimulation = false;
    }

    public abstract PieceType getPieceType();

    protected abstract String getPieceID();

    public abstract List<Tile> generateLegalMoves(Board board);

    public boolean getFirstMove(){ return this.isFirstMove; }

    public void setFirstMove(boolean firstMove){ this.isFirstMove = firstMove; }

    public PieceColor getColor() { return this.color; }

    public boolean isBlack(){
        return (this.color.getColorType() == PieceColor.ColorType.BLACK);
    }

    public Tile getTile() { return this.tile; }

    public boolean getInSimulation() { return this.isInSimulation; }

    public boolean isKing(){
        return (this.getPieceType() == PieceType.KING);
    }

    /**
     * @param piece
     * @return true if the piece is our opponent, false otherwise.
     */
    public boolean isOpponent(Piece piece){
        assert piece != null;
        return (piece.color.getColorType() != this.color.getColorType());
    }

    /**
     * @param
     * @param
     * @return whether moving to the destination tile is a good idea
     */

    public boolean isLegalMove(int x, int y, Board board) {
        if (board.isLegalCoordinate(x,y)) {
            if (board.getTile(x,y).hasPiece()) {
                if (isOpponent(board.getTile(x,y).getTilePiece())){
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * 09/21
     * Check whether is of check king but not check mate.
     */
    public boolean isCheckKing(Board board){
        List<Tile> tiles = this.generateLegalMoves(board);
        for (Tile t:tiles) {
            assert t != null;
            if (t.hasPiece() && t.getTilePiece().isKing()){
                return true;
            }
        }
        return false;
    }

    /**
     * @param destTile
     * moving our piece to the destination tile.
     */
    public void moveTo(Tile destTile) {
        assert destTile != null;
        assert this.tile != null;
        this.tile.setIcon(null);
        Piece piece = this.tile.getTilePiece();
        this.tile.setTilePiece(null);
        this.setFirstMove(false);
        destTile.setPieceOnTile(piece);
    }

    private Tile origTile = null;
    private Tile destTile = null;
    private Piece destPiece = null;

    protected void simulateMove(Tile destTile){
        assert destTile != null;
        assert this.tile != null;

        this.origTile = this.tile;
        this.destTile = destTile;
        this.destPiece = destTile.getTilePiece();

        this.isInSimulation = true;
        this.tile.setTilePiece(null);
        destTile.setTilePiece(this);
        this.setTile(destTile);
    }

    protected void endSimulation() {

        this.isInSimulation = false;
        this.destTile.setTilePiece(this.destPiece);
        this.setTile(origTile);
        tile.setTilePiece(this);

        this.origTile = null;
        this.destTile = null;
        this.destPiece = null;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * @param dir
     * @return A collection of legal piece moves.
     * TODO: Needs inner coordinate refactoring.
     */
    public List<Tile> getLegalMoves(Constants.ChessDirection dir, Board board){
        List<Tile> legalMoves = new ArrayList<>();
        assert tile.getTilePiece() != null;
        for (int i=1;;++i) {
            int curX = tile.x;
            int curY = tile.y;
            switch (dir) {
                case UP:
                    curY -= i;
                    break;
                case DOWN:
                    curY += i;
                    break;
                case LEFT:
                    curX -= i;
                    break;
                case RIGHT:
                    curX += i;
                    break;
                case DOWNLEFT:
                    curY += i;
                    curX -= i;
                    break;
                case DOWNRIGHT:
                    curY += i;
                    curX += i;
                    break;
                case UPLEFT:
                    curY -= i;
                    curX -= i;
                    break;
                case UPRIGHT:
                    curY -= i;
                    curX += i;
                    break;
            }

            if (isLegalMove(curX,curY,board)){
                legalMoves.add(board.getTile(curX,curY));
                if (board.getTile(curX,curY).hasPiece()) break;
            }else{
                break;
            }
        }
        return legalMoves;
    }

    public String getImageID() {
        String name = "";
        if (this.color.getColorType() == PieceColor.ColorType.WHITE) {
            name = "w";
        }else
            name = "b";
        return name + getPieceID() + ".png";
    }

    public ImageIcon getIcon() {
        URL path = this.getClass().getClassLoader().getResource("resources/" + this.getImageID());
        ImageIcon icon = new ImageIcon(path);
        return icon;
    }
}
