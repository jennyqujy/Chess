package chessboard;
import javax.swing.*;
import utils.*;

public class main {
    public static void main(String[] args){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Board chessBoard = new Board
                        .BoardBuilder()
                        .setBoardconfig(new Tile[Constants.boardSize][Constants.boardSize])
                        .build()
                        .setStandardPiecePlacement();
                chessBoard.show();

            }
        };

        SwingUtilities.invokeLater(r);
    }
}
