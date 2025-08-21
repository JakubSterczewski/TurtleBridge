package p02.pres;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel {
    private Image backgroundImage;

    public GamePanel() {
        try {
            backgroundImage = ImageIO.read(new File("src/p02/assets/background.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this);
        }
    }
}