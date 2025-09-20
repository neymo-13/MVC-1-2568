package view;

import model.Subject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CourseRegistrationView extends JFrame {

	private JTable subjectTable;
	private DefaultTableModel tableModel;
	private JButton registerButton;
	private JButton backButton;

	public CourseRegistrationView() {
		setTitle("Course Registration");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout(10, 10));

		String[] columns = { "Subject ID", "Subject Name", "Credits", "Instructor" };
		tableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		subjectTable = new JTable(tableModel);
		subjectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(subjectTable);
		add(scrollPane, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		registerButton = new JButton("Register");
		backButton = new JButton("Back");
		bottomPanel.add(registerButton);
		bottomPanel.add(backButton);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	public void displayAvailableSubjects(List<Subject> subjects) {
		tableModel.setRowCount(0);
		for (Subject s : subjects) {
			tableModel.addRow(new Object[] {
					s.getSubjectId(),
					s.getName(),
					s.getCredits(),
					s.getInstructor()
			});
		}
	}

	public String getSelectedSubjectId() {
		int selectedRow = subjectTable.getSelectedRow();
		if (selectedRow == -1)
			return null;
		return (String) tableModel.getValueAt(selectedRow, 0);
	}

	public JButton getRegisterButton() {
		return registerButton;
	}

	public JButton getBackButton() {
		return backButton;
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showError(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
