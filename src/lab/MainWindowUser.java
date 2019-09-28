package lab;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainWindowUser {
    private JButton changePasswordButton;
    private JButton exitButton;
    protected JPanel MainWindowUser;
    private PasswordManager passwordManager;
    private UserStruct currentUser;

    public void setPasswordManager(PasswordManager p){
        passwordManager = p;
    }

    public void setCurrentUser(UserStruct u){
        currentUser = u;
    }

    MainWindowUser() {
        changePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ChangePassWordDialog dialog = new ChangePassWordDialog(currentUser);
                dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                dialog.setLocationRelativeTo(null);
                dialog.setSize(550, 200);
                dialog.setTitle("Change password");
                dialog.setVisible(true);
                Lock lock = new ReentrantLock();
                lock.lock();
                try {
                    passwordManager.setLoginPass(currentUser.login, dialog.getCurrentuser());
                } finally {
                    lock.unlock();
                }
                try {
                    passwordManager.saveLoginPass("passwords.ser");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    System.exit(0);
            }
        });
    }
}