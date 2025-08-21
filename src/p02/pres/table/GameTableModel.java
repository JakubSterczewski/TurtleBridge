package p02.pres.table;

import p02.game.Board;
import p02.game.BoardTable;
import p02.game.events.TickEvent;
import p02.game.interfaces.TickListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GameTableModel extends DefaultTableModel implements TickListener {
    private final int[][] localBoard;
    private final BoardTable boardTable;

    public GameTableModel(Board board) {
        this.boardTable = board.getBoardTable();
        localBoard = new int[getRowCount()][getColumnCount()];

        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                localBoard[i][j] = boardTable.getBoardValue(i, j);
            }
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public int getRowCount() {
        return 5;
    }

    @Override
    public int getColumnCount() {
        return 12;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return localBoard[rowIndex][columnIndex];
    }

    public void checkFire(){
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                int newVal = boardTable.getBoardValue(i, j);
                if (localBoard[i][j] != newVal) {
                    localBoard[i][j] = newVal;
                    fireTableCellUpdated(i, j);
                }
            }
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ImageIcon.class;
    }

    @Override
    public void onTick(TickEvent event) {
        checkFire();
    }
}