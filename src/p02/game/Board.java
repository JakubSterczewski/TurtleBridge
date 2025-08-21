package p02.game;

import p02.game.events.EndEvent;
import p02.game.events.StartEvent;
import p02.game.events.TickEvent;
import p02.game.interfaces.EndListener;
import p02.game.interfaces.StartListener;
import p02.game.interfaces.TickListener;
import p02.pres.table.GameTableModel;
import p02.pres.digit.ScoreDisplay;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import static p02.game.GameElement.*;

public class Board extends JPanel implements KeyListener, TickListener, EndListener {
    private boolean isThreadRunning;
    private boolean isThreadSuspended = true;
    private final List<EndListener> endListeners = new ArrayList<>();
    private final List<StartListener> startListeners = new ArrayList<>();
    private int tickCounter;
    private GameTableModel gameTableModel;
    private ScoreDisplay scoreDisplay;
    private BoardTable boardTable;

    public Board() {
        addKeyListener(this);
        addEndListener(this);
        initializeGame();
    }
    
    private void initializeGame() {
        tickCounter = 0;
        boardTable = new BoardTable();
    }

    public void setScoreDisplay(ScoreDisplay scoreDisplay) {
        this.scoreDisplay = scoreDisplay;
    }

    public ScoreDisplay getScoreDisplay() {
        return scoreDisplay;
    }

    public void setGameTableModel(GameTableModel gameTableModel) {
        this.gameTableModel = gameTableModel;
    }

    public BoardTable getBoardTable() {
        return boardTable;
    }

    public List<EndListener> getEndListeners(){
        return endListeners;
    }

    public void addEndListener(EndListener listener) {
        endListeners.add(listener);
    }

    public void addStartListeners(StartListener listener) {
        startListeners.add(listener);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isThreadSuspended) {
            boolean removePackage = false;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> movePlayerOnPressed(false);
                case KeyEvent.VK_D -> {
                    movePlayerOnPressed(true);
                    removePackage = checkRemovePackage();
                }
            }
            gameTableModel.checkFire();

