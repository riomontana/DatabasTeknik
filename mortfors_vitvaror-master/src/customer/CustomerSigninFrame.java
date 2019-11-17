package customer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import common.MainFrame;
import common.TablePanel;
import helpers.Window;

public class CustomerSigninFrame extends JFrame {
    private JPanel pnlMain = new JPanel();
    private JPanel pnlFields = new JPanel();
    private JLabel lblSSN = new JLabel("Personnummer (YYMMDDXXXX):");
    private JTextField tfSSN = new JTextField("");
    private JLabel lblName = new JLabel("Namn:");
    private JTextField tfName = new JTextField("");
    private JLabel lblAddress = new JLabel("Address:");
    private JTextField tfAddress = new JTextField("");
    private JLabel lblEmail = new JLabel("Email:");
    private JTextField tfEmail = new JTextField("");
    private JButton btnSignin = new JButton("Ok");
    private JCheckBox cbTOS = new JCheckBox("<html>Jag godkänner att mitt personnummer lagras som kundnummer enligt personuppgiftslagen</html>");
    private MainController mainController;

    public CustomerSigninFrame(MainController mainController) {
        this.mainController = mainController;
        
        pnlMain.setLayout(new BorderLayout());
        pnlMain.setVisible(true);

        pnlFields.setLayout(new GridLayout(4, 2));
        pnlFields.setPreferredSize(new Dimension(400, 100));
        pnlFields.setVisible(true);
        
        pnlFields.add(lblSSN);
        pnlFields.add(tfSSN);
        pnlFields.add(lblName);
        pnlFields.add(tfName);
        pnlFields.add(lblAddress);
        pnlFields.add(tfAddress);
        pnlFields.add(lblEmail);
        pnlFields.add(tfEmail);
        
        btnSignin.addActionListener(new Listener());

        pnlMain.add(pnlFields, BorderLayout.NORTH);
        pnlMain.add(cbTOS, BorderLayout.CENTER);
        pnlMain.add(btnSignin, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(400, 200));
        add(pnlMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        Window.center(this);
    }

    public void checkInfo() {
        String ssn = tfSSN.getText();
        String name = tfName.getText();
        String address = tfAddress.getText();
        String email = tfEmail.getText();
        boolean pul = cbTOS.isSelected();
        String error = "";

        if (!ssn.matches("^[0-9]*$") || ssn.length() != 10) {
            error += "Personnumret är inte giltigt enligt formatet YYMMDDXXXX.\n";
        }
        
        if(name.length() < 1) {
            error += "Namn får inte vara tomt.\n";
        }
        
        if(address.length() < 1) {
            error += "Adress-fältet får inte vara tomt.";
        }
        
        if(email.length() < 1) {
            error += "Email får inte vara tomt.\n";
        }
        
        if(!pul) {
            error += "Du måste godkänna att ditt personnummer används som kundnummer för att kunna handla.\n";
        }

        if (error.length() > 0) {
            JOptionPane.showMessageDialog(this, error);
        }
        else {
            setVisible(false);
            long customerID = Long.parseLong(ssn);
            mainController.addNewCustomer(customerID, name, address, email);
            mainController.setCustomerID(customerID);
            mainController.showCustomerMainFrame();
        }
    }

    public class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnSignin) {
                checkInfo();
            }
        }

    }
}
