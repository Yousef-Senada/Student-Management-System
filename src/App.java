import javax.swing.SwingUtilities;
public class App {

    public static void main(String[] args) {
        UserStorage.initDefaultUsers();
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
