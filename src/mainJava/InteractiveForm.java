package mainJava;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

public class InteractiveForm extends JPanel {
    private final int[] preferredWidth = {300, 60, 40, 0};
    private JTable table;
    protected JScrollPane scroller;
    protected InteractiveTableModel tableModel;
   protected boolean isEnabled;
    public InteractiveForm(InteractiveTableModel interactiveTableModel, boolean isEnabled) {
        tableModel = interactiveTableModel;
        this.isEnabled = isEnabled;
        initComponent();
    }
    public void initComponent() {
        tableModel.addTableModelListener(new InteractiveForm.InteractiveTableModelListener());
        table = new JTable();
        table.setModel(tableModel);
   table.setEnabled(isEnabled);
        scroller = new javax.swing.JScrollPane(table);
        table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            TableColumn current = table.getColumnModel().getColumn(i);
            current.setMinWidth(0);
            current.setPreferredWidth(preferredWidth[i]);
            current.setMaxWidth(400);
        }
        table.getColumnModel().getColumn(InteractiveTableModel.HIDDEN_INDEX).setMaxWidth(0);
        setLayout(new BorderLayout());
        add(scroller, BorderLayout.CENTER);
    }
    public class InteractiveTableModelListener implements TableModelListener {
        public void tableChanged(TableModelEvent evt) {
            if (evt.getType() == TableModelEvent.UPDATE) {
                int column = evt.getColumn();
   int row = evt.getFirstRow();
                table.setColumnSelectionInterval(column + 1, column + 1);
                table.setRowSelectionInterval(row, row);
            }
            Window.setSubjects(evt.getFirstRow(), evt.getColumn());
        }
    }

    public JTable getTable() {
        return table;
    }
}
