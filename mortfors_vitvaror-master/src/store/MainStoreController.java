package store;

import common.MainFrame;
import common.TablePanel;
import database.DBConnection;

public class MainStoreController {
    private MainFrame mainFrame;
    private TablePanel tablePanel;
    private DBConnection dbConnection;
    private long customerID = -1;
    
    
    public MainStoreController() {
        dbConnection = new DBConnection();
        
        mainFrame = new MainFrame(new StoreViewTableModel(dbConnection, this), false);
        mainFrame.pack();
        
        tablePanel = mainFrame.getTablePanel();
     
        tablePanel.replaceData(dbConnection.getDatabaseCommonQuery().getProducts());
        tablePanel.addButtonSouth("Kundlista");
        showMainFrame();
    }
    
    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }
    
    public long getCustomerID() {
        return customerID;
    }
    
    public void showMainFrame() {
        mainFrame.setVisible(true);
    }
}
