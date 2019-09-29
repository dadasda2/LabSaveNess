package lab;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainWindowUser {
    private JButton changePasswordButton;
    private JButton exitButton;
    protected JPanel MainWindowUser;
    private PasswordManager passwordManager;
    private UserStruct currentUser;

    void setPasswordManager(PasswordManager p){
        passwordManager = p;
    }

    void setCurrentUser(UserStruct u){
        currentUser = u;
    }

    MainWindowUser() {
        changePasswordButton.addActionListener(e -> {
            ChangePassWordDialog dialog = new ChangePassWordDialog(currentUser);
            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
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
                passwordManager.saveLoginPass("temp");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    passwordManager.saveLoginPass("temp");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    passwordManager.deEnCryptFile("temp","passwords.ser","glrz123",true);
                } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchPaddingException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });
    }
}