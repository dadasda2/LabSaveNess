package lab;

import javax.swing.*;

public class lab1 {

    public static void main(String[] args) {
        LoginForm loginFrame = new LoginForm();

        JFrame loginForm = new JFrame("Login");

        loginForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginForm.setContentPane(loginFrame.Login);
        loginForm.setSize(400, 200);
        loginForm.setLocationRelativeTo(null);
        loginForm.setResizable(false);
        loginForm.setVisible(true);

        JMenuBar JMenuBar = new JMenuBar();
        JMenuItem newMenu = new JMenu("new");
        JMenuItem txtFileItem = new JMenuItem("Text file");
        newMenu.add(txtFileItem);
        JMenuBar.add(newMenu);
        loginForm.setJMenuBar(JMenuBar);

    }
}
