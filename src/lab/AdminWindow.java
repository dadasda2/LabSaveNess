package lab;

import com.sun.source.tree.PackageTree;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AdminWindow {
    private JTable table1;
    protected JPanel panel1;
    private JButton changePasswordButton;
    private JButton addUserButton;
    private JButton saveChangesButton;
    private PasswordManager passwordManager;
    private UserStruct currentUser;

    AdminWindow(){
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserNameDialog userNameDialog = new UserNameDialog();
                userNameDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                userNameDialog.setLocationRelativeTo(null);
                userNameDialog.setSize(550, 200);
                userNameDialog.setTitle("Change password");
                userNameDialog.setVisible(true);
                Lock lock = new ReentrantLock();
                lock.lock();
                try {
                    passwordManager.setLoginPass(userNameDialog.getLogin(), " ");
                } finally {
                    lock.unlock();
                }
                setTable();
                try {
                    passwordManager.saveLoginPass("passwords.ser");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (int i = 0; i < table1.getRowCount(); i++) {
                    String login = (String) table1.getValueAt(i,0);
                    String  isBlocked = (String) table1.getValueAt(i,1);
                    String hasRestrictions = (String) table1.getValueAt(i, 2);
                    var map = passwordManager.getLoginPass();
                    var us = map.get(login);
                    us.isBlocked = Boolean.parseBoolean(isBlocked);
                    us.hasRestrictions = Boolean.parseBoolean(hasRestrictions);
                    passwordManager.setLoginPass(login,us);
                    try {
                        passwordManager.saveLoginPass("passwords.ser");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void setTable(){
        Object[] columnsHeader = new String[] {"Login", "Blocked", "Restrictions" };
        DefaultTableModel  tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsHeader);
        var map = passwordManager.getLoginPass();
        for (var value: map.values()) {
            ArrayList<String> array = new ArrayList<String>();
            array.add(value.login);
            array.add(String.valueOf(value.isBlocked));
            array.add(String.valueOf(value.hasRestrictions));
            tableModel.addRow(array.toArray());
        }

        table1.setModel(tableModel);
    }




    public void setPasswordManager(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }

    public void setCurrentUser(UserStruct currentUser) {
        this.currentUser = currentUser;
    }
}
