import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class IconGenerator {
    public static void main(String[] args) {
        createIcons();
    }

    public static void createIcons() {
        createAppIcon();
        createLoginIcon();
        createUserIcon();
        createPasswordIcon();
    }

    private static void createAppIcon() {
        try {
            BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();

            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setColor(new Color(41, 128, 185));
            g.fillRoundRect(0, 0, 64, 64, 10, 10);

            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(4));

            g.drawOval(12, 12, 40, 40);

            int teeth = 8;
            int toothLength = 8;
            for (int i = 0; i < teeth; i++) {
                double angle = 2 * Math.PI * i / teeth;
                int x1 = 32 + (int) (20 * Math.cos(angle));
                int y1 = 32 + (int) (20 * Math.sin(angle));
                int x2 = 32 + (int) ((20 + toothLength) * Math.cos(angle));
                int y2 = 32 + (int) ((20 + toothLength) * Math.sin(angle));
                g.drawLine(x1, y1, x2, y2);
            }

            g.dispose();

            File outputFile = new File("src/icons/app_icon.png");
            ImageIO.write(image, "png", outputFile);
            System.out.println("App icon created at: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createLoginIcon() {
        try {
            BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();

            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.WHITE);
            g.fillOval(20, 10, 24, 24);
            g.fillRoundRect(16, 34, 32, 26, 10, 10);

            g.dispose();

            File outputFile = new File("src/icons/login_icon.png");
            ImageIO.write(image, "png", outputFile);
            System.out.println("Login icon created at: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createUserIcon() {
        try {
            BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();

            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setColor(Color.WHITE);
            g.fillOval(7, 2, 10, 10);
            g.fillRoundRect(5, 12, 14, 10, 5, 5);

            g.dispose();

            File outputFile = new File("src/icons/user_icon.png");
            ImageIO.write(image, "png", outputFile);
            System.out.println("User icon created at: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createPasswordIcon() {
        try {
            BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();

            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setColor(Color.WHITE);
            g.fillRoundRect(6, 10, 12, 12, 3, 3);

            g.setStroke(new BasicStroke(2));
            g.drawArc(8, 3, 8, 10, 0, 180);

            g.dispose();

            File outputFile = new File("src/icons/password_icon.png");
            ImageIO.write(image, "png", outputFile);
            System.out.println("Password icon created at: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}