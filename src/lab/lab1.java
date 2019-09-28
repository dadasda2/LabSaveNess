package lab;

import javax.swing.*;
import java.io.IOException;


public class lab1 {
    static public String userName;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        LoginForm loginFrame = new LoginForm();

        JFrame loginForm = new JFrame("Login");

        loginForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginForm.setContentPane(loginFrame.Login);
        loginForm.setSize(400, 200);
        loginForm.setLocationRelativeTo(null);
        loginForm.setResizable(false);
        loginForm.setVisible(true);

//        JMenuBar JMenuBar = new JMenuBar();
//        JMenuItem newMenu = new JMenu("new");
//        JMenuItem txtFileItem = new JMenuItem("Text file");
//        newMenu.add(txtFileItem);
//        JMenuBar.add(newMenu);
//        loginForm.setJMenuBar(JMenuBar);

        PasswordManager passwordManager = new PasswordManager();
        passwordManager.addLoginForm(loginFrame);
        passwordManager.setLoginPass("passwords.ser");
        passwordManager.printAllUsers();
        CancelListener cancelListener = new CancelListener();
        loginFrame.signInButton.addActionListener(passwordManager);
        loginFrame.cancelButton.addActionListener(cancelListener);

    }
}
