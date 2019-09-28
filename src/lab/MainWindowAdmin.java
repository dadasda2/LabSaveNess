package lab;

import javax.swing.*;


public class MainWindowAdmin extends JFrame {
    private PasswordManager passwordManager;
    private UserStruct currentUser;
    private JTable userTable;
    private JButton changePasswordButton;
    private JButton addUserButton;

    public void setPasswordManager(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }


    public void setCurrentUser(UserStruct currentUser) {
        this.currentUser = currentUser;
    }

    MainWindowAdmin(){
        userTable = new JTable(3,5);
        changePasswordButton = new JButton();
        addUserButton = new JButton();
        Box contents = new Box(BoxLayout.Y_AXIS);
        Box h = new Box(BoxLayout.X_AXIS);
        h.add(changePasswordButton);
        h.add(addUserButton);
        contents.add(userTable);
        contents.add(h);
        setContentPane(contents);
    }


}
