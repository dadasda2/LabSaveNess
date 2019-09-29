package lab;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class ChangePassWordDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField1;
    private JPasswordField newPasswordField2;
    private JTextField enterOldPasswordTextField;
    private JTextField enterNewPasswordTextField;
    private JTextField enterNewPasswordAgainTextField;

    private UserStruct currentuser;


    public ChangePassWordDialog(UserStruct u) {
        currentuser = u;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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

    private void onOK() {
        var oldPassword  = new String(oldPasswordField.getPassword());
        var newPassword1 = new String(newPasswordField1.getPassword());
        var newPassword2 = new String(newPasswordField2.getPassword());
        if(currentuser.password.compareTo(oldPassword) == 0){
            enterOldPasswordTextField.setText("Enter old password");
            enterOldPasswordTextField.setBorder(BorderFactory.createLineBorder(Color.black));
            if(newPassword1.compareTo(newPassword2) == 0){
                enterNewPasswordTextField.setText("Enter new password");
                enterNewPasswordTextField.setBorder(BorderFactory.createLineBorder(Color.black));
                enterNewPasswordAgainTextField.setBorder(BorderFactory.createLineBorder(Color.black));
                if(currentuser.hasRestrictions){
                    boolean specSymb  = false;
                    boolean upperSymb = false;
                    boolean lowerSymb = false;

                    for (int i = 0;i<newPassword2.length();i++){
                        if((newPassword2.charAt(i) == '+') | (newPassword2.charAt(i) == '-')
                                | (newPassword2.charAt(i) == '*') | (newPassword2.charAt(i) == '/')){ specSymb = true; }
                        if(Character.isUpperCase(newPassword2.charAt(i))){ upperSymb = true; }
                        if(Character.isLowerCase(newPassword2.charAt(i))){ lowerSymb = true; }
                    }
                    if(specSymb&upperSymb&lowerSymb){
                        currentuser.password = newPassword2;
                        currentuser.isFirst = false;
                        dispose();
                    }else {
                        enterNewPasswordTextField.setText("(Password must contain Upper Case letter,");
                        enterNewPasswordAgainTextField.setText("Lower Case letter and arithmetic symbol)");
                        enterNewPasswordTextField.setBorder(BorderFactory.createLineBorder(Color.red));
                        enterNewPasswordAgainTextField.setBorder(BorderFactory.createLineBorder(Color.red));

                    }
                }else {
                    currentuser.password = newPassword2;
                    currentuser.isFirst = false;
                    dispose();
                }
            }else {
                enterNewPasswordTextField.setText("(New passwords does not match)");
                enterNewPasswordTextField.setBorder(BorderFactory.createLineBorder(Color.red));
            }
        }else{
            enterOldPasswordTextField.setText("(Incorrect old password)");
            enterOldPasswordTextField.setBorder(BorderFactory.createLineBorder(Color.red));
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public UserStruct getCurrentuser(){
        return currentuser;
    }
//    public static void main(String[] args) {
//        ChangePassWordDialog dialog = new ChangePassWordDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }

}
