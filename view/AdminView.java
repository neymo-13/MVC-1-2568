package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class AdminView extends JFrame {

	private JTable studentTable;
	private JTextField searchField;
	private JComboBox<String> filterBox;
	private JComboBox<String> sortBox;
	private JButton sortButton;

	private JComboBox<String> courseBox;
	private JTable gradeTable;
	private JButton saveGradeButton;

	public AdminView() {
		setTitle("Admin Dashboard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLayout(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane();

		JPanel studentPanel = createStudentPanel();
		tabbedPane.add("Student List", studentPanel);

		JPanel gradePanel = createGradePanel();
		tabbedPane.add("Grade Entry", gradePanel);

		add(tabbedPane, BorderLayout.CENTER);
	}

	private JPanel createStudentPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		JPanel topPanel = new JPanel(new FlowLayout());
		searchField = new JTextField(15);
		sortBox = new JComboBox<>(new String[] { "Name", "Age" });
		sortButton = new JButton("Sort");

		topPanel.add(new JLabel("Sort by:"));
		topPanel.add(sortBox);
		topPanel.add(sortButton);

		topPanel.add(new JLabel("Search:"));
		topPanel.add(searchField);
		topPanel.add(new JLabel("Filter:"));
		topPanel.add(filterBox);
		topPanel.add(sortButton);

		String[] columns = { "ID", "Name", "School" };
		Object[][] data = {};
		studentTable = new JTable(data, columns);

		panel.add(topPanel, BorderLayout.NORTH);
		panel.add(new JScrollPane(studentTable), BorderLayout.CENTER);

		return panel;
	}

	private JPanel createGradePanel() {
		JPanel panel = new JPanel(new BorderLayout());

		JPanel topPanel = new JPanel(new FlowLayout());
		courseBox = new JComboBox<>(new String[] { "Math", "Science", "English" });
		topPanel.add(new JLabel("Select Course:"));
		topPanel.add(courseBox);

		String[] columns = { "Student ID", "Student Name", "Grade" };
		Object[][] data = {};
		gradeTable = new JTable(data, columns);

		saveGradeButton = new JButton("Save Grades");

		panel.add(topPanel, BorderLayout.NORTH);
		panel.add(new JScrollPane(gradeTable), BorderLayout.CENTER);
		panel.add(saveGradeButton, BorderLayout.SOUTH);

		return panel;
	}

	public String getSearchText() {
		return searchField.getText();
	}

	public String getSelectedFilter() {
		return (String) filterBox.getSelectedItem();
	}

	public String getSelectedCourse() {
		return (String) courseBox.getSelectedItem();
	}

	public void addSearchListener(ActionListener listener) {
		searchField.addActionListener(listener);
	}

	public void addSortListener(ActionListener listener) {
		sortButton.addActionListener(listener);
	}

	public void addSaveGradeListener(ActionListener listener) {
		saveGradeButton.addActionListener(listener);
	}

	public void updateStudentTable(Object[][] data) {
		studentTable.setModel(new javax.swing.table.DefaultTableModel(
				data,
				new String[] { "ID", "Name", "School" }));
	}

	public void updateGradeTable(Object[][] data) {
		gradeTable.setModel(new javax.swing.table.DefaultTableModel(
				data,
				new String[] { "Student ID", "Student Name", "Grade" }));
	}

	public String getSelectedStudentId() {
		int selectedRow = studentTable.getSelectedRow();
		if (selectedRow >= 0) {
			return (String) studentTable.getValueAt(selectedRow, 0);
		}
		return null;
	}

	public void addRowClickListener(MouseAdapter listener) {
		studentTable.addMouseListener(listener);
	}
}
