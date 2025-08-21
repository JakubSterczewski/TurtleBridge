package p02.game;

import static p02.game.GameElement.*;

public class BoardTable {
    private static final int[][] boardTable = new int[5][12];
    private final Player player;

    public BoardTable(){
        System.out.println("Initializing the board");

        player = new Player();

        for (int i = 0; i < boardTable.length; i++) {
            for (int j = 0; j < boardTable[i].length; j++) {
                setBoardElement(i, j, EMPTY);
            }
        }

        for (int i = 1; i < boardTable[0].length - 1; i += 2){
            setBoardElement(1, i, Math.random() < 0.3 ? TURTLE_EMERGED : TURTLE_SUBMERGED);
        }

        setBoardElement(player.getY(), player.getX(), PLAYER_RIGHT_WITH_PACKAGE);
    }

    public void setBoardElement(int row, int col, GameElement element) {
        synchronized (boardTable){
            boardTable[row][col] = element.getValue();
        }
    }

    public int getBoardValue(int row, int col) {
        synchronized (boardTable){
            return boardTable[row][col];
        }
    }

    public void movePlayerRight() {
        int oldX = player.getX();
        int oldY = player.getY();
        player.setX(oldX + 1);
        if (hasPackage(oldY, oldX)){
            setBoardElement(oldY, oldX + 1, PLAYER_RIGHT_WITH_PACKAGE);
        } else{
            setBoardElement(oldY, oldX + 1, PLAYER_RIGHT);
        }
        setBoardElement(oldY, oldX, EMPTY);
    }

    public void movePlayerLeft() {
        int oldX = player.getX();
        int oldY = player.getY();
        player.setX(oldX - 1);
        if (hasPackage(oldY, oldX)){
            setBoardElement(oldY, oldX - 1, PLAYER_LEFT_WITH_PACKAGE);
        } else{
            setBoardElement(oldY, oldX - 1, PLAYER_LEFT);
        }
        setBoardElement(oldY, oldX, EMPTY);
    }

    private boolean hasPackage(int y, int x){
        GameElement gameElement = getEnum(getBoardValue(y, x));
        return gameElement == PLAYER_RIGHT_WITH_PACKAGE || gameElement == PLAYER_LEFT_WITH_PACKAGE;
    }

    public Player getPlayer(){
        return player;
    }

    public int getRows() {
        return boardTable.length;
    }

    public int getCols() {
        return boardTable[0].length;
    }

    public void printBoard(){
        System.out.println();
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                System.out.print(boardTable[i][j] + " ");
            }
            System.out.println();
        }
    }
}