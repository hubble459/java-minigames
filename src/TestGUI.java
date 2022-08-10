import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class TestGUI extends JFrame {
    public static void main(String[] args) {
        TestGUI test = new TestGUI();
        test.setVisible(true);
    }

    public TestGUI() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(500, 200));
        getContentPane().add(panel);
        setSize(550, 300);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        try {
            BufferedImage image = ImageIO.read(new URL("https://icatcare.org/app/uploads/2019/09/The-Kitten-Checklist-1.png"));
            g.drawImage(image, 0,0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void paint(Graphics g) {
//        Line2D line;
//        super.paint(g);
//        Graphics2D graphics = (Graphics2D) g;
//        // Bottom
//        line = new Line2D.Float(0, 200, 300, 200);
//        graphics.draw(line);
//        // Main Beam
//        line = new Line2D.Float(200, 100, 200, 200);
//        graphics.draw(line);
//        // Support 1
//        line = new Line2D.Float(200, 180, 220, 200);
//        graphics.draw(line);
//        // Support 2
//        line = new Line2D.Float(200, 180, 180, 200);
//        graphics.draw(line);
//        // Top Beam
//        line = new Line2D.Float(200, 100, 100, 100);
//        graphics.draw(line);
//        // Rope
//        line = new Line2D.Float(150, 100, 150, 150);
//        graphics.draw(line);
//        // Head
//        graphics.drawOval(145, 132, 20, 20);
//        // Body
//        line = new Line2D.Float(150, 150, 140, 170);
//        graphics.draw(line);
//        // Arm 1
//        line = new Line2D.Float(140, 150, 130, 140);
//        graphics.draw(line);
//        // Arm 2
//        line = new Line2D.Float(150, 150, 140, 170);
//        graphics.draw(line);
//        graphics.drawString("UWU", 50, 50);
//
//    }
}
