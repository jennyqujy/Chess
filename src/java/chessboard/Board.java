package chessboard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import pieces.*;
import utils.*;
import utils.Player.*;

public class Board implements MouseListener {

    private JFrame frame;
    private JPanel chessBoard;
    private JMenuBar menu;
    private Tile[][] boardconfig;
    private PlayerAction action;
    private PlayerName player;
    private Tile selectedTile = null;
    private List<Piece> whites;
    private List<Piece> blacks;
    private King whiteKing;
    private King blackKing;

    private JLabel scoreLabel;
    private int scoreWhite;
    private int scoreBlack;

    private Tile lastTile;
    private Piece lastPiece;
    private Tile curTile;

    public Board(BoardBuilder boardBuilder){
        this.frame = new JFrame("Chess");
        this.boardconfig = createBoard(boardBuilder);
        this.action = PlayerAction.SELECT;
        this.player = PlayerName.PLAYER_WHITE;
        this.frame.add(this.getChessBoard());
        this.menu = new JMenuBar();
        this.scoreLabel = new JLabel();
    }

    public JPanel getChessBoard() {
        return this.chessBoard;
    }

    /**
     * @param x
     * @param y
     * @return get board tile of coordinate x, y
     */
    public Tile getTile(int x,int y) {
        if (isLegalCoordinate(x,y))
            return this.boardconfig[x][y];
        else
            return null;
    }

    /**
     * getter's method for board components.
     */
    public Tile[][] getTiles(){
        return this.boardconfig;
    }

    public King getBlackKing() {
        return this.blackKing;
    }

    public King getWhiteKing() {
        return this.whiteKing;
    }

    public List<Piece> getBlacks() {
        return this.blacks;
    }

    public List<Piece> getWhites() {
        return this.whites;
    }

    public JFrame getFrame() { return this.frame; }

    public PlayerName getPlayerName() {
        return this.player;
    }

    public PlayerAction getPlayerAction(){
        return this.action;
    }

    /**
     * @param x
     * @param y
     * @return whether x y coordinate of tile is valid.
     */
    public boolean isLegalCoordinate(int x, int y){
            return (!(x < 0 || x > Constants.boardSize - 1 || y < 0 || y > Constants.boardSize - 1));
    }

    /**
     * Show Board frame and menu.
     */
    public void show() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setMinimumSize(frame.getSize());
        frame.setVisible(true);