            if (removePackage){
                boardTable.setBoardElement(boardTable.getPlayer().getY(),boardTable.getPlayer().getX(),PLAYER_LEFT);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S && isThreadSuspended) {
            isThreadSuspended = false;
            if (!isThreadRunning){
                isThreadRunning = true;

                GameThread gameThread = GameThread.getInstance(this);
                gameThread.addTickListener(this);
                gameThread.addTickListener(gameTableModel);
                addEndListener(gameThread);
                addStartListeners(gameThread);
            }

            StartEvent event = new StartEvent(this);
            for (StartListener listener : startListeners) {
                listener.onStart(event);
            }

            initializeGame();
        }
    }

    @Override
    public void onTick(TickEvent event) {
        movePlayerFromWater();
        updateTurtleRow();
        updateFish();
        updateTurtles();
        spawnFish();
        spawnReceiver();
        boardTable.printBoard();
        checkGameOver();
    }

    @Override
    public void onEnd(EndEvent event) {
        isThreadSuspended = true;
        if (!event.hasWon()){
            boardTable.setBoardElement(4, boardTable.getPlayer().getX(), PLAYER_RIGHT);
            boardTable.setBoardElement(0, boardTable.getPlayer().getX(), EMPTY);
            gameTableModel.checkFire();
        }
    }

    private void movePlayerOnPressed(boolean isGoingRight) {
        int playerX = boardTable.getPlayer().getX();
        if (playerX != 2 && playerX != 4 && playerX != 6 && playerX != 8 && playerX != 10) {
            if (isGoingRight) {
                boardTable.movePlayerRight();
            } else {
                if (playerX != 0) {
                    boardTable.movePlayerLeft();
                }
            }
        }

        playerX = boardTable.getPlayer().getX();
        int playerY = boardTable.getPlayer().getY();
        GameElement gameElement = getEnum(boardTable.getBoardValue(playerY, playerX));
        boolean hasPackage = gameElement == PLAYER_RIGHT_WITH_PACKAGE || gameElement == PLAYER_LEFT_WITH_PACKAGE;

        if (playerX == 0 && !hasPackage) {
            if (gameElement == PLAYER_LEFT){
                boardTable.setBoardElement(playerY, playerX, PLAYER_LEFT_WITH_PACKAGE);
            } else {
                boardTable.setBoardElement(playerY, playerX, PLAYER_RIGHT_WITH_PACKAGE);
            }
            scoreDisplay.setScore(5);
        }
    }

    private boolean checkRemovePackage(){
        int playerX = boardTable.getPlayer().getX();
        int playerY = boardTable.getPlayer().getY();
        GameElement gameElement = getEnum(boardTable.getBoardValue(playerY, playerX));
        boolean hasPackage = gameElement == PLAYER_RIGHT_WITH_PACKAGE || gameElement == PLAYER_LEFT_WITH_PACKAGE;

        if (boardTable.getPlayer().getX() == 10 && hasPackage && boardTable.getBoardValue(0, boardTable.getCols() - 1) == RECEIVER.getValue()) {
            scoreDisplay.setScore(3);
            return true;
        }
        return false;
    }

    private void movePlayerFromWater() {
        int playerX = boardTable.getPlayer().getX();
        int playerY = boardTable.getPlayer().getY();

        if (playerY == 0 && (playerX == 2 || playerX == 4 || playerX == 6 || playerX == 8)) {
            GameElement gameElement = getEnum(boardTable.getBoardValue(playerY, playerX));
            if (gameElement == PLAYER_LEFT || gameElement == PLAYER_LEFT_WITH_PACKAGE) {
                boardTable.movePlayerLeft();
            } else {
                boardTable.movePlayerRight();
            }
        } else if (playerX == 10) {
            boardTable.movePlayerLeft();
        }
    }

    private void checkGameOver() {
        if (boardTable.getBoardValue(2, boardTable.getPlayer().getX()) == TURTLE_SUBMERGED.getValue() ||
                   boardTable.getBoardValue(2, boardTable.getPlayer().getX()) == TURTLE_EMERGED.getValue()) {
            boardTable.getPlayer().setY(boardTable.getRows() - 1);
            EndEvent event = new EndEvent(this, false);
            for (EndListener listener : endListeners) {
                listener.onEnd(event);
            }
        }
    }

    private void updateTurtles() {
        //Setting random state for turtles
        for (int i = 0; i < boardTable.getRows(); i++) {
            for (int j = 1; j < boardTable.getCols() - 2; j++) {
                if (boardTable.getBoardValue(i,j) == TURTLE_EMERGED.getValue() || 
                    boardTable.getBoardValue(i,j) == TURTLE_SUBMERGED.getValue()) {
                    if (Math.random() < 0.3) {
                        boardTable.setBoardElement(i, j, 
                            boardTable.getBoardValue(i,j) == TURTLE_EMERGED.getValue() ? 
                            TURTLE_SUBMERGED : TURTLE_EMERGED);
                    }
                }
            }
        }
    }

    private void updateFish() {
        for (int j = 0; j < boardTable.getCols(); j++) {
            //Turtle eating the fish
            if (boardTable.getBoardValue(2,j) == FISH.getValue() && 
                boardTable.getBoardValue(1,j) == TURTLE_SUBMERGED.getValue()) {
                boardTable.setBoardElement(2,j, TURTLE_SUBMERGED);
                boardTable.setBoardElement(1,j, EMPTY);
            }

            //Moving the fish up
            for (int i = 3; i < boardTable.getRows(); i++) {
                if (boardTable.getBoardValue(i,j) == FISH.getValue()) {
                    boardTable.setBoardElement(i-1,j, FISH);
                    boardTable.setBoardElement(i,j, EMPTY);
                }
            }
        }
    }

    private void updateTurtleRow() {
        //Moving the turtle back up
        for (int j = 0; j < boardTable.getCols(); j++) {
            if (boardTable.getBoardValue(2,j) == TURTLE_SUBMERGED.getValue() || 
                boardTable.getBoardValue(2,j) == TURTLE_EMERGED.getValue()) {
                boardTable.setBoardElement(1,j, TURTLE_EMERGED);
                boardTable.setBoardElement(2,j, EMPTY);
            }
        }
    }

    private void spawnFish() {
        int maxFish = 0;

        for (int val : scoreDisplay.getValues()){
            if (val != 0){
                maxFish++;
            }
        }

        for (int i = 1; i < boardTable.getCols() - 1; i += 2) {
            if (!hasFishInColumn(i) && Math.random() < 0.1 && maxFish != 0) {
                boardTable.setBoardElement(4, i, FISH);
                maxFish--;
            }
        }
    }

    private boolean hasFishInColumn(int col) {
        for (int i = 2; i < boardTable.getRows(); i++) {
            if (boardTable.getBoardValue(i, col) == FISH.getValue()) {
                return true;
            }
        }
        return false;
    }

    private void spawnReceiver() {
        tickCounter++;
        if (tickCounter % 5 == 0) {
            if (boardTable.getBoardValue(0,11) == EMPTY.getValue()) {
                boardTable.setBoardElement(0, 11, RECEIVER);
            } else {
                boardTable.setBoardElement(0, 11, EMPTY);
            }
        }
    }
}