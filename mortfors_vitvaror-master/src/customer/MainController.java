package customer;

import common.TablePanel;
import database.DBConnection;

public class MainController {
    private CustomerMainFrame customerMainFrame;
    private TablePanel tablePanel;
    private DBConnection dbConnection;
    private long customerID = -1;
    
    public MainController() {
        dbConnection = new DBConnection();
        
        CustomerPurchasesFrame customerPurchasesFrame = new CustomerPurchasesFrame(this, dbConnection);
        
        customerMainFrame = new CustomerMainFrame(customerPurchasesFrame, new CustomerTableModel(dbConnection, this), false);
        customerMainFrame.pack();
        
        tablePanel = customerMainFrame.getTablePanel();
        tablePanel.replaceData(dbConnection.getDatabaseCommonQuery().getProducts());

        CustomerSigninFrame csf = new CustomerSigninFrame(this);
    }
    
    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }
    
    public long getCustomerID() {
        return customerID;
    }
    
    public void showCustomerMainFrame() {
        customerMainFrame.setVisible(true);
    }

    public void addNewCustomer(long customerID, String name, String address, String email) {
        dbConnection.getDatabaseCustomerQuery().addNewCustomer(customerID, name, address, email);
    }
}
