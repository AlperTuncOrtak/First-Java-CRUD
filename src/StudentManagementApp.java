import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentManagementApp {

    public static void main(String[] args) {

        JFrame window = new JFrame("Student Management System");
        window.setSize(800, 500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());

        // Student table
        String[] columns = {"ID", "Name", "Email"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        window.add(new JScrollPane(table), BorderLayout.CENTER);

        // Input fields
        JPanel panel = new JPanel(new GridLayout(2, 3, 10, 10));

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();

        JButton addButton = new JButton("Add Student");
        JButton updateButton = new JButton("Update Student");
        JButton deleteButton = new JButton("Delete Student");

        panel.add(new JLabel("Name:"));
        panel.add(new JLabel("Email:"));
        panel.add(new JLabel(""));

        panel.add(nameField);
        panel.add(emailField);
        panel.add(addButton);

        window.add(panel, BorderLayout.NORTH);

        // Bottom buttons
        JPanel buttonPanel = new JPanel();

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        window.add(buttonPanel, BorderLayout.SOUTH);

        // CREATE - Add student
        final int[] id = {1};

        addButton.addActionListener(e -> {

            String name = nameField.getText().trim();
            String email = emailField.getText().trim();

            if (name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(window,
                        "Please fill in all fields!");
                return;
            }

            if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                JOptionPane.showMessageDialog(window,
                        "Please enter a valid email address!");
                return;
            }

            model.addRow(new Object[]{id[0]++, name, email});

            nameField.setText("");
            emailField.setText("");
            table.clearSelection();

            System.out.println("Student added: " + name);
        });

        // READ - Select student
        table.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {

                int row = table.getSelectedRow();

                nameField.setText(model.getValueAt(row, 1).toString());
                emailField.setText(model.getValueAt(row, 2).toString());
            }
        });

        // UPDATE - Edit student
        updateButton.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(window,
                        "Please select a student!");
                return;
            }

            String name = nameField.getText().trim();
            String email = emailField.getText().trim();

            if (name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(window,
                        "Please fill in all fields!");
                return;
            }

            if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                JOptionPane.showMessageDialog(window,
                        "Please enter a valid email address!");
                return;
            }

            model.setValueAt(name, row, 1);
            model.setValueAt(email, row, 2);

            System.out.println("Student updated: " + name);

            nameField.setText("");
            emailField.setText("");
            table.clearSelection();
        });

        // DELETE - Remove student
        deleteButton.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(window,
                        "Please select a student!");
                return;
            }

            String name = model.getValueAt(row, 1).toString();

            model.removeRow(row);

            nameField.setText("");
            emailField.setText("");
            table.clearSelection();

            System.out.println("Student deleted: " + name);
        });

        window.setVisible(true);

        System.out.println("Student Management System started successfully!");
    }
}
