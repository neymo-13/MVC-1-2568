package view;

import model.Student;
import model.Subject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class GradeEntryView extends JFrame {

	private JComboBox<Subject> subjectComboBox;
	private JTable gradesTable;
	private DefaultTableModel tableModel;
	private JComboBox<String> gradeComboBox;
	private JButton saveGradeButton;

	private final java.util.Map<String, String> studentGrades = new java.util.HashMap<>();

	public GradeEntryView() {
		setTitle("Grade Entry System - Admin Panel");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(new Color(245, 245, 245));
		setLayout(new BorderLayout(10, 10));

		initComponents();
	}

	private void initComponents() {
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(new JLabel("Select Subject:"));
		subjectComboBox = new JComboBox<>();
		subjectComboBox.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index,
					boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof Subject) {
					Subject subject = (Subject) value;
					setText(subject.getSubjectId() + " - " + subject.getName());
				}
				return this;
			}
		});
		topPanel.add(subjectComboBox);

		String[] columns = { "Student ID", "Name", "School", "Grade" };
		tableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		gradesTable = new JTable(tableModel);
		gradesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(gradesTable);

		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.add(new JLabel("Select Grade:"));
		String[] validGrades = { "", "A", "B+", "B", "C+", "C", "D+", "D", "F" };
		gradeComboBox = new JComboBox<>(validGrades);
		bottomPanel.add(gradeComboBox);

		saveGradeButton = new JButton("Save Grade");
		bottomPanel.add(saveGradeButton);

		add(topPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	public void populateSubjectDropdown(List<Subject> subjects) {
		subjectComboBox.removeAllItems();
		for (Subject subject : subjects) {
			subjectComboBox.addItem(subject);
		}
	}

	public Subject getSelectedSubject() {
		return (Subject) subjectComboBox.getSelectedItem();
	}

	public JComboBox<Subject> getSubjectComboBox() {
		return subjectComboBox;
	}

	public JComboBox<String> getGradeComboBox() {
		return gradeComboBox;
	}

	public JButton getSaveGradeButton() {
		return saveGradeButton;
	}

	public JTable getGradesTable() {
		return gradesTable;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void displayEnrolledStudents(Map<Student, String> studentGradeMap) {
		tableModel.setRowCount(0);
		studentGrades.clear();

		for (Map.Entry<Student, String> entry : studentGradeMap.entrySet()) {
			Student s = entry.getKey();
			String grade = entry.getValue() != null ? entry.getValue() : " ";

			tableModel.addRow(new Object[] {
					s.getStudentId(),
					s.getPrefix() + " " + s.getFirstName() + " " + s.getLastName(),
					s.getCurrentSchool(),
					grade
			});
			studentGrades.put(s.getStudentId(), grade);
		}
	}

	public void updateStudentGrade(String studentId, String grade) {
		studentGrades.put(studentId, grade);
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			if (tableModel.getValueAt(i, 0).equals(studentId)) {
				tableModel.setValueAt(grade, i, 3);
				break;
			}
		}
	}

	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Notification", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showError(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
