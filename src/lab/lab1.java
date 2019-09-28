package lab;

import javax.swing.*;
import java.io.IOException;


public class lab1 {
    static public String userName;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        LoginDialog loginDialog = new LoginDialog();

        loginDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        loginDialog.setTitle("Login");
        loginDialog.setSize(500, 200);
        loginDialog.setLocationRelativeTo(null);
        loginDialog.setResizable(false);
        loginDialog.setVisible(true);

    }
}
