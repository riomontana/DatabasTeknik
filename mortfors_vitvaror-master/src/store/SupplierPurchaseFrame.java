package store;

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
import database.DatabaseStoreQuery;
import helpers.Window;

public class SupplierPurchaseFrame extends JFrame {

    private DatabaseStoreQuery databaseStoreQuery;
    private JPanel pnlMain = new JPanel();
    private JPanel pnlPrice = new JPanel();
    private JLabel lblProductTitle = new JLabel("Product name");
    private JLabel lblQuantityTitle = new JLabel("Quantity");
    private JLabel lblUnitPriceTitle = new JLabel("Unit price", SwingConstants.RIGHT);
    private JLabel lblProduct = new JLabel("Product");
    private JTextField tfQuantity = new JTextField("0");
    private JTextField tfUnitPrice = new JTextField("10", SwingConstants.RIGHT);
    private JLabel lblTotalPrice = new JLabel("Total price: 10.0", SwingConstants.RIGHT);
    private JButton btnPurchase = new JButton("Köp");
    private JSeparator sPrice = new JSeparator(JSeparator.HORIZONTAL);

    private int supplierId;
    private int productId;
    private String type;
    private String model;
    private String manufacturer;
    private double price;
    private int stock;

    public SupplierPurchaseFrame(DatabaseStoreQuery databaseStoreQuery, int supplierId, int productId) {
        this.databaseStoreQuery = databaseStoreQuery;
        this.supplierId = supplierId;
        this.productId=productId;

        pnlMain.setLayout(new GridLayout(2, 3));
        pnlMain.setBorder(new EmptyBorder(10, 10, 10, 10));

        pnlPrice.setLayout(new GridLayout(2, 1));
        pnlPrice.setBorder(new EmptyBorder(10, 10, 10, 10));

        btnPurchase.addActionListener(new PurchaseListener());

        KeyListener keyListener = new QuantityKeyListener();
        tfQuantity.addKeyListener(keyListener);
        tfUnitPrice.addKeyListener(keyListener);
        
        
        pnlMain.add(lblProductTitle);
        pnlMain.add(lblQuantityTitle);
        pnlMain.add(lblUnitPriceTitle);
        pnlMain.add(lblProduct);
        pnlMain.add(tfQuantity);
        pnlMain.add(tfUnitPrice);

        pnlPrice.add(sPrice);
        pnlPrice.add(lblTotalPrice);

        setLayout(new BorderLayout());
        add(pnlMain, BorderLayout.NORTH);
        add(pnlPrice, BorderLayout.CENTER);
        add(btnPurchase, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
        setMinimumSize(new Dimension(400, 150));
        pack();
        Window.center(this);
    }

    public void show(Object[] values) {

        price = Double.parseDouble(String.valueOf(values[4]));

        double totalPrice = price * stock;

        tfUnitPrice.setText(String.valueOf(price));

        lblTotalPrice.setText("Total price: " + totalPrice);


        setVisible(true);
    }

    private void handlePurchase() {

        int quantity = Integer.parseInt(tfQuantity.getText());

            double totalPrice = price * quantity;


            boolean res = databaseStoreQuery.createSupplierTransaction(productId, Integer.parseInt(tfQuantity.getText()), Double.parseDouble(tfUnitPrice.getText()));
             if(res) {
             JOptionPane.showMessageDialog(null, "Du har nu köpt " + quantity
             + " för " + totalPrice);
             }
             else {
             JOptionPane.showMessageDialog(null, "Köpet kunde inte genomföras");
             }
            
             hide();
        }
//    }

    private void updateTotalPrice() {
        try {
            int q = 0;
            price = Double.parseDouble(tfUnitPrice.getText());
            if (tfQuantity.getText().length() > 0) {
                q = Integer.parseInt(tfQuantity.getText());
            }
                double totPrice = q * price;
                lblTotalPrice.setText("Total price: " + String.valueOf(totPrice));
//            }
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
