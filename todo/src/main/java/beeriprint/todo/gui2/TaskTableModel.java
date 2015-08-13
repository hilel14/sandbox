/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.todo.gui2;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hilel
 */
public class TaskTableModel extends DefaultTableModel {

    Class[] types;

    public TaskTableModel() {
        Object[][] data = new Object[1][3];
        Object[] columnNames = new String[]{
            "#", "בוצע", "תיאור"
        };
        setDataVector(data, columnNames);

        types = new Class[]{Integer.class, Boolean.class, String.class};
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
    }
}
