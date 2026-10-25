package gr.uni;

import gr.uni.model.Department;
import gr.uni.model.Student;
import gr.uni.model.TeachingStaff;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import java.awt.Font;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Fall back to the default Swing look and feel.
        }

        Department department = new Department("Computer Engineering");

        while (true) {
            String[] options = {
                    "Add Teaching Staff",
                    "Add Student",
                    "Print All",
                    "Exit"
            };

            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Select an action:",
                    "Department Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == JOptionPane.CLOSED_OPTION || choice == 3) {
                break;
            }

            switch (choice) {
                case 0 -> department.addTeachingStaffViaGui();
                case 1 -> department.addStudentViaGui();
                case 2 -> showAll(department);
            }
        }
    }

    private static void showAll(Department department) {
        StringBuilder sb = new StringBuilder();

        sb.append("=== Department: ")
                .append(department.getName())
                .append(" ===\n\n");

        sb.append("=== Teaching Staff ===\n");

        if (department.getAllStaff().isEmpty()) {
            sb.append("(none)\n");
        } else {
            for (TeachingStaff staff : department.getAllStaff()) {
                sb.append(staff).append("\n");
            }
        }

        sb.append("\n=== Students ===\n");

        if (department.getAllStudents().isEmpty()) {
            sb.append("(none)\n");
        } else {
            for (Student student : department.getAllStudents()) {
                sb.append(student).append("\n");
            }
        }

        JTextArea area = new JTextArea(sb.toString(), 20, 70);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Consolas", Font.PLAIN, 12));

        JScrollPane pane = new JScrollPane(area);

        JOptionPane.showMessageDialog(
                null,
                pane,
                "Print All",
                JOptionPane.INFORMATION_MESSAGE
        );

        System.out.println(sb);
    }
}