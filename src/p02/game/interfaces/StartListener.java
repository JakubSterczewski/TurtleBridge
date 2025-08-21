package p02.game.interfaces;

import p02.game.events.StartEvent;

public interface StartListener {
    void onStart(StartEvent event);
}