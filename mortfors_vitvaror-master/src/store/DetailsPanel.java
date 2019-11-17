package store;

import java.awt.BorderLayout;
import common.TablePanel;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import common.ApplianceTableModel;

public class DetailsPanel extends JPanel {

    private String[] columnName;
    private Object[][] objTest1;
    private ApplianceTableModel dataTable;
    private JTable vendorTable;
    private JScrollPane scrollTop;
    private JPanel pnlTop = new JPanel(new BorderLayout());
    private DetailsFrame detailsFrame;

    public DetailsPanel(String[] columnName, Object[][] object, DetailsFrame detailsFrame) {
        this.columnName = columnName;
        this.objTest1 = object;
        this.detailsFrame=detailsFrame;

        dataTable = new ApplianceTableModel(this.columnName, objTest1);
        vendorTable = new JTable(dataTable);
        scrollTop = new JScrollPane(vendorTable);

        setLayout(new BorderLayout());
        setVisible(true);
        setPreferredSize(new Dimension(1024, 200));

        pnlTop.add(scrollTop, BorderLayout.CENTER);
        add(pnlTop, BorderLayout.CENTER);

        dataTable.replaceData(objTest1);
        
        if(columnName[0].equals("Id")){
            vendorTable.addMouseListener(new TableListener());
        }

    }
    
    public int getSelectedRowId(Point point) {
        int row = vendorTable.rowAtPoint(point);
        
        return (int) vendorTable.getValueAt(row, 0);
    }
    
    public void replaceData(Object[][] objects) {
        dataTable.replaceData(objects);
    }
    
    private class TableListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            int supplierid = getSelectedRowId(e.getPoint());
            detailsFrame.supplierPurchaseFrame(supplierid);
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
    }
}
