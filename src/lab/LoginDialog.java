package lab;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoginDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPasswordField passwordField;
    private JTextField enterLoginTextField;
    private JTextField enterPasswordTextField;
    private JTextField loginField;

    private PasswordManager passwordManager;

    public LoginDialog() throws IOException, ClassNotFoundException {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        passwordManager = new PasswordManager();
        passwordManager.setLoginPass("passwords.ser");

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onCancel();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    onCancel();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onCancel();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() throws IOException, ClassNotFoundException {
        UserStruct currentUser = passwordManager.getUser(loginField.getText());

        String login = loginField.getText();
        String password = new String(passwordField.getPassword());
        if (passwordManager.checkPassword(login, password) == 0) {
            if (!currentUser.isBlocked) {
                if (currentUser.isFirst) {
                    ChangePassWordDialog dialog = new ChangePassWordDialog(currentUser);
                    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    dialog.setLocationRelativeTo(null);
                    dialog.setSize(550, 200);
                    dialog.setTitle("Change password");
                    dialog.setVisible(true);
                    Lock lock = new ReentrantLock();
                    lock.lock();
                    try {
                        passwordManager.setLoginPass(login, dialog.getCurrentuser());
                    } finally {
                        lock.unlock();
                    }

                } if (currentUser.isAdmin) {
                    //toDo create admin's form here
                    dispose();
                }else{
                    passwordManager.saveLoginPass("passwords.ser");
                    MainWindowUser mainWindowUser = new MainWindowUser();
                    mainWindowUser.setPasswordManager(passwordManager);
                    mainWindowUser.setCurrentUser(currentUser);
                    JFrame mainWindowFrame= new JFrame("Main window");
                    mainWindowFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    mainWindowFrame.setContentPane(mainWindowUser.MainWindowUser);
                    mainWindowFrame.setSize(400, 200);
                    mainWindowFrame.setLocationRelativeTo(null);
                    mainWindowFrame.setResizable(false);
                    mainWindowFrame.setVisible(true);
                    dispose();
                }
                }
            }
        }



    private void onCancel() throws IOException {
        passwordManager.saveLoginPass("passwords.ser");
        dispose();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        LoginDialog dialog = new LoginDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
