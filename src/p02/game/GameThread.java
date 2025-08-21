package p02.game;

import p02.game.events.EndEvent;
import p02.game.events.StartEvent;
import p02.game.events.TickEvent;
import p02.game.interfaces.EndListener;
import p02.game.interfaces.StartListener;
import p02.game.interfaces.TickListener;
import java.util.ArrayList;
import java.util.List;

public class GameThread extends Thread implements EndListener, StartListener {
    private static volatile GameThread instance;
    private final Board board;
    private final List<TickListener> tickListeners = new ArrayList<>();
    private boolean isRunning;
    private boolean isSuspended;

    private GameThread(Board board) {
        this.board = board;
    }

    public static GameThread getInstance(Board board) {
        if (instance == null) {
            synchronized (GameThread.class) {
                if (instance == null) {
                    instance = new GameThread(board);
                }
            }
        }
        return instance;
    }

    public void addTickListener(TickListener listener) {
        tickListeners.add(listener);
    }

    @Override
    public void run() {
        while (isRunning) {
            TickEvent event = new TickEvent(this);
            for (TickListener listener : tickListeners) {
                listener.onTick(event);
            }

            try {
                int score = board.getScoreDisplay().getDisplayedValue();
                if (score < 100) {
                    Thread.sleep(1500);
                } else if (score < 500) {
                    Thread.sleep(1000);
                } else {
                    Thread.sleep(500);
                }

                synchronized (this){
                    while(isSuspended){
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEnd(EndEvent event) {
        synchronized (this){
            isSuspended = true;
        }
    }

    @Override
    public void onStart(StartEvent event) {
        if (!isRunning) {
            isRunning = true;
            start();
        } else if (instance != null) {
            synchronized (this){
                isSuspended = false;
                notify();
            }
        }
    }
}