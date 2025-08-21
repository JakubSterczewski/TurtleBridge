package p02.pres.table;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ImageCellRenderer extends JLayeredPane implements TableCellRenderer {
    public ImageCellRenderer() {
        setLayout(new BorderLayout());
        setOpaque(false);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        removeAll();

        ImageIcon imageIcon = null;
        String layoutRegion = BorderLayout.NORTH;

        switch (String.valueOf(value)) {
            case "1" -> {
                if (column == 2 || column == 4 || column == 6 || column == 8) {
                    imageIcon = new ImageIcon("src/p02/assets/playerJumpRight.png");
                } else if (column == 10) {
                    imageIcon = new ImageIcon("src/p02/assets/playerGiver.png");
                    layoutRegion = BorderLayout.CENTER;
                } else {
                    imageIcon = new ImageIcon("src/p02/assets/playerRight.png");
                    layoutRegion = BorderLayout.SOUTH;
                }
            }
            case "2" -> {
                if (column == 2 || column == 4 || column == 6 || column == 8) {
                    imageIcon = new ImageIcon("src/p02/assets/playerJumpLeft.png");
                } else {
                    imageIcon = new ImageIcon("src/p02/assets/playerLeft.png");
                    layoutRegion = BorderLayout.SOUTH;
                }
            }
            case "3" -> {
                if (column == 2 || column == 4 || column == 6 || column == 8) {
                    imageIcon = new ImageIcon("src/p02/assets/playerJumpRightWithPackage.png");
                } else if (column == 10) {
                    imageIcon = new ImageIcon("src/p02/assets/playerGiverWithPackage.png");
                    layoutRegion = BorderLayout.CENTER;
                } else {
                    imageIcon = new ImageIcon("src/p02/assets/playerRightWithPackage.png");
                    layoutRegion = BorderLayout.SOUTH;
                }
            }
            case "4" -> {
                if (column == 2 || column == 4 || column == 6 || column == 8) {
                    imageIcon = new ImageIcon("src/p02/assets/playerJumpLeftWithPackage.png");
                } else {
                    imageIcon = new ImageIcon("src/p02/assets/playerLeftWithPackage.png");
                    layoutRegion = BorderLayout.SOUTH;
                }
            }
            case "5" -> {
                imageIcon = new ImageIcon("src/p02/assets/turtleAbove.png");
            }
            case "6" -> {
                imageIcon = new ImageIcon("src/p02/assets/turtleBelow.png");
            }
            case "7" -> {
                imageIcon = new ImageIcon("src/p02/assets/fish.png");
            }
            case "8" -> {
                imageIcon = new ImageIcon("src/p02/assets/receiver.png");
                layoutRegion = BorderLayout.CENTER;
            }
        }

        if (imageIcon != null) {
            add(new ImageComponent(imageIcon), layoutRegion);
        }

        return this;
    }

    private static class ImageComponent extends JComponent {
        private final ImageIcon icon;

        public ImageComponent(ImageIcon icon) {
            this.icon = icon;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (icon != null) {
                int x = (getWidth() - icon.getIconWidth()) / 2;
                int y = (getHeight() - icon.getIconHeight()) / 2;
                g.drawImage(icon.getImage(), x, y, this);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(icon.getIconWidth(), icon.getIconHeight());
        }
    }
}