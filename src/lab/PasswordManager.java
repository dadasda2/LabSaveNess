package lab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class PasswordManager implements ActionListener {
    private Map<String,UserStruct> loginPass;
    private LoginForm loginForm;

    public PasswordManager(LoginForm l)
    {
        loginPass = new HashMap<String, UserStruct>();
        loginForm = l;
    }

    public void setLoginPass(String login, String password){
            System.out.print("Trying to set loginPass..." + "\n");
            UserStruct u = new UserStruct();
            u.setUser(login, password, false, false, true, true);
            loginPass.put(login, u);
            System.out.println("Added via 2 param \n ");
    }

    public int checkPassword(String login, String password){
        if(loginPass.containsKey(login)) {
            return loginPass.get(login).password.compareTo(password);
        }else return 123;
    }

    public Map<String,UserStruct> getLoginPass(){ return loginPass;}

    public void setLoginPass(String fileName) throws IOException, ClassNotFoundException {
        try {
            FileReader fr = new FileReader("passwords.ser");
            fr.close();
            System.out.print("File is founded, opening... \n");
            FileInputStream fi = new FileInputStream("passwords.ser");
            ObjectInputStream in = new ObjectInputStream(fi);
            loginPass = (HashMap<String, UserStruct>) in.readObject();
            in.close();
            fi.close();
            System.out.print("set via file " + "\n");
        } catch (FileNotFoundException e) {
            System.out.print("File not found, creating default... \n");
            UserStruct admin = new UserStruct();
            admin.setUser("ADMIN", " ", true, false, true, true);
            loginPass.put(admin.login, admin);
            FileOutputStream fo = new FileOutputStream(fileName);
            ObjectOutputStream os = new ObjectOutputStream(fo);
            os.writeObject(loginPass);
            os.close();
            fo.close();
        }
    }

    public void saveLoginPass(String fileName) throws IOException {
        FileOutputStream fo = new FileOutputStream(fileName);
        ObjectOutputStream os = new ObjectOutputStream(fo);
        System.out.print("File is opened, saving..." + "\n");
        os.writeObject(loginPass);
        System.out.println("File is saved " + "\n");
        os.close();
        fo.close();
    }
    public void printAllUsers(){ loginPass.forEach((key,value)->value.print()); }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String login = loginForm.loginField.getText();
        String password = new String(loginForm.passwordField.getPassword());
        if (checkPassword(login, password) == 0) {
            if (!loginPass.get(login).isBlocked) {
                if (loginPass.get(login).isFirst) {
                    //toDo create change password form here
                    ChangePassWordDialog dialog = new ChangePassWordDialog();
                    dialog.pack();
                    dialog.setVisible(true);
                } else {
                    if (loginPass.get(login).isAdmin) {
                        //toDo create admin's form here
                    }
                }
            }
        }
    }
}

