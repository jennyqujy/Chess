package pieces;

import chessboard.Board;
import chessboard.Tile;
import utils.Constants;
import utils.PieceColor;
import utils.PieceType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece {

    public Queen(final PieceColor color){
        super(color);
    }

    @Override
    public PieceType getPieceType(){
        return PieceType.QUEEN;
    }

    @Override
    public String getPieceID() {
        return "q";
    }

    @Override
    public List<Tile> generateLegalMoves(Board board) {
        assert super.tile.getTilePiece() != null;
        ArrayList<Tile> legalMoves = new ArrayList<>();
        legalMoves.addAll(getLegalMoves(Constants.ChessDirection.UP, board));
        legalMoves.addAll(getLegalMoves(Constants.ChessDirection.DOWN, board));
        legalMoves.addAll(getLegalMoves(Constants.ChessDirection.RIGHT, board));
        legalMoves.addAll(getLegalMoves(Constants.ChessDirection.LEFT, board));
        legalMoves.addAll(getLegalMoves(Constants.ChessDirection.UPRIGHT ,board));
        legalMoves.addAll(getLegalMoves(Constants.ChessDirection.DOWNLEFT, board));
        legalMoves.addAll(getLegalMoves(Constants.ChessDirection.UPLEFT, board));
        legalMoves.addAll(getLegalMoves(Constants.ChessDirection.DOWNRIGHT ,board));
        return legalMoves;
    }
}
