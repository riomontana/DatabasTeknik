package customer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import database.DBConnection;
import helpers.Window;

public class CustomerPurchaseFrame extends JFrame {
    private DBConnection dbConnection;

    private JPanel pnlMain = new JPanel();
    private JPanel pnlPrice = new JPanel();
    private JLabel lblProductTitle = new JLabel("Product name");
    private JLabel lblQuantityTitle = new JLabel("Quantity (in stock: 5)");
    private JLabel lblUnitPriceTitle = new JLabel("Unit price", SwingConstants.RIGHT);
    private JLabel lblProduct = new JLabel("Product");
    private JTextField tfQuantity = new JTextField("0");
    private JLabel lblUnitPrice = new JLabel("10", SwingConstants.RIGHT);
    private JLabel lblTotalPrice = new JLabel("Total price: 10.0", SwingConstants.RIGHT);
    private JButton btnPurchase = new JButton("Köp");
    private JSeparator sPrice = new JSeparator(JSeparator.HORIZONTAL);
    
    private MainController mainController;

    private int id;
    private String type;
    private String model;
    private String manufacturer;
    private double price;
    private int stock;

    public CustomerPurchaseFrame(DBConnection dbConnection, MainController mainController) {
        this.dbConnection = dbConnection;
        this.mainController = mainController;

        pnlMain.setLayout(new GridLayout(2, 3));
        pnlMain.setBorder(new EmptyBorder(10, 10, 10, 10));

        pnlPrice.setLayout(new GridLayout(2, 1));
        pnlPrice.setBorder(new EmptyBorder(10, 10, 10, 10));

        btnPurchase.addActionListener(new PurchaseListener());

        KeyListener keyListener = new QuantityKeyListener();
        tfQuantity.addKeyListener(keyListener);

        pnlMain.add(lblProductTitle);
        pnlMain.add(lblQuantityTitle);
        pnlMain.add(lblUnitPriceTitle);
        pnlMain.add(lblProduct);
        pnlMain.add(tfQuantity);
        pnlMain.add(lblUnitPrice);

        pnlPrice.add(sPrice);
        pnlPrice.add(lblTotalPrice);

        setLayout(new BorderLayout());
        add(pnlMain, BorderLayout.NORTH);
        add(pnlPrice, BorderLayout.CENTER);
        add(btnPurchase, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(false);
        setMinimumSize(new Dimension(400, 150));
        pack();
        Window.center(this);
    }

    public void show(Object[] values) {
        id = Integer.parseInt(String.valueOf(values[0]));
        type = String.valueOf(values[1]);
        model = String.valueOf(values[2]);
        manufacturer = String.valueOf(values[3]);
        price = Double.parseDouble(String.valueOf(values[4]));
        stock = Integer.parseInt(String.valueOf(values[5]));
        
        double totalPrice = price * stock;
        
        lblProduct.setText(manufacturer + " " + model);
        lblUnitPrice.setText(String.valueOf(price));
        lblQuantityTitle.setText("Quantity (in stock: " + stock + ")");
        lblTotalPrice.setText("Total price: " + totalPrice);
        tfQuantity.setText(String.valueOf(stock));

        setVisible(true);
    }

    private void handlePurchase() {
        int quantity = Integer.parseInt(tfQuantity.getText());
        
        if(quantity > stock) {
            JOptionPane.showMessageDialog(null, "Du kan inte köpa fler varor än vad som finns i lager.");
        }
        else {
            double totalPrice = price * quantity;
            long customerID = mainController.getCustomerID();
            
            boolean res = dbConnection.getDatabaseCustomerQuery().customerPurchase(id, customerID, quantity);
            
            if(res) {
                JOptionPane.showMessageDialog(null, "Du har nu köpt " + quantity + " " + manufacturer + " "
                        + " " + model + " för " + totalPrice);
            }
            else {
                JOptionPane.showMessageDialog(null, "Köpet kunde inte genomföras");
            }
            
            setVisible(false);
        }
    }

    private void updateTotalPrice() {
        try {
            int q = 0;

            if (tfQuantity.getText().length() > 0) {
                q = Integer.parseInt(tfQuantity.getText());
            }

            if (q > stock) {
                tfQuantity.setText(String.valueOf(stock));
                double totPrice = stock * price;
                lblTotalPrice.setText("Total price: " + String.valueOf(totPrice));
            } else {
                double totPrice = q * price;
                lblTotalPrice.setText("Total price: " + String.valueOf(totPrice));
            }
        } catch (NumberFormatException e) {
            tfQuantity.setText("1");
            lblTotalPrice.setText("Total price: " + String.valueOf(price));
        }
    }

    private class QuantityKeyListener implements KeyListener {
        public void keyPressed(KeyEvent keyEvent) {
        }

        public void keyReleased(KeyEvent keyEvent) {
            updateTotalPrice();
        }

        public void keyTyped(KeyEvent keyEvent) {
        }
    }

    private class PurchaseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnPurchase) {
                handlePurchase();
            }
        }

    }
}
