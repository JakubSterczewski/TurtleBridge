package p02.game.interfaces;

import p02.game.events.EndEvent;

public interface EndListener {
    void onEnd(EndEvent event);
}