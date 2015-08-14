/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.todo.gui2;

import java.awt.ComponentOrientation;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hilel
 */
public class TaskTable extends javax.swing.JTable {

    final Object[] columnNames = new String[]{"#", "בוצע", "תיאור"};

    public TaskTable() {
        super();
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        setAutoCreateRowSorter(true);
        setModel(new TaskTableModel(columnNames, 0));
    }

    class TaskTableModel extends DefaultTableModel {

        final Class[] types = new Class[]{Integer.class, Boolean.class, String.class};

        public TaskTableModel(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        @Override
        public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }
    }
}
