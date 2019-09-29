package lab;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;

public class PasswordManager{
    private Map<String,UserStruct> loginPass;
    private String keyHash;

    public PasswordManager(){
        loginPass = new HashMap<String, UserStruct>();
        keyHash = "0fc70f3e46ee7f762354ee5d8f66b4e2";
    }

    public UserStruct getUser(String login){
        return loginPass.get(login);
    }

    public void setLoginPass(String login, String password){
            System.out.print("Trying to set loginPass..." + "\n");
            UserStruct u = new UserStruct();
            u.setUser(login, password, false, false, true, true);
            loginPass.put(login, u);
            System.out.println("Added via 2 param \n ");
    }

    public void setLoginPass(String login, UserStruct u){
        System.out.print("Trying to set loginPass... \n");
        loginPass.put(login, u);
        System.out.println("Added via user struct \n ");
    }

    public int checkPassword(String login, String password){
        if(loginPass.containsKey(login)) {
            return loginPass.get(login).password.compareTo(password);
        }else return 123;
    }

    public Map<String,UserStruct> getLoginPass(){ return loginPass;}



    public boolean deEnCryptFile(String noCryptoFile,String cryptoFile, String ikey, boolean deEn) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeySpecException, IOException {

        String md5Hex = DigestUtils.md5Hex(ikey);
        if (keyHash.compareTo(md5Hex) == 0) {
            if (deEn) {
                CryptoUtils.encrypt(md5Hex,new File(noCryptoFile),new File(cryptoFile));

                File del = new File(noCryptoFile);
                del.delete();
                return true;
            }else {
            try {

                FileInputStream fi = new FileInputStream(cryptoFile);
                fi.close();
                CryptoUtils.decrypt(md5Hex,new File(cryptoFile),new File(noCryptoFile));

            }catch (java.io.FileNotFoundException e){
                System.out.print("File not found, creating default... \n");
                UserStruct admin = new UserStruct();
                admin.setUser("ADMIN", " ", true, false, true, true);
                loginPass.put(admin.login, admin);
                FileOutputStream fo = new FileOutputStream("temp");
                ObjectOutputStream os = new ObjectOutputStream(fo);
                os.writeObject(loginPass);
                os.close();
                fo.close();

            }

                return true;
            }
        }
        return false;
    }




    public void setLoginPass(String fileName) throws IOException, ClassNotFoundException {
        try {
            FileReader fr = new FileReader(fileName);
            fr.close();
            System.out.print("File is founded, opening... \n");
            FileInputStream fi = new FileInputStream(fileName);
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

    public static void main(String[] args) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        PasswordManager p = new PasswordManager();
        p.deEnCryptFile("temp","passwords.ser", "glrz123", true);
        p.deEnCryptFile("temp","passwords.ser", "glrz123", false);
    }
}

