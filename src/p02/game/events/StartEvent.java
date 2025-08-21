package p02.game.events;

import java.util.EventObject;

public class StartEvent extends EventObject {
    public StartEvent(Object source) {
        super(source);
    }
}