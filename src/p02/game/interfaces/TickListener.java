package p02.game.interfaces;

import p02.game.events.TickEvent;

public interface TickListener {
    void onTick(TickEvent event);
}