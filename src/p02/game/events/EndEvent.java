package p02.game.events;

import java.util.EventObject;

public class EndEvent extends EventObject {
    private final boolean hasWon;

    public EndEvent(Object source, boolean hasWon) {
        super(source);
        this.hasWon = hasWon;
    }

    public boolean hasWon() {
        return hasWon;
    }
}