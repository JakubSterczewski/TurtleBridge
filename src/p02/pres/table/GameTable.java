package p02.pres.table;

import javax.swing.*;

public class GameTable extends JTable {
    public GameTable(GameTableModel model) {
        setModel(model);
        setRowHeight(750/5);

        setOpaque(false);
        setShowGrid(false);

        ImageCellRenderer renderer = new ImageCellRenderer();
        setDefaultRenderer(ImageIcon.class, renderer);
    }
}