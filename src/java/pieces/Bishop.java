package pieces;

import chessboard.Board;
import chessboard.Tile;
import utils.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bishop extends Piece{

    public Bishop(final PieceColor color){
        super(color);
    }

    @Override
    public PieceType getPieceType(){
        return PieceType.BISHOP;
    }

    @Override
    public List<Tile> generateLegalMoves(Board board) {
        assert super.tile.getTilePiece() != null;
        List<Tile> legalMoves = new ArrayList<>();
        legalMoves.addAll(getLegalMoves(Constants.ChessDirection.DOWNLEFT,board));
        legalMoves.addAll(getLegalMoves(Constants.ChessDirection.DOWNRIGHT,board));
        legalMoves.addAll(getLegalMoves(Constants.ChessDirection.UPLEFT,board));
        legalMoves.addAll(getLegalMoves(Constants.ChessDirection.UPRIGHT,board));
        return legalMoves;
    }

    @Override
    public String getPieceID() {
        return "b";
    }

}
