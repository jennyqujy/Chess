package pieces;

import chessboard.Board;
import chessboard.Tile;
import utils.Constants;
import utils.PieceColor;
import utils.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{
    public Rook(final PieceColor color){
        super(color);
    }

    @Override
    public PieceType getPieceType(){
        return PieceType.ROOK;
    }

    @Override
    public List<Tile> generateLegalMoves(Board board) {
        assert super.tile.getTilePiece() != null;
        List<Tile> legalMoves = new ArrayList<>();
        legalMoves.addAll(getLegalMoves(Constants.ChessDirection.UP,board));
        legalMoves.addAll(getLegalMoves(Constants.ChessDirection.DOWN,board));
        legalMoves.addAll(getLegalMoves(Constants.ChessDirection.LEFT,board));
        legalMoves.addAll(getLegalMoves(Constants.ChessDirection.RIGHT,board));
        return legalMoves;
    }

    @Override
    public String getPieceID() {
        return "r";
    }
}
