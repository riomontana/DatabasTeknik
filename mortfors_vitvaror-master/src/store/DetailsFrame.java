package store;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import database.DBConnection;
import database.DatabaseStoreQuery;

public class DetailsFrame extends JFrame {
    private Object[][] supplier = new Object[6][6];
    private Object[][] transaction = new Object[6][6];
    private String[] supplierColumnName = { "Id", "Vendor", "Telefon", "Websida", "Senaste pris" };
    private String[] transactionColumnName = { "Kundnummer", "Kund", "Model", "Transaktion", "Försäljningspris" };
    private DetailsPanel supplierPanel;
    private DetailsPanel transactionPanel;
    private DBConnection dbConnection;
    private DatabaseStoreQuery databaseStoreQuery;
    private int productId;

    public DetailsFrame(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.databaseStoreQuery = dbConnection.getDatabaseStoreQuery();

        supplierPanel = new DetailsPanel(supplierColumnName, supplier, this);
        transactionPanel = new DetailsPanel(transactionColumnName, transaction, this);

        setPreferredSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocation(400, 200);

        setLayout(new BorderLayout());
        add(transactionPanel, BorderLayout.CENTER);
        add(supplierPanel, BorderLayout.NORTH);
        setVisible(false);
        pack();
    }

    public void showWindow(int id) {
        this.productId = id;
        populateWindow(id);
        setVisible(true);
    }

    private void populateWindow(int id) {
        supplier = databaseStoreQuery.getSuppliers(id);
        supplierPanel.replaceData(supplier);

        transaction = databaseStoreQuery.getTransactions(id);
        transactionPanel.replaceData(transaction);
    }


    public void supplierPurchaseFrame(int supplierId) {
        SupplierPurchaseFrame spf = new SupplierPurchaseFrame(databaseStoreQuery, supplierId, productId);
        Object[] rowInfo = new Object[5];
        for (int row = 0; row < supplier.length; row++) {
            if (supplier[row][0].equals((Object) supplierId)) {
                for (int x = 0; x < rowInfo.length; x++) {
                    rowInfo[x] = supplier[row][x];
                }
            }
        }
        spf.show(rowInfo);
    }
}
