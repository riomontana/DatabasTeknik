package common;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import database.DBConnection;
import helpers.Window;

public class MainFrame extends JFrame {
    private TablePanel tablePanel;

    public MainFrame(TablePanel tablePanel, boolean visible) {
        this.tablePanel = tablePanel;
        
        setVisible(visible);
        setPreferredSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        add(tablePanel, BorderLayout.CENTER);
        pack();
        Window.center(this);
    }
    
    public TablePanel getTablePanel() {
        return this.tablePanel;
    }
}
