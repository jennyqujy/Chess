package pieces;

import chessboard.*;

import java.util.ArrayList;
import java.util.List;

import utils.*;

public class Knight extends Piece{

    public Knight(final PieceColor color){
        super(color);
    }

    @Override
    public PieceType getPieceType(){
        return PieceType.KNIGHT;
    }

    @Override
    public List<Tile> generateLegalMoves(Board board) {
        assert super.tile.getTilePiece() != null;
        List<Tile> legalMoves = new ArrayList<>();
        addMajorMove(board,legalMoves,super.tile.x-2, super.tile.y-1);
        addMajorMove(board,legalMoves,super.tile.x-2, super.tile.y+1);
        addMajorMove(board,legalMoves,super.tile.x+2, super.tile.y-1);
        addMajorMove(board,legalMoves,super.tile.x+2, super.tile.y+1);
        addMajorMove(board,legalMoves,super.tile.x+1, super.tile.y+2);
        addMajorMove(board,legalMoves,super.tile.x+1, super.tile.y-2);
        addMajorMove(board,legalMoves,super.tile.x-1, super.tile.y+2);
        addMajorMove(board,legalMoves,super.tile.x-1, super.tile.y-2);
        return legalMoves;
    }

    private void addMajorMove(Board board, List<Tile> legalMoves, int x, int y) {
        if (isLegalMove(x,y,board)) {
            legalMoves.add(board.getTile(x,y));
        }
    }

    @Override
    public String getPieceID() {
        return "n";
    }

}