        menu.setOpaque(true);
        menu.setBackground(Constants.Colors.darkColor);
        setUpMenuButton("Restart");
        setUpMenuButton("Undo");
        setUpMenuButton("Forfeit");
        setScoreLabel();
        frame.add(menu,BorderLayout.PAGE_START);
    }

    private void setScoreLabel(){
        JLabel player1 = new JLabel();
        JLabel player2 = new JLabel();
        player1.setText("              Player_White ");
        setScore(scoreWhite,scoreBlack);
        player2.setText(" Player_Black");

        scoreLabel.setForeground(Color.DARK_GRAY);
        setPlayerLabel(player1);
        menu.add(scoreLabel);
        setPlayerLabel(player2);
    }

    private void setPlayerLabel(JLabel playerX) {
        menu.add(playerX);
        playerX.setForeground(Color.DARK_GRAY);
    }

    private void setUpMenuButton(final String action) {
        final JButton button = new JButton(action);
        final ImageIcon icon = (this.getPlayerName()==PlayerName.PLAYER_BLACK)? whiteKing.getIcon(): blackKing.getIcon();
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                if (action.equals("Restart")) {
                    int dialogResult = JOptionPane.showConfirmDialog(null, " Would you like to restart this game?","Restart Game", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
                    if (dialogResult==0) {
                        restart();
                    }
                }
                else if (action.equals("Undo")) {
                    undo();
                }
                else if (action.equals("Forfeit")) {
                    forfeit();
                }
            }
        });

        menu.add(button);
        button.setForeground(Color.DARK_GRAY);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void setScore(int l, int r) {
        scoreLabel.setText(" " + Integer.toString(l) + " : " + Integer.toString(r) + " ");
    }

    /**
     * restarting game.
     */
    private void restart() {
        Board newBoard = new BoardBuilder()
                .setBoardconfig(new Tile[Constants.boardSize][Constants.boardSize])
                .build()
                .setStandardPiecePlacement();
        newBoard.show();
        newBoard.setScore(scoreWhite, scoreBlack);
        this.frame.dispose();
    }

    private boolean lastPieceFirstMove = false;
    /**
     * Undo the current move to the last level
     */
    private void undo() {
        assert curTile != null;
        assert lastTile != null;

        if (curTile.hasPiece()) {
            Piece piece = curTile.getTilePiece();

            List<Tile> tiles = piece.generateLegalMoves(this);
            for (Tile t : tiles) {
                t.resetBackground();
            }

            if (selectedTile.hasPiece()) {
                List<Tile> selected_tiles = selectedTile.getTilePiece().generateLegalMoves(this);
                for (Tile t:selected_tiles) {
                    t.resetBackground();
                }
            }

            curTile.setIcon(null);
            curTile.setTilePiece(null);
            lastTile.setPieceOnTile(piece);
            piece.setFirstMove(this.lastPieceFirstMove);

            if (lastPiece!=null) {
                this.getTile(lastPiece.getTile().x, lastPiece.getTile().y).setPieceOnTile(lastPiece);
                if (lastPiece.isBlack()) {
                    blacks.add(lastPiece);
                } else {
                    whites.add(lastPiece);
                }
            }

            switchPlayerTurn();
            lastTile.resetBackground();
            curTile.resetBackground();

            isEndGame();
            action = PlayerAction.SELECT;
            this.lastTile = curTile;
        }
    }

    /**
     * Surrender for the current player
     */
    private void forfeit() {
        ImageIcon icon = (this.getPlayerName()==PlayerName.PLAYER_BLACK)? whiteKing.getIcon(): blackKing.getIcon();
        int dialogResult = JOptionPane.showConfirmDialog(null, " Would you like to forfeit this game?","Forfeit Game", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
        if (dialogResult==0){
            scoreWhite += 1;
            scoreBlack += 1;
        }
        restart();
        setScore(scoreWhite,scoreBlack);
    }

    /**
     * @param boardBuilder
     * @return 2D array of tile coordinates/ids.
     * Set up board config the and add each tile to board
     */
    private Tile[][] createBoard(BoardBuilder boardBuilder){

        //Set up chess board colors and size
        chessBoard = new JPanel(new GridLayout(0, Constants.boardSize));
        chessBoard.setPreferredSize(new Dimension(800,800));
        chessBoard.setBorder(new CompoundBorder(
                new EmptyBorder(24,24,24,24),
                new LineBorder(Color.DARK_GRAY)
        ));
        chessBoard.setBackground(Color.DARK_GRAY);

        final Tile[][] tiles = new Tile[Constants.boardSize][Constants.boardSize];

        for (int i=0;i<Constants.boardSize;i++){
            for(int j=0;j<Constants.boardSize;j++){
                tiles[j][i] = new Tile(j,i);
            }
        }

        // ------------------ 09/17 17:10
        whites = new ArrayList<>();
        blacks = new ArrayList<>();

        return tiles;
    }

    public Board setStandardPiecePlacement() {
        this.chessBoard.removeAll();
        for (int i=0;i<Constants.boardSize;i++){
            for(int j=0;j<Constants.boardSize;j++){
                Tile tile = createStandardTile(j,i);
                this.addTile(tile);
                this.chessBoard.add(tile);
            }
        }
        return this;
    }

    public void addTile(Tile tile) {
        this.boardconfig[tile.x][tile.y] = tile;
        setBlackAndWhite(tile);
        tile.addMouseListener(this);
        tile.resetBackground();
    }

    /**
     * @param tile
     * @Setting setting up white/black pieces and kings.
     */
    private void setBlackAndWhite(Tile tile) {
        if (tile.hasPiece()) {
            Piece piece = tile.getTilePiece();
            if (piece.isBlack()) {
                blacks.add(piece);
                if (piece.isKing())
                    blackKing = (King) piece;
            } else {
                whites.add(piece);
                if (piece.isKing())
                    whiteKing = (King) piece;
            }
        }
    }

    /**
     * @param j
     * @param i
     * @return create an occupied tile with pieces, otherwise create an empty tile.
     */
    private Tile createStandardTile(int j, int i){
        PieceColor color = PieceColor.black();

        switch(i){
            case 7:
                color = PieceColor.white();
            case 0:
                switch (j){
                    case 0: case 7:
                        return new Tile(j, i, new Rook(color));
                    case 1: case 6:
                        return new Tile(j, i, new Knight(color));
                    case 2: case 5:
                        return new Tile(j, i, new Bishop(color));
                    case 3:
                        return new Tile(j, i,new Queen(color));
                    case 4:
                        return new Tile(j, i, new King(color));
                }
            case 1:
                return new Tile(j, i, new Pawn(PieceColor.black()));
            case 6:
                return new Tile(j, i, new Pawn(PieceColor.white()));
//            case 5:
//                switch (j){
//                    case 2:
//                        return new Tile(j, i,new Prime(PieceColor.white()));
//                    case 5:
//                        return new Tile(j, i,new Optimus(PieceColor.white()));
//                }
//            case 2:
//                switch (j){
//                    case 2:
//                        return new Tile(j, i,new Prime(PieceColor.black()));
//                    case 5:
//                        return new Tile(j, i,new Optimus(PieceColor.black()));
//                }
            default:
                return new Tile(j,i);
        }
    }

    /**
     * Builder pattern for the Board Constructor.
     */
    public static class BoardBuilder {
        private Tile[][] boardconfig = new Tile[Constants.boardSize][Constants.boardSize];
        public BoardBuilder setBoardconfig(Tile[][] boardconfig){
            this.boardconfig = boardconfig;
            return this;
        }

        public Board build(){
            return new Board(this);
        }
    }

    /**
     * Checking end game coditions: either is CheckMate or StaleMate.
     */
    public boolean isEndGame() {
        return (isCheckMate() || isStaleMate());
    }

    /**
     * @return check if enemy piece can check an opponent King, either check king or check mate.
     */
    public boolean isCheckMate() {
        for (Piece p: blacks) {
            if (p.isCheckKing(this)) {
                if (whiteKing.isInCheckMate(this, p, whites, blacks)) {
                    scoreBlack += 1;
                    setScore(scoreWhite,scoreBlack);
                    JOptionPane.showMessageDialog(null, "Check Mate, Black Win!", "About", JOptionPane.INFORMATION_MESSAGE, blackKing.getIcon());
                    restart();
                    return true;
                }
                JOptionPane.showMessageDialog(null,
                        "Check Message: " + "Your White King is in Check!", "Warning", JOptionPane.INFORMATION_MESSAGE, whiteKing.getIcon());
                ;
            }
        }

        for (Piece p: whites) {
            if (p.isCheckKing(this)) {
                if (blackKing.isInCheckMate(this, p, blacks, whites)) {
                    scoreWhite += 1;
                    setScore(scoreWhite,scoreBlack);
                    JOptionPane.showMessageDialog(null, "Check Mate, White Win!","About", JOptionPane.INFORMATION_MESSAGE, whiteKing.getIcon());
                    restart();
                    return true;
                }
                JOptionPane.showMessageDialog(null,
                        "Check Message: " + "Your Black King is in Check!", "Warning", JOptionPane.INFORMATION_MESSAGE, blackKing.getIcon());
            }
        }

        return false;
    }

    /**
     * @return check if the black or white is in stale mate.
     */
    public boolean isStaleMate(){
        assert blackKing != null;
        assert whiteKing != null;
        if (blackKing.isInStaleMate(this,blacks,whites) || whiteKing.isInStaleMate(this,whites,blacks)) {
            JOptionPane.showMessageDialog(null,
                    "Stale Message: " + "Stale Mate!", "About", JOptionPane.INFORMATION_MESSAGE, whiteKing.getIcon());
            setScore(scoreWhite,scoreBlack);
            restart();
            return true;
        }

        return false;
    }

    //press and release
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof Tile) {
            Tile targetTile = (Tile) e.getSource();
            assert targetTile != null;

            switch (action) {
                case SELECT:
                    selectPlayer(targetTile);
                    break;
                case MOVE:
                    assert selectedTile != null;
                    assert !selectedTile.equals(targetTile);
                    this.lastTile = selectedTile;
                    this.curTile = targetTile;
                    movePlayer(selectedTile, targetTile);
                    break;
            }
        }
    }

    /**
     * When player action is set to move.
     */
    void movePlayer(Tile selectedTile, Tile targetTile) {
        Piece piece = selectedTile.getTilePiece();
        List<Tile> tiles = piece.generateLegalMoves(this);
        //update whites and blacks and move piece
        if (tiles.contains(targetTile)) {
            removeColorPieces(targetTile);
            if (!piece.getInSimulation()) {
                if (targetTile.hasPiece()){
                    this.lastPiece = targetTile.getTilePiece();
                }else{
                    this.lastPiece = null;
                }
                this.lastPieceFirstMove = piece.getFirstMove();
                piece.moveTo(targetTile);
                switchPlayerTurn();
            }
        }
        selectedTile.resetBackground();
        for (Tile t: tiles) {
            t.resetBackground();
        }
        isEndGame();
        action = PlayerAction.SELECT;
    }

    /**
     * When player action is set to select.
     */
    void selectPlayer(Tile targetTile) {
        if (targetTile.hasPiece()) {
            if ( (player.equals(PlayerName.PLAYER_BLACK) && targetTile.getTilePiece().isBlack()) ||
            (player.equals(PlayerName.PLAYER_WHITE) && !targetTile.getTilePiece().isBlack())) {
               selectPiece(targetTile);
            }
        }
    }

    /**
     * @param targetTile
     * White/Black player Selecting a target tile with white/black piece
     */
    void selectPiece(Tile targetTile) {
        this.selectedTile = targetTile;
        targetTile.enableHighlight();
        List<Tile> tiles = targetTile.getTilePiece().generateLegalMoves(this);

        //for debugging----------------
        for (Tile t : tiles) {
            if ((t.x%2==0 && t.y%2==0) || (t.x%2==1 && t.y%2==1))
                t.setBackground(Constants.Colors.highlightColor2);
            else
                t.setBackground(Constants.Colors.highlightColor1);
        }
        //-----------------------------

        action = PlayerAction.MOVE;
    }

    /**
     * switching player's turn
     */
    public void switchPlayerTurn() {
        switch (player){
            case PLAYER_WHITE:
                player = PlayerName.PLAYER_BLACK;
                break;
            case PLAYER_BLACK:
                player = PlayerName.PLAYER_WHITE;
                break;
        }
    }

    /**
     * @param targetTile
     * remove white/black piece after piece on the target tile is killed
     */
    void removeColorPieces(Tile targetTile) {
        if (targetTile.hasPiece()) {
            if (targetTile.getTilePiece().isBlack()) {
                blacks.remove(targetTile.getTilePiece());
            }else {
                whites.remove(targetTile.getTilePiece());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
