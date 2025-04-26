import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MainSystemFrame extends JFrame {
    private String currentUser;
    private String userRole;
    private Color primaryColor = new Color(41, 128, 185);
    private Color secondaryColor = new Color(52, 152, 219);
    private Color accentColor = new Color(46, 204, 113);
    private List<User> users;
    private List<Student> students;
    private List<Course> courses;
    private List<Enrollment> enrollments;
    Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

    public MainSystemFrame(String username) {
        this.currentUser = username;
        initializeSampleData();
        this.userRole = getUserRole(username);

        setTitle("Student Management System - Welcome " + username + " (" + userRole + ")");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, primaryColor, getWidth(), 0, secondaryColor);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(900, 60));

        JLabel titleLabel = new JLabel("Student Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);

        JLabel userLabel = new JLabel("Logged in as: " + username);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(Color.WHITE);
        userLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        logoutButton.addActionListener(_ -> {
            int confirm = JOptionPane.showConfirmDialog(
                    MainSystemFrame.this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });

        userPanel.add(userLabel);
        userPanel.add(logoutButton);
        topPanel.add(userPanel, BorderLayout.EAST);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        if ("Admin".equals(userRole)) {
            tabbedPane.addTab("Users", createUserPanel());
            tabbedPane.addTab("Students", createStudentPanel());
            tabbedPane.addTab("Courses", createCoursePanel());
            tabbedPane.addTab("Enrollments", createEnrollmentPanel());
        } else if ("Teacher".equals(userRole)) {
            tabbedPane.addTab("Students", createStudentPanel());
            tabbedPane.addTab("Courses", createCoursePanel());
            tabbedPane.addTab("Enrollments", createEnrollmentPanel());
        } else if ("Student".equals(userRole)) {
            tabbedPane.addTab("My Profile", createStudentProfilePanel());
            tabbedPane.addTab("My Courses", createStudentCoursesPanel());
            tabbedPane.addTab("My Grades", createStudentGradesPanel());
        }

        // Main content
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        // Main layout
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void initializeSampleData() {

        users = new ArrayList<>();
        users.add(new User(1, "admin", "admin", "System Administrator", "admin@system.com", "Admin"));
        users.add(new User(2, "student1", "student1", "John Student", "john@student.com", "Student"));
        users.add(new User(3, "student2", "student2", "Jane Student", "jane@student.com", "Student"));
        users.add(new User(4, "teacher", "teacher", "Teacher Account", "teacher@school.edu", "Teacher"));

        students = new ArrayList<>();
        students.add(new Student(1, "S1001", "John Student", "john@student.com", "Computer Science", 2, users.get(1)));
        students.add(new Student(2, "S1002", "Jane Student", "jane@student.com", "Business Administration", 3,
                users.get(2)));

        courses = new ArrayList<>();
        courses.add(new Course(1, "CS101", "Introduction to Programming", "Basic programming concepts using Java", 3));
        courses.add(new Course(2, "CS201", "Data Structures", "Advanced data structures and algorithms", 4));
        courses.add(new Course(3, "MATH101", "Calculus I", "Introduction to differential calculus", 3));
        courses.add(new Course(4, "ENG101", "English Composition", "Basic writing and composition skills", 3));
        courses.add(new Course(5, "BUS101", "Introduction to Business", "Fundamentals of business operations", 3));

        enrollments = new ArrayList<>();
        enrollments.add(new Enrollment(1, students.get(0), courses.get(0), new java.util.Date(), "Active"));
        enrollments.add(new Enrollment(2, students.get(0), courses.get(2), new java.util.Date(), "Active"));
        enrollments.add(new Enrollment(3, students.get(1), courses.get(1), new java.util.Date(), "Active"));
        enrollments.add(new Enrollment(4, students.get(1), courses.get(4), new java.util.Date(), "Active"));

        enrollments.get(0).setGrade("A");
        enrollments.get(2).setGrade("B+");

    }

    private String getUserRole(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user.getRole();
            }
        }
        return "User";
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.addColumn("ID");
        tableModel.addColumn("Username");
        tableModel.addColumn("Full Name");
        tableModel.addColumn("Email");
        tableModel.addColumn("Role");

        for (User user : users) {
            if ("Admin".equals(userRole) ||
                    ("Manager".equals(userRole) && !"Admin".equals(user.getRole())) ||
                    (!("Admin".equals(userRole) || "Manager".equals(userRole)) &&
                            (user.getUsername().equals(currentUser) || "Guest".equals(user.getRole())))) {

                tableModel.addRow(new Object[] {
                        user.getId(),
                        user.getUsername(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getRole()
                });
            }
        }

        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));

        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        RoundedButton addButton = new RoundedButton();
        addButton.setText("Add");
        addButton.setFont(buttonFont);
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setPreferredSize(new Dimension(90, 40));

        RoundedButton editButton = new RoundedButton();
        editButton.setText("Edit");
        editButton.setFont(buttonFont);
        editButton.setBackground(new Color(52, 152, 219));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setBorderPainted(false);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editButton.setPreferredSize(new Dimension(90, 40));

        RoundedButton deleteButton = new RoundedButton();
        deleteButton.setText("Delete");
        deleteButton.setFont(buttonFont);
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.setPreferredSize(new Dimension(90, 40));

        RoundedButton refreshButton = new RoundedButton();
        refreshButton.setText("Refresh");
        refreshButton.setFont(buttonFont);
        refreshButton.setBackground(new Color(155, 89, 182));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.setPreferredSize(new Dimension(90, 40));

        if ("Admin".equals(userRole)) {
            buttonPanel.add(addButton);
            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(refreshButton);
        } else if ("Manager".equals(userRole)) {
            buttonPanel.add(addButton);
            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(refreshButton);
        } else {
            buttonPanel.add(editButton);
            buttonPanel.add(refreshButton);
        }

        panel.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUserDialog(null, "Admin".equals(userRole));
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String selectedUsername = (String) table.getValueAt(selectedRow, 1);
                    String selectedRole = (String) table.getValueAt(selectedRow, 4);

                    boolean canEdit = false;

                    if ("Admin".equals(userRole)) {
                        canEdit = true;
                    } else if ("Manager".equals(userRole)) {
                        canEdit = !"Admin".equals(selectedRole);
                    } else {
                        canEdit = selectedUsername.equals(currentUser);
                    }

                    if (canEdit) {
                        User userToEdit = null;
                        for (User user : users) {
                            if (user.getUsername().equals(selectedUsername)) {
                                userToEdit = user;
                                break;
                            }
                        }

                        if (userToEdit != null) {
                            showUserDialog(userToEdit, "Admin".equals(userRole));
                        }
                    } else {
                        JOptionPane.showMessageDialog(MainSystemFrame.this,
                                "You don't have permission to edit this user",
                                "Permission Denied", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(MainSystemFrame.this,
                            "Please select a user to edit",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String selectedUsername = (String) table.getValueAt(selectedRow, 1);
                    String selectedRole = (String) table.getValueAt(selectedRow, 4);

                    boolean canDelete = false;

                    if ("Admin".equals(userRole)) {
                        canDelete = !selectedUsername.equals(currentUser);
                    } else if ("Manager".equals(userRole)) {
                        canDelete = !"Admin".equals(selectedRole) && !selectedUsername.equals(currentUser);
                    } else {
                        canDelete = false;
                    }

                    if (canDelete) {
                        int result = JOptionPane.showConfirmDialog(MainSystemFrame.this,
                                "Are you sure you want to delete this user?",
                                "Confirm Delete", JOptionPane.YES_NO_OPTION);

                        if (result == JOptionPane.YES_OPTION) {
                            User userToDelete = null;
                            for (User user : users) {
                                if (user.getUsername().equals(selectedUsername)) {
                                    userToDelete = user;
                                    break;
                                }
                            }

                            if (userToDelete != null) {
                                users.remove(userToDelete);
                                tableModel.removeRow(selectedRow);

                                JOptionPane.showMessageDialog(MainSystemFrame.this,
                                        "User deleted successfully",
                                        "Delete Success", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(MainSystemFrame.this,
                                "You don't have permission to delete this user",
                                "Permission Denied", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(MainSystemFrame.this,
                            "Please select a user to delete",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                for (User user : users) {
                    tableModel.addRow(new Object[] {
                            user.getId(),
                            user.getUsername(),
                            user.getFullName(),
                            user.getEmail(),
                            user.getRole()
                    });
                }
            }
        });

        return panel;
    }

    private void showUserDialog(User user, boolean canAssignAdmin) {
        final boolean isNewUser = (user == null);

        JDialog dialog = new JDialog(this, isNewUser ? "Add User" : "Edit User", true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JLabel fullNameLabel = new JLabel("Full Name:");
        JTextField fullNameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel roleLabel = new JLabel("Role:");
        String[] roles;

        if (canAssignAdmin) {
            roles = new String[] { "Admin", "Manager", "User", "Guest" };
        } else if ("Manager".equals(userRole)) {
            roles = new String[] { "User", "Guest" };
        } else {
            roles = new String[] { "User" };
        }

        JComboBox<String> roleComboBox = new JComboBox<>(roles);

        if (!isNewUser) {
            idField.setText(String.valueOf(user.getId()));
            usernameField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            fullNameField.setText(user.getFullName());
            emailField.setText(user.getEmail());
            roleComboBox.setSelectedItem(user.getRole());

            idField.setEditable(false);
        } else {
            idField.setText(String.valueOf(getNextUserId()));
            idField.setEditable(false);
        }

        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(fullNameLabel);
        formPanel.add(fullNameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(roleLabel);
        formPanel.add(roleComboBox);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(accentColor);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);

        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(mainPanel);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usernameField.getText().isEmpty() || fullNameField.getText().isEmpty() ||
                        emailField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Please fill in all required fields",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int id = Integer.parseInt(idField.getText());
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String fullName = fullNameField.getText();
                String email = emailField.getText();
                String role = (String) roleComboBox.getSelectedItem();

                if (isNewUser) {

                    User newUser = new User(id, username, password, fullName, email, role);
                    users.add(newUser);

                    DefaultTableModel model = (DefaultTableModel) ((JTable) ((JScrollPane) ((JPanel) dialog.getParent())
                            .getComponent(0)).getViewport().getView()).getModel();
                    model.addRow(new Object[] { id, username, fullName, email, role });
                } else {

                    user.setUsername(username);
                    user.setPassword(password);
                    user.setFullName(fullName);
                    user.setEmail(email);
                    user.setRole(role);

                    refreshUserTable();
                }

                JOptionPane.showMessageDialog(dialog,
                        "User saved successfully",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private int getNextUserId() {
        int maxId = 0;
        for (User user : users) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }
        return maxId + 1;
    }

    private void refreshUserTable() {

        JTabbedPane tabbedPane = (JTabbedPane) ((JPanel) getContentPane().getComponent(1)).getComponent(0);
        JPanel userPanel = (JPanel) tabbedPane.getSelectedComponent();
        JScrollPane scrollPane = (JScrollPane) userPanel.getComponent(0);
        JTable table = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        for (User user : users) {
            model.addRow(new Object[] {
                    user.getId(),
                    user.getUsername(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getRole()
            });
        }
    }

    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.addColumn("ID");
        tableModel.addColumn("Student ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Email");
        tableModel.addColumn("Program");
        tableModel.addColumn("Year Level");

        for (Student student : students) {
            tableModel.addRow(new Object[] {
                    student.getId(),
                    student.getStudentId(),
                    student.getName(),
                    student.getEmail(),
                    student.getProgram(),
                    student.getYearLevel()
            });
        }

        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        RoundedButton addButton = new RoundedButton();
        addButton.setText("Add");
        addButton.setFont(buttonFont);
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setPreferredSize(new Dimension(90, 40));

        RoundedButton editButton = new RoundedButton();
        editButton.setText("Edit");
        editButton.setFont(buttonFont);
        editButton.setBackground(new Color(52, 152, 219));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setBorderPainted(false);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editButton.setPreferredSize(new Dimension(90, 40));

        RoundedButton deleteButton = new RoundedButton();
        deleteButton.setText("Delete");
        deleteButton.setFont(buttonFont);
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.setPreferredSize(new Dimension(90, 40));

        RoundedButton refreshButton = new RoundedButton();
        refreshButton.setText("Refresh");
        refreshButton.setFont(buttonFont);
        refreshButton.setBackground(new Color(155, 89, 182));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.setPreferredSize(new Dimension(90, 40));

        RoundedButton viewCoursesButton = new RoundedButton();
        viewCoursesButton.setText("View Courses");
        viewCoursesButton.setFont(buttonFont);
        viewCoursesButton.setBackground(new Color(241, 196, 15));
        viewCoursesButton.setForeground(Color.WHITE);
        viewCoursesButton.setFocusPainted(false);
        viewCoursesButton.setBorderPainted(false);
        viewCoursesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewCoursesButton.setPreferredSize(new Dimension(140, 40));

        if ("Admin".equals(userRole)) {
            buttonPanel.add(addButton);
            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);
        }
        buttonPanel.add(viewCoursesButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStudentDialog(null);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int studentId = (Integer) table.getValueAt(selectedRow, 0);
                    Student studentToEdit = null;

                    for (Student student : students) {
                        if (student.getId() == studentId) {
                            studentToEdit = student;
                            break;
                        }
                    }

                    if (studentToEdit != null) {
                        showStudentDialog(studentToEdit);
                    }
                } else {
                    JOptionPane.showMessageDialog(MainSystemFrame.this,
                            "Please select a student to edit",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int studentId = (Integer) table.getValueAt(selectedRow, 0);

                    int result = JOptionPane.showConfirmDialog(MainSystemFrame.this,
                            "Are you sure you want to delete this student?",
                            "Confirm Delete", JOptionPane.YES_NO_OPTION);

                    if (result == JOptionPane.YES_OPTION) {
                        Student studentToDelete = null;
                        for (Student student : students) {
                            if (student.getId() == studentId) {
                                studentToDelete = student;
                                break;
                            }
                        }

                        if (studentToDelete != null) {
                            students.remove(studentToDelete);
                            tableModel.removeRow(selectedRow);

                            List<Enrollment> enrollmentsToRemove = new ArrayList<>();
                            for (Enrollment enrollment : enrollments) {
                                if (enrollment.getStudent().getId() == studentId) {
                                    enrollmentsToRemove.add(enrollment);
                                }
                            }
                            enrollments.removeAll(enrollmentsToRemove);

                            JOptionPane.showMessageDialog(MainSystemFrame.this,
                                    "Student deleted successfully",
                                    "Delete Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(MainSystemFrame.this,
                            "Please select a student to delete",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        viewCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int studentId = (Integer) table.getValueAt(selectedRow, 0);
                    Student selectedStudent = null;

                    for (Student student : students) {
                        if (student.getId() == studentId) {
                            selectedStudent = student;
                            break;
                        }
                    }

                    if (selectedStudent != null) {
                        showStudentCoursesDialog(selectedStudent);
                    }
                } else {
                    JOptionPane.showMessageDialog(MainSystemFrame.this,
                            "Please select a student to view courses",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshStudentTable(tableModel);
            }
        });

        return panel;
    }

    private void refreshStudentTable(DefaultTableModel model) {
        model.setRowCount(0);

        for (Student student : students) {
            model.addRow(new Object[] {
                    student.getId(),
                    student.getStudentId(),
                    student.getName(),
                    student.getEmail(),
                    student.getProgram(),
                    student.getYearLevel()
            });
        }
    }

    private void showStudentDialog(Student student) {
        final boolean isNewStudent = (student == null);

        JDialog dialog = new JDialog(this, isNewStudent ? "Add Student" : "Edit Student", true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();

        JLabel studentIdLabel = new JLabel("Student ID:");
        JTextField studentIdField = new JTextField();

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel programLabel = new JLabel("Program:");
        JTextField programField = new JTextField();

        JLabel yearLabel = new JLabel("Year Level:");
        JComboBox<Integer> yearComboBox = new JComboBox<>(new Integer[] { 1, 2, 3, 4, 5 });

        JLabel userLabel = new JLabel("User Account:");
        JComboBox<String> userComboBox = new JComboBox<>();

        for (User user : users) {
            if ("Student".equals(user.getRole())) {
                userComboBox.addItem(user.getUsername() + " (" + user.getFullName() + ")");
            }
        }

        if (!isNewStudent) {
            idField.setText(String.valueOf(student.getId()));
            studentIdField.setText(student.getStudentId());
            nameField.setText(student.getName());
            emailField.setText(student.getEmail());
            programField.setText(student.getProgram());
            yearComboBox.setSelectedItem(student.getYearLevel());

            if (student.getUser() != null) {
                userComboBox.setSelectedItem(
                        student.getUser().getUsername() + " (" + student.getUser().getFullName() + ")");
            }

            idField.setEditable(false);
        } else {

            idField.setText(String.valueOf(getNextStudentId()));
            idField.setEditable(false);
        }

        panel.add(idLabel);
        panel.add(idField);
        panel.add(studentIdLabel);
        panel.add(studentIdField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(programLabel);
        panel.add(programField);
        panel.add(yearLabel);
        panel.add(yearComboBox);
        panel.add(userLabel);
        panel.add(userComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.setBackground(accentColor);
        saveButton.setForeground(Color.WHITE);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String studentId = studentIdField.getText().trim();
                    String name = nameField.getText().trim();
                    String email = emailField.getText().trim();
                    String program = programField.getText().trim();
                    int yearLevel = (Integer) yearComboBox.getSelectedItem();
                    String selectedUser = (String) userComboBox.getSelectedItem();

                    User userAccount = null;
                    if (selectedUser != null && !selectedUser.isEmpty()) {
                        String username = selectedUser.split(" ")[0];
                        for (User user : users) {
                            if (user.getUsername().equals(username)) {
                                userAccount = user;
                                break;
                            }
                        }
                    }

                    if (studentId.isEmpty() || name.isEmpty() || email.isEmpty() || program.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog,
                                "Please fill in all required fields",
                                "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (isNewStudent) {
                        Student newStudent = new Student(id, studentId, name, email, program, yearLevel, userAccount);
                        students.add(newStudent);
                    } else {
                        student.setStudentId(studentId);
                        student.setName(name);
                        student.setEmail(email);
                        student.setProgram(program);
                        student.setYearLevel(yearLevel);
                        student.setUser(userAccount);
                    }
                    refreshStudentTable();

                    JOptionPane.showMessageDialog(dialog,
                            "Student saved successfully",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Please enter valid numbers for numeric fields",
                            "Validation Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private void showStudentCoursesDialog(Student student) {
        JDialog dialog = new JDialog(this, "Courses for " + student.getName(), true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.addColumn("Course Code");
        tableModel.addColumn("Title");
        tableModel.addColumn("Credits");
        tableModel.addColumn("Status");
        tableModel.addColumn("Grade");

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().getId() == student.getId()) {
                tableModel.addRow(new Object[] {
                        enrollment.getCourse().getCourseCode(),
                        enrollment.getCourse().getTitle(),
                        enrollment.getCourse().getCredits(),
                        enrollment.getStatus(),
                        enrollment.getGrade()
                });
            }
        }

        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton enrollButton = createButton("Enroll in Course", new Color(46, 204, 113));
        JButton updateGradeButton = createButton("Update Grade", new Color(52, 152, 219));
        JButton closeButton = new JButton("Close");

        if ("Admin".equals(userRole) || "Teacher".equals(userRole)) {
            buttonPanel.add(enrollButton);
            buttonPanel.add(updateGradeButton);
        }

        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        enrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEnrollmentDialog(student);
                refreshCourseTable(tableModel, student);
            }
        });

        updateGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String courseCode = (String) table.getValueAt(selectedRow, 0);
                    for (Enrollment enrollment : enrollments) {
                        if (enrollment.getStudent().getId() == student.getId() &&
                                enrollment.getCourse().getCourseCode().equals(courseCode)) {
                            showGradeUpdateDialog(enrollment);
                            refreshCourseTable(tableModel, student);
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog,
                            "Please select a course",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private void refreshCourseTable(DefaultTableModel model, Student student) {
        model.setRowCount(0);

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().getId() == student.getId()) {
                model.addRow(new Object[] {
                        enrollment.getCourse().getCourseCode(),
                        enrollment.getCourse().getTitle(),
                        enrollment.getCourse().getCredits(),
                        enrollment.getStatus(),
                        enrollment.getGrade()
                });
            }
        }
    }

    private void showEnrollmentDialog(Student student) {
        JDialog dialog = new JDialog(this, "Enroll " + student.getName() + " in Course", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel courseLabel = new JLabel("Course:");
        JComboBox<String> courseComboBox = new JComboBox<>();

        for (Course course : courses) {
            boolean alreadyEnrolled = false;
            for (Enrollment enrollment : enrollments) {
                if (enrollment.getStudent().getId() == student.getId() &&
                        enrollment.getCourse().getId() == course.getId()) {
                    alreadyEnrolled = true;
                    break;
                }
            }

            if (!alreadyEnrolled) {
                courseComboBox.addItem(course.getCourseCode() + " - " + course.getTitle());
            }
        }

        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusComboBox = new JComboBox<>(
                new String[] { "Active", "Pending", "Completed", "Withdrawn" });

        panel.add(courseLabel);
        panel.add(courseComboBox);
        panel.add(statusLabel);
        panel.add(statusComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton enrollButton = new JButton("Enroll");
        JButton cancelButton = new JButton("Cancel");

        enrollButton.setBackground(accentColor);
        enrollButton.setForeground(Color.WHITE);

        buttonPanel.add(enrollButton);
        buttonPanel.add(cancelButton);

        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        enrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourseString = (String) courseComboBox.getSelectedItem();
                String status = (String) statusComboBox.getSelectedItem();

                if (selectedCourseString != null && !selectedCourseString.isEmpty()) {
                    String courseCode = selectedCourseString.split(" - ")[0];

                    Course selectedCourse = null;
                    for (Course course : courses) {
                        if (course.getCourseCode().equals(courseCode)) {
                            selectedCourse = course;
                            break;
                        }
                    }

                    if (selectedCourse != null) {
                        Enrollment newEnrollment = new Enrollment(
                                getNextEnrollmentId(),
                                student,
                                selectedCourse,
                                new java.util.Date(),
                                status);

                        enrollments.add(newEnrollment);

                        JOptionPane.showMessageDialog(dialog,
                                "Student enrolled successfully",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog,
                            "Please select a course",
                            "Validation Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private void showGradeUpdateDialog(Enrollment enrollment) {
        JDialog dialog = new JDialog(this, "Update Grade", true);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel gradeLabel = new JLabel("Grade:");
        JComboBox<String> gradeComboBox = new JComboBox<>(
                new String[] { "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F", "Not Graded" });

        gradeComboBox.setSelectedItem(enrollment.getGrade());

        panel.add(gradeLabel);
        panel.add(gradeComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");

        updateButton.setBackground(accentColor);
        updateButton.setForeground(Color.WHITE);

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String grade = (String) gradeComboBox.getSelectedItem();
                enrollment.setGrade(grade);

                JOptionPane.showMessageDialog(dialog,
                        "Grade updated successfully",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private int getNextStudentId() {
        int maxId = 0;
        for (Student student : students) {
            if (student.getId() > maxId) {
                maxId = student.getId();
            }
        }
        return maxId + 1;
    }

    private void refreshStudentTable() {
        JTabbedPane tabbedPane = (JTabbedPane) ((JPanel) getContentPane().getComponent(1)).getComponent(0);
        int studentTabIndex = -1;

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if ("Students".equals(tabbedPane.getTitleAt(i))) {
                studentTabIndex = i;
                break;
            }
        }

        if (studentTabIndex >= 0) {
            JPanel studentPanel = (JPanel) tabbedPane.getComponentAt(studentTabIndex);
            JScrollPane scrollPane = (JScrollPane) studentPanel.getComponent(0);
            JTable table = (JTable) scrollPane.getViewport().getView();
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            refreshStudentTable(model);
        }
    }

    private int getNextEnrollmentId() {
        int maxId = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() > maxId) {
                maxId = enrollment.getId();
            }
        }
        return maxId + 1;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 30));

        return button;
    }

    private JPanel createCoursePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.addColumn("ID");
        tableModel.addColumn("Course Code");
        tableModel.addColumn("Title");
        tableModel.addColumn("Description");
        tableModel.addColumn("Credits");

        for (Course course : courses) {
            tableModel.addRow(new Object[] {
                    course.getId(),
                    course.getCourseCode(),
                    course.getTitle(),
                    course.getDescription(),
                    course.getCredits()
            });
        }

        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        RoundedButton addButton = new RoundedButton();
        addButton.setText("Add");
        addButton.setFont(buttonFont); // استخدام buttonFont
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setPreferredSize(new Dimension(90, 40));

        RoundedButton editButton = new RoundedButton();
        editButton.setText("Edit");
        editButton.setFont(buttonFont);
        editButton.setBackground(new Color(52, 152, 219));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setBorderPainted(false);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editButton.setPreferredSize(new Dimension(90, 40));

        RoundedButton deleteButton = new RoundedButton();
        deleteButton.setText("Delete");
        deleteButton.setFont(buttonFont);
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.setPreferredSize(new Dimension(90, 40));

        RoundedButton refreshButton = new RoundedButton();
        refreshButton.setText("Refresh");
        refreshButton.setFont(buttonFont);
        refreshButton.setBackground(new Color(155, 89, 182));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.setPreferredSize(new Dimension(90, 40));

        RoundedButton viewStudentsButton = new RoundedButton();
        viewStudentsButton.setText("View Students");
        viewStudentsButton.setFont(buttonFont);
        viewStudentsButton.setBackground(new Color(241, 196, 15));
        viewStudentsButton.setForeground(Color.WHITE);
        viewStudentsButton.setFocusPainted(false);
        viewStudentsButton.setBorderPainted(false);
        viewStudentsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewStudentsButton.setPreferredSize(new Dimension(140, 40));

        RoundedButton viewCoursesButton = new RoundedButton();
        viewCoursesButton.setText("View Courses");
        viewCoursesButton.setFont(buttonFont);
        viewCoursesButton.setBackground(new Color(241, 196, 15));
        viewCoursesButton.setForeground(Color.WHITE);
        viewCoursesButton.setFocusPainted(false);
        viewCoursesButton.setBorderPainted(false);
        viewCoursesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewCoursesButton.setPreferredSize(new Dimension(140, 40));

        if ("Admin".equals(userRole)) {
            buttonPanel.add(addButton);
            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);
        }

        buttonPanel.add(viewStudentsButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCourseDialog(null);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int courseId = (Integer) table.getValueAt(selectedRow, 0);
                    Course courseToEdit = null;

                    for (Course course : courses) {
                        if (course.getId() == courseId) {
                            courseToEdit = course;
                            break;
                        }
                    }

                    if (courseToEdit != null) {
                        showCourseDialog(courseToEdit);
                    }
                } else {
                    JOptionPane.showMessageDialog(MainSystemFrame.this,
                            "Please select a course to edit",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int courseId = (Integer) table.getValueAt(selectedRow, 0);

                    int result = JOptionPane.showConfirmDialog(MainSystemFrame.this,
                            "Are you sure you want to delete this course?",
                            "Confirm Delete", JOptionPane.YES_NO_OPTION);

                    if (result == JOptionPane.YES_OPTION) {
                        Course courseToDelete = null;
                        for (Course course : courses) {
                            if (course.getId() == courseId) {
                                courseToDelete = course;
                                break;
                            }
                        }

                        if (courseToDelete != null) {
                            courses.remove(courseToDelete);
                            tableModel.removeRow(selectedRow);

                            List<Enrollment> enrollmentsToRemove = new ArrayList<>();
                            for (Enrollment enrollment : enrollments) {
                                if (enrollment.getCourse().getId() == courseId) {
                                    enrollmentsToRemove.add(enrollment);
                                }
                            }
                            enrollments.removeAll(enrollmentsToRemove);

                            JOptionPane.showMessageDialog(MainSystemFrame.this,
                                    "Course deleted successfully",
                                    "Delete Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(MainSystemFrame.this,
                            "Please select a course to delete",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        viewStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int courseId = (Integer) table.getValueAt(selectedRow, 0);
                    Course selectedCourse = null;

                    for (Course course : courses) {
                        if (course.getId() == courseId) {
                            selectedCourse = course;
                            break;
                        }
                    }

                    if (selectedCourse != null) {
                        showCourseStudentsDialog(selectedCourse);
                    }
                } else {
                    JOptionPane.showMessageDialog(MainSystemFrame.this,
                            "Please select a course to view students",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshCourseTable(tableModel);
            }
        });

        return panel;
    }

    private void refreshCourseTable(DefaultTableModel model) {
        model.setRowCount(0);

        for (Course course : courses) {
            model.addRow(new Object[] {
                    course.getId(),
                    course.getCourseCode(),
                    course.getTitle(),
                    course.getDescription(),
                    course.getCredits()
            });
        }
    }

    private void showCourseDialog(Course course) {
        final boolean isNewCourse = (course == null);

        JDialog dialog = new JDialog(this, isNewCourse ? "Add Course" : "Edit Course", true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();

        JLabel codeLabel = new JLabel("Course Code:");
        JTextField codeField = new JTextField();

        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();

        JLabel descLabel = new JLabel("Description:");
        JTextField descField = new JTextField();

        JLabel creditsLabel = new JLabel("Credits:");
        JSpinner creditsSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 10, 1));

        if (!isNewCourse) {
            idField.setText(String.valueOf(course.getId()));
            codeField.setText(course.getCourseCode());
            titleField.setText(course.getTitle());
            descField.setText(course.getDescription());
            creditsSpinner.setValue(course.getCredits());

            idField.setEditable(false);
        } else {
            idField.setText(String.valueOf(getNextCourseId()));
            idField.setEditable(false);
        }

        panel.add(idLabel);
        panel.add(idField);
        panel.add(codeLabel);
        panel.add(codeField);
        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(descLabel);
        panel.add(descField);
        panel.add(creditsLabel);
        panel.add(creditsSpinner);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.setBackground(accentColor);
        saveButton.setForeground(Color.WHITE);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String code = codeField.getText().trim();
                    String title = titleField.getText().trim();
                    String description = descField.getText().trim();
                    int credits = (Integer) creditsSpinner.getValue();

                    if (code.isEmpty() || title.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog,
                                "Please fill in all required fields",
                                "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (isNewCourse) {

                        Course newCourse = new Course(id, code, title, description, credits);
                        courses.add(newCourse);
                    } else {

                        course.setCourseCode(code);
                        course.setTitle(title);
                        course.setDescription(description);
                        course.setCredits(credits);
                    }

                    refreshCourseTable();

                    JOptionPane.showMessageDialog(dialog,
                            "Course saved successfully",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Please enter valid numbers for numeric fields",
                            "Validation Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private void showCourseStudentsDialog(Course course) {
        JDialog dialog = new JDialog(this, "Students in " + course.getCourseCode() + ": " + course.getTitle(), true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.addColumn("Student ID");
        tableModel.addColumn("Student Name");
        tableModel.addColumn("Program");
        tableModel.addColumn("Year");
        tableModel.addColumn("Status");
        tableModel.addColumn("Grade");

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().getId() == course.getId()) {
                Student student = enrollment.getStudent();
                tableModel.addRow(new Object[] {
                        student.getStudentId(),
                        student.getName(),
                        student.getProgram(),
                        student.getYearLevel(),
                        enrollment.getStatus(),
                        enrollment.getGrade()
                });
            }
        }

        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton updateGradeButton = createButton("Update Grade", new Color(52, 152, 219));
        JButton closeButton = new JButton("Close");

        if ("Admin".equals(userRole) || "Teacher".equals(userRole)) {
            buttonPanel.add(updateGradeButton);
        }

        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        updateGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String studentId = (String) table.getValueAt(selectedRow, 0);

                    for (Enrollment enrollment : enrollments) {
                        if (enrollment.getCourse().getId() == course.getId() &&
                                enrollment.getStudent().getStudentId().equals(studentId)) {
                            showGradeUpdateDialog(enrollment);

                            tableModel.setValueAt(enrollment.getGrade(), selectedRow, 5);
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog,
                            "Please select a student",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private int getNextCourseId() {
        int maxId = 0;
        for (Course course : courses) {
            if (course.getId() > maxId) {
                maxId = course.getId();
            }
        }
        return maxId + 1;
    }

    private void refreshCourseTable() {

        JTabbedPane tabbedPane = (JTabbedPane) ((JPanel) getContentPane().getComponent(1)).getComponent(0);
        int courseTabIndex = -1;

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if ("Courses".equals(tabbedPane.getTitleAt(i))) {
                courseTabIndex = i;
                break;
            }
        }

        if (courseTabIndex >= 0) {
            JPanel coursePanel = (JPanel) tabbedPane.getComponentAt(courseTabIndex);
            JScrollPane scrollPane = (JScrollPane) coursePanel.getComponent(0);
            JTable table = (JTable) scrollPane.getViewport().getView();
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            refreshCourseTable(model);
        }
    }

    private JPanel createEnrollmentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Add columns
        tableModel.addColumn("ID");
        tableModel.addColumn("Student");
        tableModel.addColumn("Course");
        tableModel.addColumn("Date");
        tableModel.addColumn("Status");
        tableModel.addColumn("Grade");

        // Add rows from sample data
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        for (Enrollment enrollment : enrollments) {
            tableModel.addRow(new Object[] {
                    enrollment.getId(),
                    enrollment.getStudent().getName() + " (" + enrollment.getStudent().getStudentId() + ")",
                    enrollment.getCourse().getCourseCode() + ": " + enrollment.getCourse().getTitle(),
                    dateFormat.format(enrollment.getEnrollmentDate()),
                    enrollment.getStatus(),
                    enrollment.getGrade()
            });
        }

        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        RoundedButton addButton = new RoundedButton();
        addButton.setText("Add Enrollment");
        addButton.setFont(buttonFont);
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.white);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setPreferredSize(new Dimension(140, 40));

        RoundedButton updateStatusButton = new RoundedButton();
        updateStatusButton.setText("Update Status");
        updateStatusButton.setFont(buttonFont);
        updateStatusButton.setBackground(new Color(52, 152, 219));
        updateStatusButton.setForeground(Color.white);
        updateStatusButton.setFocusPainted(false);
        updateStatusButton.setBorderPainted(false);
        updateStatusButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updateStatusButton.setPreferredSize(new Dimension(140, 40));

        RoundedButton updateGradeButton = new RoundedButton();
        updateGradeButton.setText("Update Grade");
        updateGradeButton.setFont(buttonFont);
        updateGradeButton.setBackground(new Color(241, 196, 15));
        updateGradeButton.setForeground(Color.white);
        updateGradeButton.setFocusPainted(false);
        updateGradeButton.setBorderPainted(false);
        updateGradeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updateGradeButton.setPreferredSize(new Dimension(140, 40));

        RoundedButton deleteButton = new RoundedButton();
        deleteButton.setText("Delete");
        deleteButton.setFont(buttonFont);
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.white);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.setPreferredSize(new Dimension(90, 40));

        RoundedButton refreshButton = new RoundedButton();
        refreshButton.setText("Refresh");
        refreshButton.setFont(buttonFont);
        refreshButton.setBackground(new Color(155, 89, 182));
        refreshButton.setForeground(Color.white);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.setPreferredSize(new Dimension(90, 40));

        if ("Admin".equals(userRole)) {
            buttonPanel.add(addButton);
            buttonPanel.add(deleteButton);
        }

        if ("Admin".equals(userRole) || "Teacher".equals(userRole)) {
            buttonPanel.add(updateStatusButton);
            buttonPanel.add(updateGradeButton);
        }

        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddEnrollmentDialog();
            }
        });

        updateStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int enrollmentId = (Integer) table.getValueAt(selectedRow, 0);

                    Enrollment enrollmentToUpdate = null;
                    for (Enrollment enrollment : enrollments) {
                        if (enrollment.getId() == enrollmentId) {
                            enrollmentToUpdate = enrollment;
                            break;
                        }
                    }

                    if (enrollmentToUpdate != null) {
                        showEnrollmentStatusDialog(enrollmentToUpdate);

                        tableModel.setValueAt(enrollmentToUpdate.getStatus(), selectedRow, 4);
                    }
                } else {
                    JOptionPane.showMessageDialog(MainSystemFrame.this,
                            "Please select an enrollment to update",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        updateGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int enrollmentId = (Integer) table.getValueAt(selectedRow, 0);

                    Enrollment enrollmentToUpdate = null;
                    for (Enrollment enrollment : enrollments) {
                        if (enrollment.getId() == enrollmentId) {
                            enrollmentToUpdate = enrollment;
                            break;
                        }
                    }

                    if (enrollmentToUpdate != null) {
                        showGradeUpdateDialog(enrollmentToUpdate);

                        tableModel.setValueAt(enrollmentToUpdate.getGrade(), selectedRow, 5);
                    }
                } else {
                    JOptionPane.showMessageDialog(MainSystemFrame.this,
                            "Please select an enrollment to update",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int enrollmentId = (Integer) table.getValueAt(selectedRow, 0);

                    int result = JOptionPane.showConfirmDialog(MainSystemFrame.this,
                            "Are you sure you want to delete this enrollment?",
                            "Confirm Delete", JOptionPane.YES_NO_OPTION);

                    if (result == JOptionPane.YES_OPTION) {
                        Enrollment enrollmentToDelete = null;
                        for (Enrollment enrollment : enrollments) {
                            if (enrollment.getId() == enrollmentId) {
                                enrollmentToDelete = enrollment;
                                break;
                            }
                        }

                        if (enrollmentToDelete != null) {
                            enrollments.remove(enrollmentToDelete);
                            tableModel.removeRow(selectedRow);

                            JOptionPane.showMessageDialog(MainSystemFrame.this,
                                    "Enrollment deleted successfully",
                                    "Delete Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(MainSystemFrame.this,
                            "Please select an enrollment to delete",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshEnrollmentTable(tableModel);
            }
        });

        return panel;
    }

    private void refreshEnrollmentTable(DefaultTableModel model) {
        model.setRowCount(0);

        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        for (Enrollment enrollment : enrollments) {
            model.addRow(new Object[] {
                    enrollment.getId(),
                    enrollment.getStudent().getName() + " (" + enrollment.getStudent().getStudentId() + ")",
                    enrollment.getCourse().getCourseCode() + ": " + enrollment.getCourse().getTitle(),
                    dateFormat.format(enrollment.getEnrollmentDate()),
                    enrollment.getStatus(),
                    enrollment.getGrade()
            });
        }
    }

    private void showAddEnrollmentDialog() {
        JDialog dialog = new JDialog(this, "Add New Enrollment", true);
        dialog.setSize(450, 250);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel studentLabel = new JLabel("Student:");
        JComboBox<String> studentComboBox = new JComboBox<>();

        for (Student student : students) {
            studentComboBox.addItem(student.getName() + " (" + student.getStudentId() + ")");
        }

        JLabel courseLabel = new JLabel("Course:");
        JComboBox<String> courseComboBox = new JComboBox<>();

        for (Course course : courses) {
            courseComboBox.addItem(course.getCourseCode() + ": " + course.getTitle());
        }

        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusComboBox = new JComboBox<>(
                new String[] { "Active", "Pending", "Completed", "Withdrawn" });

        JLabel dateLabel = new JLabel("Date:");
        JTextField dateField = new JTextField(
                new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));

        panel.add(studentLabel);
        panel.add(studentComboBox);
        panel.add(courseLabel);
        panel.add(courseComboBox);
        panel.add(statusLabel);
        panel.add(statusComboBox);
        panel.add(dateLabel);
        panel.add(dateField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.setBackground(accentColor);
        saveButton.setForeground(Color.WHITE);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedStudentString = (String) studentComboBox.getSelectedItem();
                    String selectedCourseString = (String) courseComboBox.getSelectedItem();
                    String status = (String) statusComboBox.getSelectedItem();
                    String dateString = dateField.getText().trim();

                    if (selectedStudentString == null || selectedCourseString == null || dateString.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog,
                                "Please fill in all required fields",
                                "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date enrollmentDate = dateFormat.parse(dateString);

                    String studentId = selectedStudentString.substring(
                            selectedStudentString.lastIndexOf("(") + 1,
                            selectedStudentString.lastIndexOf(")"));

                    Student selectedStudent = null;
                    for (Student student : students) {
                        if (student.getStudentId().equals(studentId)) {
                            selectedStudent = student;
                            break;
                        }
                    }

                    String courseCode = selectedCourseString.split(":")[0].trim();
                    Course selectedCourse = null;
                    for (Course course : courses) {
                        if (course.getCourseCode().equals(courseCode)) {
                            selectedCourse = course;
                            break;
                        }
                    }

                    for (Enrollment enrollment : enrollments) {
                        if (enrollment.getStudent().getId() == selectedStudent.getId() &&
                                enrollment.getCourse().getId() == selectedCourse.getId()) {
                            JOptionPane.showMessageDialog(dialog,
                                    "This student is already enrolled in this course",
                                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    Enrollment newEnrollment = new Enrollment(
                            getNextEnrollmentId(),
                            selectedStudent,
                            selectedCourse,
                            enrollmentDate,
                            status);

                    enrollments.add(newEnrollment);

                    JTabbedPane tabbedPane = (JTabbedPane) ((JPanel) getContentPane().getComponent(1)).getComponent(0);
                    int enrollmentTabIndex = -1;

                    for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                        if ("Enrollments".equals(tabbedPane.getTitleAt(i))) {
                            enrollmentTabIndex = i;
                            break;
                        }
                    }

                    if (enrollmentTabIndex >= 0) {
                        JPanel enrollmentPanel = (JPanel) tabbedPane.getComponentAt(enrollmentTabIndex);
                        JScrollPane scrollPane = (JScrollPane) enrollmentPanel.getComponent(0);
                        JTable table = (JTable) scrollPane.getViewport().getView();
                        DefaultTableModel model = (DefaultTableModel) table.getModel();

                        refreshEnrollmentTable(model);
                    }

                    JOptionPane.showMessageDialog(dialog,
                            "Enrollment added successfully",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private void showEnrollmentStatusDialog(Enrollment enrollment) {
        JDialog dialog = new JDialog(this, "Update Enrollment Status", true);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusComboBox = new JComboBox<>(
                new String[] { "Active", "Pending", "Completed", "Withdrawn" });

        statusComboBox.setSelectedItem(enrollment.getStatus());

        panel.add(statusLabel);
        panel.add(statusComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");

        updateButton.setBackground(accentColor);
        updateButton.setForeground(Color.WHITE);

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String status = (String) statusComboBox.getSelectedItem();
                enrollment.setStatus(status);

                refreshEnrollmentTable();

                JOptionPane.showMessageDialog(dialog,
                        "Status updated successfully",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private JPanel createStudentProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Student currentStudent = null;
        for (Student student : students) {
            if (student.getUser() != null &&
                    student.getUser().getUsername().equals(currentUser)) {
                currentStudent = student;
                break;
            }
        }

        if (currentStudent == null) {

            JLabel notFoundLabel = new JLabel("No student profile found for this account", JLabel.CENTER);
            notFoundLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            panel.add(notFoundLabel, BorderLayout.CENTER);
            return panel;
        }

        JPanel profilePanel = new JPanel(new GridLayout(0, 2, 10, 15));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addProfileField(profilePanel, "Student ID:", currentStudent.getStudentId());
        addProfileField(profilePanel, "Name:", currentStudent.getName());
        addProfileField(profilePanel, "Email:", currentStudent.getEmail());
        addProfileField(profilePanel, "Program:", currentStudent.getProgram());
        addProfileField(profilePanel, "Year Level:", String.valueOf(currentStudent.getYearLevel()));

        panel.add(profilePanel, BorderLayout.NORTH);

        return panel;
    }

    private void addProfileField(JPanel panel, String label, String value) {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel fieldValue = new JLabel(value);
        fieldValue.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panel.add(fieldLabel);
        panel.add(fieldValue);
    }

    private JPanel createStudentCoursesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Student currentStudent = null;
        for (Student student : students) {
            if (student.getUser() != null &&
                    student.getUser().getUsername().equals(currentUser)) {
                currentStudent = student;
                break;
            }
        }

        if (currentStudent == null) {

            JLabel notFoundLabel = new JLabel("No student profile found for this account", JLabel.CENTER);
            notFoundLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            panel.add(notFoundLabel, BorderLayout.CENTER);
            return panel;
        }

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.addColumn("Course Code");
        tableModel.addColumn("Course Title");
        tableModel.addColumn("Credits");
        tableModel.addColumn("Status");

        final Student student = currentStudent;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().getId() == student.getId()) {
                tableModel.addRow(new Object[] {
                        enrollment.getCourse().getCourseCode(),
                        enrollment.getCourse().getTitle(),
                        enrollment.getCourse().getCredits(),
                        enrollment.getStatus()
                });
            }
        }

        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        int totalCredits = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().getId() == student.getId() &&
                    enrollment.getStatus().equals("Active")) {
                totalCredits += enrollment.getCourse().getCredits();
            }
        }

        JLabel creditsLabel = new JLabel("Total Credits: " + totalCredits);
        creditsLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        summaryPanel.add(creditsLabel);

        panel.add(summaryPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createStudentGradesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Find the current student
        Student currentStudent = null;
        for (Student student : students) {
            if (student.getUser() != null &&
                    student.getUser().getUsername().equals(currentUser)) {
                currentStudent = student;
                break;
            }
        }

        if (currentStudent == null) {
            // No student profile found
            JLabel notFoundLabel = new JLabel("No student profile found for this account", JLabel.CENTER);
            notFoundLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            panel.add(notFoundLabel, BorderLayout.CENTER);
            return panel;
        }

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Add columns
        tableModel.addColumn("Course Code");
        tableModel.addColumn("Course Title");
        tableModel.addColumn("Credits");
        tableModel.addColumn("Grade");

        final Student student = currentStudent;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().getId() == student.getId()) {
                tableModel.addRow(new Object[] {
                        enrollment.getCourse().getCourseCode(),
                        enrollment.getCourse().getTitle(),
                        enrollment.getCourse().getCredits(),
                        enrollment.getGrade()
                });
            }
        }

        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel summaryPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        double totalPoints = 0;
        int totalCredits = 0;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().getId() == student.getId()) {
                String grade = enrollment.getGrade();
                int credits = enrollment.getCourse().getCredits();

                // Only count if a grade has been assigned
                if (!grade.equals("Not Graded")) {
                    double points = 0;

                    // Convert letter grade to points
                    switch (grade) {
                        case "A":
                            points = 4.0;
                            break;
                        case "A-":
                            points = 3.7;
                            break;
                        case "B+":
                            points = 3.3;
                            break;
                        case "B":
                            points = 3.0;
                            break;
                        case "B-":
                            points = 2.7;
                            break;
                        case "C+":
                            points = 2.3;
                            break;
                        case "C":
                            points = 2.0;
                            break;
                        case "C-":
                            points = 1.7;
                            break;
                        case "D+":
                            points = 1.3;
                            break;
                        case "D":
                            points = 1.0;
                            break;
                        case "F":
                            points = 0.0;
                            break;
                    }

                    totalPoints += (points * credits);
                    totalCredits += credits;
                }
            }
        }

        double gpa = (totalCredits > 0) ? (totalPoints / totalCredits) : 0;

        int completedCourses = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().getId() == student.getId() &&
                    !enrollment.getGrade().equals("Not Graded")) {
                completedCourses++;
            }
        }

        JLabel gpaLabel = new JLabel("GPA: " + String.format("%.2f", gpa));
        gpaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel completedLabel = new JLabel("Completed Courses: " + completedCourses);
        completedLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        summaryPanel.add(gpaLabel);
        summaryPanel.add(completedLabel);

        panel.add(summaryPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshEnrollmentTable() {

        JTabbedPane tabbedPane = (JTabbedPane) ((JPanel) getContentPane().getComponent(1)).getComponent(0);
        int enrollmentTabIndex = -1;

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if ("Enrollments".equals(tabbedPane.getTitleAt(i))) {
                enrollmentTabIndex = i;
                break;
            }
        }

        if (enrollmentTabIndex >= 0) {
            JPanel enrollmentPanel = (JPanel) tabbedPane.getComponentAt(enrollmentTabIndex);
            JScrollPane scrollPane = (JScrollPane) enrollmentPanel.getComponent(0);
            JTable table = (JTable) scrollPane.getViewport().getView();
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            refreshEnrollmentTable(model);
        }
    }
}