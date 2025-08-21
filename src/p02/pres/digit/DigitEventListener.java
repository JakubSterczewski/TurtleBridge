package p02.pres.digit;

import p02.game.events.EndEvent;
import p02.pres.digit.Events.PlusOneEvent;
import p02.pres.digit.Events.StartEvent;

public interface DigitEventListener {
    void increment(PlusOneEvent event);
    void onStart(StartEvent event);
    void onEnd(EndEvent event);
}