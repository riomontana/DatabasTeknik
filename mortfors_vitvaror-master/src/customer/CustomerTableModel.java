package customer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import common.TablePanel;
import database.DBConnection;

public class CustomerTableModel extends TablePanel {
    private DBConnection dbConnection;
    private CustomerPurchaseFrame customerPurchaseFrame;

    public CustomerTableModel(DBConnection dbConnection, MainController mainController) {
        super(dbConnection);
        
        this.dbConnection = dbConnection;

        setMouseListener(new TableListener());
        
        customerPurchaseFrame = new CustomerPurchaseFrame(dbConnection, mainController);
    }

    private class TableListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            Object[] values= getSelectedRow(e.getPoint());
            
            customerPurchaseFrame.show(values);
        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }
    }
}
