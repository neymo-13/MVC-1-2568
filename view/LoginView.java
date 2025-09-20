package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;

	public LoginView() {
		setTitle("Login System");
		setSize(350, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);

		JLabel userLabel = new JLabel("Username:");
		userLabel.setBounds(30, 30, 80, 25);
		add(userLabel);

		usernameField = new JTextField();
		usernameField.setBounds(120, 30, 165, 25);
		add(usernameField);

		JLabel passLabel = new JLabel("Password:");
		passLabel.setBounds(30, 70, 80, 25);
		add(passLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(120, 70, 165, 25);
		add(passwordField);

		loginButton = new JButton("Login");
		loginButton.setBounds(120, 110, 80, 25);
		add(loginButton);
	}

	public String getUsername() {
		return usernameField.getText();
	}

	public String getPassword() {
		return new String(passwordField.getPassword());
	}

	public void addLoginListener(ActionListener listener) {
		loginButton.addActionListener(listener);
	}

	public void displayError(String message) {
		JOptionPane.showMessageDialog(this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
	}
}