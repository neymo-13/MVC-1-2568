package view;

import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.MouseAdapter;

public class AdminDashboardView extends JFrame {

	private JTable studentTable;
	private DefaultTableModel tableModel;
	private JButton filterButton;
	private JTextField filterTextField;

	private JButton gradeEntryButton;

	public AdminDashboardView() {
		setTitle("Admin Dashboard - Student Management");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JPanel filterPanel = new JPanel();
		JLabel filterLabel = new JLabel("Filter by School:");
		filterTextField = new JTextField(20);
		filterButton = new JButton("Filter");

		filterPanel.add(filterLabel);
		filterPanel.add(filterTextField);
		filterPanel.add(filterButton);
		add(filterPanel, BorderLayout.NORTH);

		String[] columnNames = { "Student ID", "Prefix", "First Name", "Last Name", "School", "Email" };

		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		studentTable = new JTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(studentTable);
		add(scrollPane, BorderLayout.CENTER);
		JPanel bottomPanel = new JPanel();
		gradeEntryButton = new JButton("Enter Grades");
		bottomPanel.add(gradeEntryButton);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	public void displayStudents(List<Student> students) {
		DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
		model.setRowCount(0);

		for (Student student : students) {
			Object[] row = {
					student.getStudentId(),
					student.getPrefix(),
					student.getFirstName(),
					student.getLastName(),
					student.getCurrentSchool(),
					student.getEmail()
			};
			tableModel.addRow(row);
		}
	}

	public String getFilterText() {
		return filterTextField.getText();
	}

	public void addFilterListener(ActionListener listener) {
		filterButton.addActionListener(listener);
	}

	public void addRowClickListener(MouseAdapter listener) {
		studentTable.addMouseListener(listener);
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public String getSelectedStudentId() {
		int selectedRow = studentTable.getSelectedRow();
		if (selectedRow >= 0) {
			return (String) studentTable.getValueAt(selectedRow, 0);
		}
		return null;
	}

	public void updateStudentTable(Object[][] data) {
		studentTable.setModel(new DefaultTableModel(
				data,
				new String[] { "ID", "Name", "School" }));
	}

	public void addGradeEntryButtonListener(ActionListener listener) {
		gradeEntryButton.addActionListener(listener);
	}

}