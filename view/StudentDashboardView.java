package view;

import model.RegisteredSubject;
import model.Student;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentDashboardView extends JFrame {

	private JLabel studentIdLabel;
	private JLabel studentNameLabel;
	private JLabel schoolLabel;
	private JTable gradesTable;
	private DefaultTableModel tableModel;
	private JButton registerButton;
	private JLabel ageLabel;
	private JLabel emailLabel;

	public StudentDashboardView() {
		setTitle("Student Dashboard - My Profile");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(3, 2, 5, 5)); 
		infoPanel.setBorder(new TitledBorder("Student Information"));

		infoPanel.add(new JLabel("Student ID:"));
		studentIdLabel = new JLabel("...");
		infoPanel.add(studentIdLabel);

		infoPanel.add(new JLabel("Full Name:"));
		studentNameLabel = new JLabel("...");
		infoPanel.add(studentNameLabel);

		infoPanel.add(new JLabel("Age:"));
		ageLabel = new JLabel("...");
		infoPanel.add(ageLabel);

		infoPanel.add(new JLabel("School:"));
		schoolLabel = new JLabel("...");
		infoPanel.add(schoolLabel);

		infoPanel.add(new JLabel("Email:"));
		emailLabel = new JLabel("...");
		infoPanel.add(emailLabel);

		add(infoPanel, BorderLayout.NORTH);

		String[] columnNames = { "Subject ID", "Grade" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; 
			}
		};
		gradesTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(gradesTable);
		scrollPane.setBorder(new TitledBorder("Registered Courses & Grades"));
		add(scrollPane, BorderLayout.CENTER);

		JPanel actionPanel = new JPanel();
		registerButton = new JButton("Register for New Courses");
		actionPanel.add(registerButton);
		add(actionPanel, BorderLayout.SOUTH);
	}

	public void displayStudentInfo(Student student) {
		if (student != null && student.getAge() >= 15) {
			studentIdLabel.setText(student.getStudentId());
			studentNameLabel.setText(student.getPrefix() + " " + student.getFirstName() + " " + student.getLastName());
			schoolLabel.setText(student.getCurrentSchool());
			ageLabel.setText(String.valueOf(student.getAge()));
        	emailLabel.setText(student.getEmail());
		}
	}

	public void displayGrades(List<RegisteredSubject> courses) {
		DefaultTableModel model = (DefaultTableModel) gradesTable.getModel();
		model.setRowCount(0);
		for (RegisteredSubject rc : courses) {
			String grade = rc.getGrade() != null ? rc.getGrade() : " "; 
			model.addRow(new Object[] {
					rc.getSubjectId(),
					grade
			});
		}
	}

	public void addRegisterButtonListener(ActionListener listener) {
		registerButton.addActionListener(listener);
	}

	public void setRegisterButtonVisible(boolean visible) {
		registerButton.setVisible(visible);
	}
}