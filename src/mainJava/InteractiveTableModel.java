package mainJava;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class InteractiveTableModel extends AbstractTableModel {
    public static final int NAME_INDEX = 0;
    public static final int DATE_INDEX = 1;
    public static final int COUNT_INDEX = 2;
    public static final int HIDDEN_INDEX = 3;
    protected String[] columnNames;
    protected Vector dataVector;
    public InteractiveTableModel(String[] columnNames) {
        this.columnNames = columnNames;
        dataVector = new Vector();
    }
    public String getColumnName(int column) {
        return columnNames[column];
    }
    public boolean isCellEditable(int row, int column) {
        return true;
    }
    public Class getColumnClass(int column) {
        switch (column) {
            case NAME_INDEX:
                return String.class;
            case DATE_INDEX:
                return Integer.class;
            case COUNT_INDEX:
                return Integer.class;
        }
        return Object.class;
    }
    public Object getValueAt(int row, int column) {
        TableRow record = (TableRow) dataVector.get(row);
        switch (column) {
            case NAME_INDEX:
                return record.getSubject();
            case DATE_INDEX:
                return record.getDeadline();
            case COUNT_INDEX:
                return record.getCount();
            default:
                return new Object();
        }
 }
    public void setValueAt(Object value, int row, int column) {
        TableRow record = (TableRow) dataVector.get(row);
        switch (column) {
            case NAME_INDEX:
                record.setSubject((String) value);
                break;
            case DATE_INDEX:
                record.setDeadline((Integer) value);
                break;
            case COUNT_INDEX:
                record.setCount((Integer) value);
                break;
            default:
                System.out.println("invalid index");
        }
        fireTableCellUpdated(row, column);
    }
    public int getRowCount() {
        return dataVector.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }
    public void deleteRows() {
        fireTableRowsDeleted(0, getRowCount());
    }
    public void addEmptyRow() {
        dataVector.add(new TableRow());
        fireTableRowsInserted(dataVector.size() - 1, dataVector.size() - 1);
    }
}
