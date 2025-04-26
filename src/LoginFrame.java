import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Map;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final Color accentColor = new Color(231, 76, 60);
    private final Color textColor = new Color(236, 240, 241);
    private final Font titleFont = new Font("Segoe UI", Font.BOLD, 28);
    private final Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

    public LoginFrame() {
        setTitle("Student Management System - Login");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(400, 550));
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 450, 600, 15, 15));

        UserStorage.initDefaultUsers();

        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(41, 128, 185),
                        getWidth(), getHeight(), new Color(109, 213, 250));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(new Color(255, 255, 255, 15));
                for (int i = 0; i < getHeight(); i += 5) {
                    g2d.drawLine(0, i, getWidth(), i);
                }
            }
        };

        JPanel topBar = createTopBar();
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        centerPanel.setOpaque(false);

        JPanel logoPanel = createLogoPanel();
        JPanel formPanel = createLoginForm();
        JPanel quickLoginPanel = createQuickLoginPanel();
        JButton registerButton = createRegisterButton();

        centerPanel.add(logoPanel);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(formPanel);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(quickLoginPanel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(registerButton);
        centerPanel.add(Box.createVerticalGlue());

        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        addWindowDragListener();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
            }
        });

        addFadeInEffect();
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        topBar.setOpaque(false);

        JButton minimizeButton = new JButton("—");
        minimizeButton.setFont(new Font("Arial", Font.BOLD, 18));
        minimizeButton.setForeground(textColor);
        minimizeButton.setBorderPainted(false);
        minimizeButton.setContentAreaFilled(false);
        minimizeButton.setFocusPainted(false);
        minimizeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        minimizeButton.addActionListener(_ -> setState(JFrame.ICONIFIED));

        JButton closeButton = new JButton("×");
        closeButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeButton.setForeground(textColor);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(_ -> System.exit(0));
        topBar.add(minimizeButton);
        topBar.add(closeButton);

        return topBar;
    }

    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setOpaque(false);
        logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel logoLabel = createAnimatedLogo();
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Student Management", JLabel.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(textColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoPanel.add(logoLabel, BorderLayout.CENTER);
        logoPanel.add(titleLabel, BorderLayout.SOUTH);

        return logoPanel;
    }

    private JPanel createLoginForm() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(labelFont);
        usernameLabel.setForeground(textColor);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel usernamePanel = new JPanel(new BorderLayout(10, 0));
        usernamePanel.setOpaque(false);
        usernamePanel.setMaximumSize(new Dimension(350, 40));
        usernamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBackground(new Color(255, 255, 255, 200));
        usernameField.setForeground(new Color(44, 62, 80));
        usernameField.setCaretColor(new Color(44, 62, 80));
        usernameField.putClientProperty("JTextField.placeholderText", "Enter your username");
        usernameField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        usernamePanel.add(usernameField, BorderLayout.CENTER);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(textColor);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel passwordPanel = new JPanel(new BorderLayout(10, 0));
        passwordPanel.setOpaque(false);
        passwordPanel.setMaximumSize(new Dimension(350, 40));
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBackground(new Color(255, 255, 255, 200));
        passwordField.setForeground(new Color(44, 62, 80));
        passwordField.setCaretColor(new Color(44, 62, 80));
        passwordField.putClientProperty("JTextField.placeholderText", "Enter your password");
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(buttonFont);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(accentColor);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(350, 45));
        loginButton.setPreferredSize(new Dimension(350, 45));
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(accentColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(accentColor);
            }
        });

        loginButton.addActionListener(_ -> login());

        formPanel.add(usernameLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(usernamePanel);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(passwordPanel);
        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(loginButton);

        getRootPane().setDefaultButton(loginButton);

        return formPanel;
    }

    private JPanel createQuickLoginPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel quickLoginLabel = new JLabel("Quick Login:");
        quickLoginLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        quickLoginLabel.setForeground(textColor);

        RoundedButton adminQuickButton = new RoundedButton();
        adminQuickButton.setText("Admin");
        adminQuickButton.setBackground(new Color(41, 128, 185));
        adminQuickButton.setForeground(Color.WHITE);
        adminQuickButton.setFocusPainted(false);
        adminQuickButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        adminQuickButton.setPreferredSize(new Dimension(70, 40));
        adminQuickButton.setBorderPainted(false);
        adminQuickButton.addActionListener(_ -> quickLogin("admin", "admin"));

        RoundedButton teacherQuickButton = new RoundedButton();
        teacherQuickButton.setText("Teacher");
        teacherQuickButton.setBackground(new Color(46, 204, 113));
        teacherQuickButton.setForeground(Color.WHITE);
        teacherQuickButton.setFocusPainted(false);
        teacherQuickButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        teacherQuickButton.setPreferredSize(new Dimension(90, 40));
        teacherQuickButton.setBorderPainted(false);
        teacherQuickButton.addActionListener(_ -> quickLogin("teacher", "teacher"));

        RoundedButton studentQuickButton = new RoundedButton();
        studentQuickButton.setText("Student");
        studentQuickButton.setBackground(new Color(155, 89, 182));
        studentQuickButton.setForeground(Color.WHITE);
        studentQuickButton.setFocusPainted(false);
        studentQuickButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        studentQuickButton.setPreferredSize(new Dimension(90, 40));
        studentQuickButton.setBorderPainted(false);
        studentQuickButton.addActionListener(_ -> quickLogin("student1", "student1"));

        panel.add(quickLoginLabel);
        panel.add(adminQuickButton);
        panel.add(teacherQuickButton);
        panel.add(studentQuickButton);

        return panel;
    }

    private JButton createRegisterButton() {
        JButton registerButton = new JButton("Create an Account");
        registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        registerButton.setForeground(textColor);
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setForeground(new Color(255, 255, 255, 150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setForeground(textColor);
            }
        });

        registerButton.addActionListener(_ -> {
            new RegisterFrame().setVisible(true);
            dispose();
        });

        return registerButton;
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() && password.isEmpty()) {
            showErrorMessage("Please enter username and password");
            return;
        }
        if (username.isEmpty()) {
            showErrorMessage("Please enter a username");
            return;
        }
        if (password.isEmpty()) {
            showErrorMessage("Please enter a password");
            return;
        }

        Map<String, String> user = UserStorage.authenticateUser(username, password);

        if (user != null) {
            showSuccessAnimation(username, user.get("fullName"), user.get("role"));
        } else {
            showErrorMessage("Invalid username or password");
            shakeWindow();
        }
    }

    private void quickLogin(String username, String password) {
        usernameField.setText(username);
        passwordField.setText(password);

        Timer timer = new Timer(300, _ -> login());
        timer.setRepeats(false);
        timer.start();
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
                    Point currentLocation = getLocation();
                    setLocation(
                            currentLocation.x + e.getX() - dragPoint[0].x,
                            currentLocation.y + e.getY() - dragPoint[0].y);
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

    private JLabel createAnimatedLogo() {
        return new JLabel() {
            private int animationStep = 0;

            {
                Timer animationTimer = new Timer(50, _ -> {
                    animationStep = (animationStep + 1) % 20;
                    repaint();
                });
                animationTimer.start();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int centerX = getWidth() / 2;
                int offset = Math.min(5, animationStep / 2);
                g2d.setColor(Color.WHITE);
                int[] xPoints = { centerX - 20, centerX + 20, centerX };
                int[] yPoints = { 30 - offset, 30 - offset, 15 - offset };
                g2d.fillPolygon(xPoints, yPoints, 3);
                g2d.fillRect(centerX - 20, 30 - offset, 40, 5);
                g2d.setColor(new Color(255, 215, 0));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(
                        centerX + 10, 35 - offset,
                        centerX + 15 + (int) (Math.sin(animationStep * 0.3) * 3),
                        45 - offset + (int) (Math.cos(animationStep * 0.3) * 2));

                g2d.setColor(Color.WHITE);
                g2d.fillOval(centerX - 15, 40, 30, 30);
                g2d.fillRoundRect(centerX - 20, 70, 40, 30, 10, 10);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, 110);
            }
        };
    }

    private void showSuccessAnimation(String username, String fullName, String role) {
        try {

            JDialog loadingDialog = new JDialog(this, "", true);
            loadingDialog.setUndecorated(true);
            loadingDialog.setSize(300, 150);
            loadingDialog.setLocationRelativeTo(this);
            loadingDialog.setShape(new RoundRectangle2D.Double(0, 0, 300, 150, 15, 15));

            JPanel loadingPanel = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                    GradientPaint gp = new GradientPaint(
                            0, 0, new Color(46, 204, 113),
                            getWidth(), getHeight(), new Color(26, 188, 156));
                    g2d.setPaint(gp);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };

            JLabel welcomeLabel = new JLabel("Welcome, " + fullName + "!");
            welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            welcomeLabel.setForeground(Color.WHITE);
            welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
            welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

            JLabel roleLabel = new JLabel("Logging in as: " + role.toUpperCase());
            roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            roleLabel.setForeground(Color.WHITE);
            roleLabel.setHorizontalAlignment(JLabel.CENTER);
            roleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

            JLabel loadingLabel = new JLabel("Loading system...");
            loadingLabel.setForeground(Color.WHITE);
            loadingLabel.setHorizontalAlignment(JLabel.CENTER);
            loadingLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setOpaque(false);
            infoPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT); // Center the content
            infoPanel.add(welcomeLabel);
            infoPanel.add(roleLabel);
            infoPanel.add(loadingLabel);

            JPanel progressPanel = getJPanel();
            progressPanel.setPreferredSize(new Dimension(280, 10)); // Set fixed size for progress bar

            loadingPanel.add(infoPanel, BorderLayout.CENTER);
            loadingPanel.add(progressPanel, BorderLayout.SOUTH);
            loadingDialog.setContentPane(loadingPanel);

            Timer timer = new Timer(1500, _ -> {
                loadingDialog.dispose();
                openMainSystem(username);
            });
            timer.setRepeats(false);
            timer.start();

            loadingDialog.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            openMainSystem(username);
        }
    }

    private JPanel getJPanel() {
        JPanel progressPanel = new JPanel() {
            private int position = 0;

            {
                Timer animationTimer = new Timer(10, _ -> {
                    position = (position + 3) % (getWidth() + 50);
                    repaint();
                });
                animationTimer.start();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(Color.WHITE);
                int barWidth = 50;
                g2d.fillRoundRect(position - barWidth, getHeight() / 2 - 2, barWidth, 4, 2, 2);
            }
        };
        progressPanel.setPreferredSize(new Dimension(300, 20));
        return progressPanel;
    }

    private void showErrorMessage(String message) {
        try {
            JDialog errorDialog = new JDialog(this, "", true);
            errorDialog.setUndecorated(true);
            errorDialog.setSize(300, 180);
            errorDialog.setLocationRelativeTo(this);
            errorDialog.setShape(new RoundRectangle2D.Double(0, 0, 300, 180, 15, 15));
            errorDialog.getRootPane().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            JPanel errorPanel = getJPanel(errorDialog);

            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
            topPanel.setOpaque(false);

            JButton closeButton = createCloseButton(errorDialog);
            topPanel.add(closeButton);

            JPanel messagePanel = new JPanel();
            messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
            messagePanel.setOpaque(false);
            messagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel errorLabel = new JLabel(message);
            errorLabel.setForeground(Color.WHITE);
            errorLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            errorLabel.setHorizontalAlignment(JLabel.CENTER);
            errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            messagePanel.add(Box.createVerticalGlue());
            messagePanel.add(errorLabel);
            messagePanel.add(Box.createVerticalStrut(10));

            JButton okButton = createOkButton(errorDialog);
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            buttonPanel.setOpaque(false);
            buttonPanel.add(okButton);

            errorPanel.add(topPanel, BorderLayout.NORTH);
            errorPanel.add(messagePanel, BorderLayout.CENTER);
            errorPanel.add(buttonPanel, BorderLayout.SOUTH);

            errorDialog.setContentPane(errorPanel);
            errorDialog.setAlwaysOnTop(true);
            errorDialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JPanel getJPanel(JDialog errorDialog) {
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(231, 76, 60),
                0, errorDialog.getHeight(), new Color(192, 57, 43));

        return new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    private JButton createCloseButton(JDialog errorDialog) {
        JButton closeButton = new JButton("×");
        closeButton.setFont(new Font("Arial", Font.BOLD, 18));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(_ -> errorDialog.dispose());
        return closeButton;
    }

    private JButton createOkButton(JDialog errorDialog) {
        JButton okButton = new JButton("OK");
        okButton.setBackground(new Color(255, 255, 255, 100));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.addActionListener(_ -> errorDialog.dispose());
        return okButton;
    }

    private void openMainSystem(String username) {
        new MainSystemFrame(username).setVisible(true);
        dispose();
    }

    private void shakeWindow() {
        final int[] direction = { 1 };
        final int[] cycles = { 0 };

        Timer timer = new Timer(30, e -> {
            setLocation(getX() + 8 * direction[0], getY());
            if (++cycles[0] % 2 == 0)
                direction[0] = -direction[0];
            if (cycles[0] == 10) {
                ((Timer) e.getSource()).stop();
                setLocationRelativeTo(null);
            }
        });
        timer.start();
    }

}