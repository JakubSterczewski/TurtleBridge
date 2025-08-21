package p02.pres.digit;

import p02.game.events.EndEvent;
import p02.game.interfaces.EndListener;
import p02.pres.digit.Events.PlusOneEvent;
import p02.pres.digit.Events.StartEvent;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SevenSegmentDigit extends JPanel implements DigitEventListener {
    private static final int width = 20;
    private static final int height = 5;
    private static final int panelWidth = width + 10;

    private int value;
    private final boolean[] segments;
    private List<EndListener> endListeners;
    private DigitEventListener nextListener;

    public SevenSegmentDigit() {
        value = 0;
        segments = new boolean[7];
        setPreferredSize(new Dimension(panelWidth, width * 2 + height * 3));
        setOpaque(false);
    }

    public void setEndListeners(List<EndListener> endListeners){
        this.endListeners = endListeners;
    }

    public void setNextListener(DigitEventListener nextListener){
        this.nextListener = nextListener;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void increment(PlusOneEvent event) {
        value++;
        if (value == 10){
            value=0;
            if (nextListener != null){
                nextListener.increment(event);
            } else{
                EndEvent endEvent = new EndEvent(this, true);
                for (EndListener listener : endListeners) {
                    listener.onEnd(endEvent);
                }
            }
        }
        updateSegments();
    }

    @Override
    public void onStart(StartEvent event) {
        value = 0;
        setVisible(true);
        updateSegments();

        if (nextListener != null){
            StartEvent startEvent = new StartEvent(this);
            nextListener.onStart(startEvent);
        }
    }

    @Override
    public void onEnd(EndEvent event) {
        setVisible(false);
    }

    private void updateSegments() {
        segments[0] = value != 1 && value != 4;
        segments[1] = value != 5 && value != 6;
        segments[2] = value != 2;
        segments[3] = value != 1 && value != 4 && value != 7;
        segments[4] = value == 0 || value == 2 || value == 6 || value == 8;
        segments[5] = value != 1 && value != 2 && value != 3 && value != 7;
        segments[6] = value != 0 && value != 1 && value != 7;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);

        if (segments[0]) {
            g.fillRect(5, 5, width, height);
        }

        if (segments[1]) {
            g.fillRect(width + 5, 5, height, width);
        }

        if (segments[2]) {
            g.fillRect(width + 5, width + 5, height, width + 5);
        }

        if (segments[3]) {
            g.fillRect(5, width * 2 + 5, width, height);
        }

        if (segments[4]) {
            g.fillRect(5, width + 5, height, width);
        }

        if (segments[5]) {
            g.fillRect(5, 5, height, width);
        }

        if (segments[6]) {
            g.fillRect(5, width + 5, width, height);
        }
    }
}