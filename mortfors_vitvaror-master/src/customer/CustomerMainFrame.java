package customer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import common.MainFrame;
import common.TablePanel;

public class CustomerMainFrame extends MainFrame {
    private JButton btnShowCustomerPurchases; 
    private CustomerPurchasesFrame customerPurchasesFrame;
    
    public CustomerMainFrame(CustomerPurchasesFrame customerPurchasesFrame, TablePanel tablePanel, boolean visible) {
        super(tablePanel, visible);
        
        this.customerPurchasesFrame = customerPurchasesFrame;
        
        btnShowCustomerPurchases = new JButton("Show my previous purchases");
        btnShowCustomerPurchases.addActionListener(new ShowCustomerPurchasesListener());
        
        add(btnShowCustomerPurchases, BorderLayout.SOUTH);
    }
    
    private class ShowCustomerPurchasesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == btnShowCustomerPurchases) {
                customerPurchasesFrame.showWindow();
            }
        }
        
    }
}
