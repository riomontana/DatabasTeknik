package common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import database.DBConnection;
import store.CustomerListFrame;

public class TablePanel extends JPanel {
    private JPanel pnlTop = new JPanel();
    private JButton btnDisplayAll = new JButton("Display all");
    private String[] types = { "model", "manufacturer", "type" };
    private String[] searchTypes = { "Modell", "Tillverkare", "Typ" };
    private JComboBox<String> cbSearch = new JComboBox<String>(searchTypes);
    private JTextField tfSearch = new JTextField();
    private JButton btnSearch = new JButton("Sök");
    private ApplianceTableModel applianceTableModel = new ApplianceTableModel();
    private JTable tableResults = new JTable(applianceTableModel);
    private JScrollPane spTable = new JScrollPane(tableResults);
    private DBConnection dbConnection;
    private JButton btnSouth;
    private ButtonListener buttonListener;
    private CustomerListFrame clf;

    private Object[][] objTest1 = { { "1", "1", "1", "1", "1", "1" }, { "2", "2", "2", "2", "2", "2" } }; // TODO:
                                                                                                          // Temp
                                                                                                          // -
                                                                                                          // Remove

    public TablePanel(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
        clf = new CustomerListFrame(dbConnection);
        setLayout(new BorderLayout());
        setVisible(true);
        setPreferredSize(new Dimension(1024, 768));

        createTableStructure();
        createTopView();

        add(pnlTop, BorderLayout.NORTH);
        add(spTable, BorderLayout.CENTER);
    }

    private void createTopView() {
        buttonListener = new ButtonListener();
        btnDisplayAll.addActionListener(buttonListener);
        btnSearch.addActionListener(buttonListener);

        tfSearch.setPreferredSize(new Dimension(250, 26));
        tfSearch.setText("Sök...");

        pnlTop.setLayout(new FlowLayout());
        pnlTop.add(btnDisplayAll);
        pnlTop.add(cbSearch);
        pnlTop.add(tfSearch);
        pnlTop.add(btnSearch);
    }

    private void createTableStructure() {

    }

    public void setMouseListener(MouseListener listener) {
        tableResults.addMouseListener(listener);
    }

    public int getSelectedRowId(Point point) {
        int row = tableResults.rowAtPoint(point);

        return (int) tableResults.getValueAt(row, 0);
    }

    public Object[] getSelectedRow(Point point) {
        int row = tableResults.rowAtPoint(point);

        return applianceTableModel.getRow(row);
    }

    public void replaceData(Object[][] object) {
        applianceTableModel.replaceData(object);
    }

    public void addButtonSouth(String btnText) {
        btnSouth = new JButton(btnText);
        add(btnSouth, BorderLayout.SOUTH);
        btnSouth.addActionListener(buttonListener);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnSearch) {
                int index = cbSearch.getSelectedIndex();

                if (index == -1) {
                    JOptionPane.showMessageDialog(null, "Du måste välja en typ att söka efter.");
                } else {
                    Object[][] objects = dbConnection.getDatabaseCommonQuery().searchProducts(types[index],
                            tfSearch.getText());
                    replaceData(objects);
                }
            } else if (e.getSource() == btnDisplayAll) {
                replaceData(dbConnection.getDatabaseCommonQuery().getProducts());

            } else if (e.getSource() == btnSouth) {
                clf.showWindow();
            }
        }

    }
}