package store;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import common.MainFrame;
import database.DBConnection;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    "javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        MainStoreController msc = new MainStoreController();
//        DBConnection dbConnection = new DBConnection();
//        StoreViewTableModel svtm = new StoreViewTableModel(dbConnection);
//        
//        MainFrame mainFrame = new MainFrame(svtm, true);
//        mainFrame.pack();
    }
}
