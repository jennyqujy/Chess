package pieces;

import chessboard.Board;
import chessboard.Tile;
import utils.PieceColor;
import utils.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{
    public Pawn(final PieceColor color){
        super(color);
    }

    @Override
    public PieceType getPieceType(){
        return PieceType.PAWN;
    }

    /**
     * @param board
     * @return All the Valid moves for pawn piece:
     * 1. Always case: if it is black can move 1 down; if it is white, can move 1 up
     * 2. if is first move: if it is black can move 2 down; if it is white, can move 2 up.
     * 3. Can only kill pieces diagnolly;
     * 4. UNIMPLEMENTED YET!!! Transform to ANY piece after row<=1 for white and row >= 6 for black.
     */
    @Override
    public List<Tile> generateLegalMoves(Board board)  {
        List<Tile> legalMoves = new ArrayList<>();

        int newY = (this.color.getColorType() == PieceColor.ColorType.BLACK)? this.tile.y+1 : this.tile.y-1;
        addMajorMove(board, legalMoves,this.tile.x, newY);
        addKillMove(board, legalMoves, this.tile.x+1, newY);
        addKillMove(board, legalMoves, this.tile.x-1, newY);

        if (this.getFirstMove() && !board.getTile(this.tile.x,newY).hasPiece()) {
                newY = (this.color.getColorType() == PieceColor.ColorType.BLACK) ? this.tile.y + 2 : this.tile.y - 2;
                addMajorMove(board, legalMoves, this.tile.x, newY);
        }

        return legalMoves;
    }

    private void addKillMove(Board board, List<Tile> legalMoves, int x, int y) {
        if (isLegalMove(x,y,board) && board.getTile(x, y).hasPiece()){
            Piece piece = board.getTile(x,y).getTilePiece();
            if (isOpponent(piece)) legalMoves.add(board.getTile(x,y));
        }
    }

    private void addMajorMove(Board board, List<Tile> legalMoves, int x, int y) {
        if (isLegalMove(x,y,board) && (!board.getTile(x,y).hasPiece())){
           legalMoves.add(board.getTile(x,y));
        }
    }

    @Override
    public String getPieceID() { return "p"; }
}
