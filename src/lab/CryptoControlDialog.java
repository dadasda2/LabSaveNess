package lab;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.management.Notification;
import javax.swing.*;
import javax.swing.text.Position;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class CryptoControlDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPasswordField cryptoField;
    private JTextArea enterKeyTextArea;

    public CryptoControlDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (IOException | IllegalBlockSizeException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | InvalidKeySpecException | ClassNotFoundException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() throws IOException, ClassNotFoundException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException, InterruptedException {
        PasswordManager pm = new PasswordManager();
        if (pm.deEnCryptFile("temp", "passwords.ser", new String(cryptoField.getPassword()), false)) {

            LoginDialog loginDialog = new LoginDialog();
            loginDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            loginDialog.setTitle("Login");
            loginDialog.setSize(500, 200);
            loginDialog.setLocationRelativeTo(null);
            loginDialog.setResizable(false);
            loginDialog.setVisible(true);
        }else {
            JOptionPane.showMessageDialog(null, "WRONG KEY", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
        }
        dispose();

    }
    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        CryptoControlDialog dialog = new CryptoControlDialog();
        dialog.pack();
        dialog.setVisible(true);
    }
}
