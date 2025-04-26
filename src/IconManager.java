import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class IconManager {

    public static JLabel createUserIcon() {
        BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);

        g2d.fillOval(7, 2, 10, 10);

        g2d.fillRoundRect(5, 12, 14, 10, 5, 5);

        g2d.dispose();
        return new JLabel(new ImageIcon(image));
    }

    public static JLabel createPasswordIcon() {
        BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(6, 10, 12, 12, 3, 3);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawArc(8, 3, 8, 10, 0, 180);
        g2d.dispose();
        return new JLabel(new ImageIcon(image));
    }

    public static JLabel createLogoIcon() {
        BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);

        g2d.fillOval(20, 10, 24, 24);

        g2d.fillRoundRect(16, 34, 32, 26, 10, 10);

        g2d.dispose();
        return new JLabel(new ImageIcon(image));
    }

    public static JLabel createColorCircleIcon(Color color, int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(color);
        g2d.fillOval(2, 2, size - 4, size - 4);

        g2d.dispose();
        return new JLabel(new ImageIcon(image));
    }
}