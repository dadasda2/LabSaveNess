package lab;

import javax.swing.*;

public class lab1 {

    public static void main(String[] args){
        CryptoControlDialog cryptoControlDialog = new CryptoControlDialog();
        cryptoControlDialog.pack();
        cryptoControlDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        cryptoControlDialog.setLocationRelativeTo(null);
        cryptoControlDialog.setVisible(true);
    }
}