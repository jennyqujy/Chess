package pieces;

import chessboard.Board;
import chessboard.Tile;
import utils.PieceColor;
import utils.PieceType;
import java.util.*;

public class King extends Piece {

    public King(final PieceColor color){
        super(color);
    }

    @Override
    public PieceType getPieceType(){
        return PieceType.KING;
    }

    @Override
    public List<Tile> generateLegalMoves(Board board) {
        List<Tile> legalMoves = new ArrayList<>();
        int[][] moves = {
                {0, -1},
                {0, 1},
                {1, -1},
                {1, 0},
                {1, 1},
                {-1, -1},
                {-1, 0},
                {-1, 1},
        };

        for (int[] offset:moves){
            if (isLegalMove(super.tile.x+offset[0],super.tile.y+offset[1],board)){
                legalMoves.add(board.getTile(super.tile.x+offset[0],super.tile.y+offset[1])); }
        }
        return legalMoves;
    }

    /**
     *
     * @param board
     * @param check
     * @param teammate
     * @param enemy
     * @return Determining whether a king is in checkmate situation:
     * 1. check piece is not king piece
     * 2. check all tile t of p.legalMove
     * 3. simulate the move from p to t
     * 4. check all enemies.legal moves.
     * 5. initiate enemy size.
     * 6. if there the enemy piece cannot checkKing, enemy size - -
     * 7. stop simulation
     * 8. check if enemy size < 1 then it is impossible to start a defense move. We are In Check Mate!
     */
    public boolean isInCheckMate(Board board, Piece check, List<Piece> teammate, List<Piece> enemy){
        assert board != null;
        assert check != null;

        if (!check.isKing()) {
            for (Piece teamPiece : teammate) {
                if (simulateDefense(board, enemy, teamPiece)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean simulateDefense(Board board, List<Piece> enemy, Piece teamPiece) {
        for (Tile t : teamPiece.generateLegalMoves(board)) {
            teamPiece.simulateMove(t);
            int counter = enemy.size();
            for (Piece enemyPiece : enemy) {
                if (!enemyPiece.generateLegalMoves(board).contains(this.tile))
                    counter--;
            }
            teamPiece.endSimulation();
            if (counter <= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param board
     * @param teammates
     * @param enemies
     * @return Whether a king is in stale mate situation:
     * 1. No enemy is checking the king
     * 2. no teammate other than king has legal moves.
     * 3. the king has no where to escape
     *
     * check all piece p pf teammates.
     * generate all legal moves of p.
     * if p.legalmoves>0 return false;
     * iterate all enemy piece, if can check team king, return false.
     * simulate king's next moves, if will not be checked return false.
     *
     */
    public boolean isInStaleMate(Board board, List<Piece> teammates, List<Piece> enemies){

        for (Piece teammate:teammates){
            if (!teammate.isKing() && teammate.generateLegalMoves(board).size()>0) return false;
        }

        for (Piece enemy:enemies){
            if (enemy.isCheckKing(board)) return false;
        }

        List<Tile> kingMoves = this.generateLegalMoves(board);
        int counter = kingMoves.size();

        for (Tile t:kingMoves) {
            this.simulateMove(t);
            for (Piece enemy : enemies) {
                if (enemy.generateLegalMoves(board).contains(this.tile))
                    counter--;
            }
            this.endSimulation();
        }

        if (counter > 0)
            return false;
        return true;
    }

    @Override
    public String getPieceID() {
        return "k";
    }
}
