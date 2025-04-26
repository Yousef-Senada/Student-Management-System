import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Objects;

public class RegisterFrame extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JTextField emailField;
    private final JTextField fullNameField;
    private final JComboBox<String> roleComboBox;
    private final Color primaryColor = new Color(41, 128, 185);
    private final Color secondaryColor = new Color(52, 152, 219);
    private final Color accentColor = new Color(46, 204, 113);
    private final Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

    public RegisterFrame() {
        setTitle("System Management - Registration");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(400, 550));
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 500, 600, 15, 15));

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, primaryColor, 0, getHeight(), secondaryColor);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));

        JPanel topBarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        topBarPanel.setOpaque(false);

        JButton minimizeButton = new JButton("—");
        minimizeButton.setFont(new Font("Arial", Font.BOLD, 16));
        minimizeButton.setForeground(Color.WHITE);
        minimizeButton.setBorderPainted(false);
        minimizeButton.setContentAreaFilled(false);
        minimizeButton.setFocusPainted(false);
        minimizeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        minimizeButton.addActionListener(_ -> setState(JFrame.ICONIFIED));

        JButton closeButton = new JButton("×");
        closeButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(_ -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            dispose();
        });

        topBarPanel.add(minimizeButton);
        topBarPanel.add(closeButton);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("User Registration", JLabel.CENTER);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 24);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(topBarPanel, BorderLayout.NORTH);

        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.setOpaque(false);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel fullNameLabelPanel = createLabelPanel("Full Name");
        JPanel fullNamePanel = createFieldPanel(IconManager.createUserIcon());
        fullNameField = new JTextField(15);
        stylizeTextField(fullNameField);
        fullNamePanel.add(fullNameField, BorderLayout.CENTER);

        JPanel emailLabelPanel = createLabelPanel("Email");
        JPanel emailPanel = createFieldPanel(IconManager.createUserIcon());
        emailField = new JTextField(15);
        stylizeTextField(emailField);
        emailPanel.add(emailField, BorderLayout.CENTER);

        JPanel usernameLabelPanel = createLabelPanel("Username");
        JPanel usernamePanel = createFieldPanel(IconManager.createUserIcon());
        usernameField = new JTextField(15);
        stylizeTextField(usernameField);
        usernamePanel.add(usernameField, BorderLayout.CENTER);

        JPanel passwordLabelPanel = createLabelPanel("Password");
        JPanel passwordPanel = createFieldPanel(IconManager.createPasswordIcon());
        passwordField = new JPasswordField(15);
        stylizeTextField(passwordField);
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        JPanel confirmPasswordLabelPanel = createLabelPanel("Confirm Password");
        JPanel confirmPasswordPanel = createFieldPanel(IconManager.createPasswordIcon());
        confirmPasswordField = new JPasswordField(15);
        stylizeTextField(confirmPasswordField);
        confirmPasswordPanel.add(confirmPasswordField, BorderLayout.CENTER);

        JPanel roleLabelPanel = createLabelPanel("Role");
        JPanel rolePanel = new JPanel(new BorderLayout(10, 0));
        rolePanel.setOpaque(false);

        String[] roles = { "Student", "Teacher" };
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleComboBox.setForeground(Color.WHITE);
        roleComboBox.setBackground(new Color(41, 128, 185));
        roleComboBox.setPreferredSize(new Dimension(200, 30));
        rolePanel.add(roleComboBox, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton registerButton = new JButton("Register");
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        registerButton.setFont(buttonFont);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(accentColor);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setBorderPainted(false);
        registerButton.setPreferredSize(new Dimension(130, 40));
        registerButton.setMaximumSize(new Dimension(130, 40));
        registerButton.setMinimumSize(new Dimension(130, 40));
        registerButton.setContentAreaFilled(false);
        registerButton.setOpaque(true);

        registerButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                registerButton.setBackground(accentColor.darker());
            }

            public void mouseExited(MouseEvent e) {
                registerButton.setBackground(accentColor);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = fullNameField.getText().trim();
                String email = emailField.getText().trim();
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
                String role = Objects.requireNonNull(roleComboBox.getSelectedItem()).toString().toLowerCase();

                if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() ||
                        password.isEmpty() || confirmPassword.isEmpty()) {
                    showError("Please fill in all fields");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    showError("Passwords do not match");
                    return;
                }

                if (!isValidEmail(email)) {
                    showError("Please enter a valid email address");
                    return;
                }

                if (password.length() < 6) {
                    showError("Password must be at least 6 characters long");
                    return;
                }

                boolean success = UserStorage.saveUser(username, password, fullName, email, role);

                if (success) {
                    showSuccess("Registration successful! You can now login.");
                    LoginFrame loginFrame = new LoginFrame();
                    loginFrame.setVisible(true);
                    dispose();
                } else {
                    showError("Username already exists. Please choose another one.");
                }
            }
        });

        JButton cancelButton = new JButton("Back to Login");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorderPainted(false);
        cancelButton.setContentAreaFilled(false);
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        cancelButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                cancelButton.setForeground(new Color(255, 255, 255, 150));
            }

            public void mouseExited(MouseEvent e) {
                cancelButton.setForeground(Color.WHITE);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                dispose();
            }
        });

        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(fullNameLabelPanel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(fullNamePanel);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(emailLabelPanel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(emailPanel);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(usernameLabelPanel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(usernamePanel);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(passwordLabelPanel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(passwordPanel);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(confirmPasswordLabelPanel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(confirmPasswordPanel);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(roleLabelPanel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(rolePanel);

        buttonsPanel.add(Box.createVerticalStrut(20));
        buttonsPanel.add(registerButton);
        buttonsPanel.add(Box.createVerticalStrut(15));
        buttonsPanel.add(cancelButton);

        formWrapper.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formWrapper, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        getRootPane().setDefaultButton(registerButton);

        addWindowDragListener();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
            }
        });

        addFadeInEffect();
    }

    private JPanel createLabelPanel(String text) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setOpaque(false);

        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        label.setForeground(Color.WHITE);
        panel.add(label);

        return panel;
    }

    private JPanel createFieldPanel(JLabel iconLabel) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);
        panel.add(iconLabel, BorderLayout.WEST);
        return panel;
    }

    private void stylizeTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
    }

    private void showError(String message) {
        JDialog errorDialog = createDialog(new Color(231, 76, 60), new Color(192, 57, 43), message);
        errorDialog.setVisible(true);
    }

    private void showSuccess(String message) {
        JDialog successDialog = createDialog(new Color(46, 204, 113), new Color(26, 188, 156), message);
        successDialog.setVisible(true);
    }

    private JDialog createDialog(Color startColor, Color endColor, String message) {
        JDialog dialog = new JDialog(this, "", true);
        dialog.setUndecorated(true);
        dialog.setSize(300, 100);
        dialog.setLocationRelativeTo(this);
        dialog.setShape(new RoundRectangle2D.Double(0, 0, 300, 100, 15, 15));

        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setPaint(new GradientPaint(0, 0, startColor, getWidth(), getHeight(), endColor));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JLabel label = new JLabel(message);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JButton okButton = new JButton("OK");
        okButton.setBackground(new Color(255, 255, 255, 80));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.addActionListener(_ -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(okButton);

        panel.add(label, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        return dialog;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private void addWindowDragListener() {
        final Point[] dragPoint = { null };

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                dragPoint[0] = e.getPoint();
            }

            public void mouseReleased(MouseEvent e) {
                dragPoint[0] = null;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (dragPoint[0] != null) {
                    Point current = getLocation();
                    setLocation(current.x + e.getX() - dragPoint[0].x, current.y + e.getY() - dragPoint[0].y);
                }
            }
        });
    }

    private void addFadeInEffect() {
        setOpacity(0.0f);

        final float[] opacity = { 0.0f };
        Timer fadeTimer = new Timer(15, e -> {
            opacity[0] += 0.05f;
            if (opacity[0] >= 1.0f) {
                opacity[0] = 1.0f;
                ((Timer) e.getSource()).stop();
            }
            setOpacity(opacity[0]);
        });

        fadeTimer.start();
    }

}