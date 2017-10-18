package pieces;

import chessboard.Board;
import chessboard.Tile;
import utils.PieceColor;
import utils.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Optimus extends Piece {

    public Optimus(final PieceColor color){
        super(color);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.OPTIMUS;
    }

    @Override
    protected String getPieceID() {
        return "o";
    }

    @Override
    public List<Tile> generateLegalMoves(Board board) {
        assert super.tile.getTilePiece() != null;
        List<Tile> legalMoves = new ArrayList<>();

        int[][] moves = {
                {2, -2},
                {-2, 2},
                {-2, -2},
                {2, 2},
        };

        for (int[] offset:moves){
            if (isLegalMove(super.tile.x+offset[0],super.tile.y+offset[1],board)){
                legalMoves.add(board.getTile(super.tile.x+offset[0],super.tile.y+offset[1])); }
        }

        return legalMoves;
    }
}
