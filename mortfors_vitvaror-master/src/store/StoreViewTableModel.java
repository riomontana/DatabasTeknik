package store;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import common.TablePanel;
import database.DBConnection;

public class StoreViewTableModel extends TablePanel {
    private DetailsFrame detailsFrame;
    private DBConnection dbConnection;
    private MainStoreController mainStoreController;

    public StoreViewTableModel(DBConnection dbConnection, MainStoreController mainStoreController) {
        super(dbConnection);
        
        this.dbConnection = dbConnection;
        this.mainStoreController=mainStoreController;
        this.detailsFrame = new DetailsFrame(dbConnection);

        setMouseListener(new TableListener());
    }

    private class TableListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            int id = getSelectedRowId(e.getPoint());
            detailsFrame.showWindow(id);
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
