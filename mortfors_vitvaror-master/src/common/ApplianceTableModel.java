package common;

import javax.swing.table.AbstractTableModel;

public class ApplianceTableModel extends AbstractTableModel {
	private String[] columnNames = { "ID", "Typ", "Modell", "Tillverkare", "Pris", "Antal" };
	private Object[][] data = new Object[6][6];
	
	public ApplianceTableModel() {
		
	}
	
	public ApplianceTableModel(String[] columnNames) {
		this.columnNames = columnNames;
	}
	
	public ApplianceTableModel(String[] columnNames, Object[][] data) {
		this.columnNames = columnNames;
		this.data = data;
	}

	public int getColumnCount() {
		return columnNames.length;
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}

	public int getRowCount() {
		return data.length;
	}
	
	public Object[] getRow(int row) {
	    return data[row];
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	public void setValueAt(Object val, int row, int col) {
		data[row][col] = val;
		fireTableCellUpdated(row, col);
	}
	
	public void replaceData(Object[][] data) {
		this.data = data;
		fireTableDataChanged();
	}
}
