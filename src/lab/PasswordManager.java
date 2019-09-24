package lab;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.Map;

public class PasswordManager {
    private Map<String,String> loginPass;

    public boolean checkPassword(String login, String password){
        return loginPass.get(login) == password;
    }

    private void setLoginPass(String login, String password){
            System.out.print("Trying to set loginPass..." + "\n");
            loginPass.put(login, password);
            System.out.println("set via 2 param " + loginPass + "\n");
    }

    public void setLoginPass(String fileName) throws IOException {
        try {
            JsonReader reader = new JsonReader(new FileReader(fileName));
            System.out.print("File is founded, opening..." + "\n");
            Gson g = new Gson();
            loginPass = g.fromJson(reader,Map.class);
            reader.close();
            System.out.print("set via file " + String.valueOf(loginPass) + "\n");
        }catch (FileNotFoundException e){
            System.out.print("File not found, creating default..." + "\n");
            FileWriter writer = new FileWriter(fileName);
            writer.write("{\"ADMIN\":\" \"}");
            writer.close();
            setLoginPass(fileName);
        }
    }

    public void saveLoginPass(String fileName) throws IOException {
        try {
            System.out.print("File is opened, saving..." + "\n");
            Gson g = new Gson();
            FileWriter fileWriter = new FileWriter(fileName);
            g.toJson(loginPass, fileWriter);
            System.out.println("File is saved " + loginPass + "\n");
            fileWriter.close();
        }
        catch (java.lang.NullPointerException e) {
            setLoginPass(fileName);
        }
    }

    public static void main(String[] args) throws IOException {
        PasswordManager p = new PasswordManager();
        p.setLoginPass("passwords.json");
        p.setLoginPass("aa","awgwad");
        p.setLoginPass("aa","awgwadd");
        p.saveLoginPass("passwords.json");
    }
    }

