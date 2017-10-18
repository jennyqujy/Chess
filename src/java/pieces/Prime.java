package pieces;

import chessboard.Board;
import chessboard.Tile;
import utils.PieceColor;
import utils.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Prime extends Piece {

    public Prime(final PieceColor color){
        super(color);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PRIME;
    }

    @Override
    protected String getPieceID() {
        return "m";
    }

    @Override
    public List<Tile> generateLegalMoves(Board board) {
        assert super.tile.getTilePiece() != null;
        List<Tile> legalMoves = new ArrayList<>();
        int[][] moves = {
                {1, -1},
                {1, 1},
                {-1, -1},
                {-1, 1},
        };

        for (int[] offset:moves){
            if (isLegalMove(super.tile.x+offset[0],super.tile.y+offset[1],board)){
                legalMoves.add(board.getTile(super.tile.x+offset[0],super.tile.y+offset[1])); }
        }
        return legalMoves;
    }
}
