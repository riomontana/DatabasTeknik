package customer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.ApplianceTableModel;
import database.DBConnection;
import helpers.Window;

public class CustomerPurchasesFrame extends JFrame {
    private String[] columnNames = { "Datum", "Typ", "Modell", "Tillverkare", "Antal", "Pris" };
    private ApplianceTableModel applianceTableModel = new ApplianceTableModel(columnNames);
    private JTable table = new JTable(applianceTableModel);
    private JScrollPane spTable = new JScrollPane(table);
    private DBConnection dbConnection;
    private MainController mainController;

    public CustomerPurchasesFrame(MainController mainController, DBConnection dbConnection) {
        this.mainController = mainController;
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
        Object[][] data = dbConnection.getDatabaseCustomerQuery().getCustomerPurchases(mainController.getCustomerID());
        
        applianceTableModel.replaceData(data);
        setVisible(true);
    }
}
