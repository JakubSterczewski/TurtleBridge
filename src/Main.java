import p02.game.Board;
import p02.pres.GamePanel;
import p02.pres.table.GameTable;
import p02.pres.table.GameTableModel;
import p02.pres.digit.ScoreDisplay;
import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    public Main() {
        super("Turtle bridge");
        int width = 1215;
        int height = 785;

        Board board = new Board();
        GameTableModel model = new GameTableModel(board);
        GameTable table = new GameTable(model);
        ScoreDisplay scoreDisplay = new ScoreDisplay(board);

        board.setScoreDisplay(scoreDisplay);
        board.setGameTableModel(model);

        board.addEndListener(scoreDisplay);

        GamePanel backgroundPanel = new GamePanel();
        backgroundPanel.setBounds(0, 0, width, height);
        table.setBounds(0, 0, width, height);
        scoreDisplay.setBounds(0, 0, 200, 200);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(width, height));

        layeredPane.add(backgroundPanel, Integer.valueOf(0));
        layeredPane.add(table, Integer.valueOf(1));
        layeredPane.add(scoreDisplay, Integer.valueOf(2));
        layeredPane.add(board, Integer.valueOf(3));
        add(layeredPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setVisible(true);

        board.requestFocus();
    }
}