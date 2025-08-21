package p02.game.events;

import java.util.EventObject;

public class TickEvent extends EventObject {
    public TickEvent(Object source) {
        super(source);
    }
}