package p02.pres.digit;

import p02.game.Board;
import p02.game.events.EndEvent;
import p02.game.interfaces.EndListener;
import p02.game.interfaces.StartListener;
import p02.pres.digit.Events.PlusOneEvent;
import p02.pres.digit.Events.StartEvent;
import javax.swing.*;
import java.awt.*;

public class ScoreDisplay extends JPanel implements EndListener, StartListener {
    private final SevenSegmentDigit hundreds;
    private final SevenSegmentDigit tens;
    private final SevenSegmentDigit units;

    public ScoreDisplay(Board board) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        hundreds = new SevenSegmentDigit();
        tens = new SevenSegmentDigit();
        units = new SevenSegmentDigit();

        add(hundreds);
        add(tens);
        add(units);

        units.setNextListener(tens);
        tens.setNextListener(hundreds);
        hundreds.setEndListeners(board.getEndListeners());

        board.addStartListeners(this);

        setOpaque(false);
    }

    public void setScore(int n){
        for (int i = 0; i < n; i++) {
            increment();
        }
    }

    public void increment() {
        units.increment(new PlusOneEvent(this));
    }

    public int[] getValues(){
        return new int[]{units.getValue(), tens.getValue(), hundreds.getValue()};
    }

    public int getDisplayedValue() {
        return hundreds.getValue() * 100 + tens.getValue() * 10 + units.getValue();
    }

    @Override
    public void onEnd(EndEvent event) {
        units.onEnd(event);
        tens.onEnd(event);
        hundreds.onEnd(event);
    }

    @Override
    public void onStart(p02.game.events.StartEvent event) {
        units.onStart(new StartEvent(this));
    }
}