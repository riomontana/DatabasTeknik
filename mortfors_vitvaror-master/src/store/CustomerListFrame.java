package store;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.ApplianceTableModel;
import database.DBConnection;
import helpers.Window;

public class CustomerListFrame extends JFrame {
    private String[] columnNames = { "Kundnummer", "Namn", "Adress", "Epost", "Årsinköp" };
    private ApplianceTableModel applianceTableModel = new ApplianceTableModel(columnNames);
    private JTable table = new JTable(applianceTableModel);
    private JScrollPane spTable = new JScrollPane(table);
    private DBConnection dbConnection;
   

    public CustomerListFrame(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
        
        setPreferredSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());
        add(spTable, BorderLayout.CENTER);
       
        setVisible(false);
        pack();
        Window.center(this);
    }

    public void showWindow() {
        Object[][] data = dbConnection.getDatabaseStoreQuery().getAllCustomers();
        applianceTableModel.replaceData(data);
        setVisible(true);
    }
}
