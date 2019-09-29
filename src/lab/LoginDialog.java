package lab;

import javax.swing.*;
import java.awt.*;
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
    private int deathCounter;
    private PasswordManager passwordManager;

    public LoginDialog() throws IOException, ClassNotFoundException {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        passwordManager = new PasswordManager();
        passwordManager.setLoginPass("temp");
        deathCounter = 0;

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

    private void onOK() throws IOException, ClassNotFoundException {;
        UserStruct currentUser = passwordManager.getUser(loginField.getText());
        passwordManager.printAllUsers();
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());
        if (passwordManager.checkPassword(login, password) == 0) {
            enterLoginTextField.setText("Enter login");
            enterLoginTextField.setBorder(BorderFactory.createLineBorder(Color.black));
            enterPasswordTextField.setText("Enter password");
            enterPasswordTextField.setBorder(BorderFactory.createLineBorder(Color.red));
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
//                    passwordManager.saveLoginPass("passwords.ser");
                    AdminWindow adminWindow = new AdminWindow();
                    adminWindow.setCurrentUser(currentUser);
                    adminWindow.setPasswordManager(passwordManager);
                    adminWindow.setTable();
                    JFrame jFrame = new JFrame("Main window");

                    jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    jFrame.setContentPane(adminWindow.panel1);
                    jFrame.pack();
                    jFrame.setLocationRelativeTo(null);
                    jFrame.setVisible(true);


                    JMenuBar jMenuBar = new JMenuBar();
                    JMenuItem helpMenu = new JMenu("Help");
                    JMenuItem about = new JMenuItem("About");
                    about.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            JOptionPane.showMessageDialog(null, "Овчинников А.И. ИДБ-16-09 Наличие строчных, прописных букв и символов математических операций.", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                        }
                    });
                    helpMenu.add(about);
                    jMenuBar.add(helpMenu);
                    jFrame.setJMenuBar(jMenuBar);

                    dispose();
                }else{
                    passwordManager.saveLoginPass("temp");
                    MainWindowUser mainWindowUser = new MainWindowUser();
                    mainWindowUser.setPasswordManager(passwordManager);
                    mainWindowUser.setCurrentUser(currentUser);

                    JFrame mainWindowFrame= new JFrame("Main window");
                    mainWindowFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    mainWindowFrame.setContentPane(mainWindowUser.MainWindowUser);
                    mainWindowFrame.setSize(400, 200);
                    mainWindowFrame.setLocationRelativeTo(null);
                    mainWindowFrame.setResizable(false);

                    JMenuBar JMenuBar = new JMenuBar();
                    JMenuItem helpMenu = new JMenu("Help");
                    JMenuItem about = new JMenuItem("About");
                    about.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            JOptionPane.showMessageDialog(null, "Овчинников А.И. ИДБ-16-09 Наличие строчных, прописных букв и символов математических операций.", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                        }

                    });
                    helpMenu.add(about);
                    JMenuBar.add(helpMenu);
                    mainWindowFrame.setJMenuBar(JMenuBar);

                    mainWindowFrame.setVisible(true);
                    dispose();
                }
                }else{
                JOptionPane.showMessageDialog(null, "You are blocked", "InfoBox", JOptionPane.INFORMATION_MESSAGE);


            }
            }else{
            if(passwordManager.checkPassword(login, password) == 123){
                enterLoginTextField.setText("(Incorrect login)");
                enterLoginTextField.setBorder(BorderFactory.createLineBorder(Color.red));
            }else{
                enterPasswordTextField.setText("(Incorrect password)");
                enterPasswordTextField.setBorder(BorderFactory.createLineBorder(Color.red));

                deathCounter++;
                if(deathCounter == 3){
                    System.exit(0);
                }
            }
        }
        }



    private void onCancel() throws IOException {
        passwordManager.saveLoginPass("temp");
        dispose();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        LoginDialog dialog = new LoginDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
