/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.todo.gui2;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hilel
 */
public class ProjectTableModel extends DefaultTableModel {

    Class[] types;

    public ProjectTableModel() {
        Object[][] data = new Object[1][7];
        Object[] columnNames = new String[]{
            "#", "נושא", "התחלה", "סיום", "קטגוריה", "חשיבות", "סטטוס"
        };
        setDataVector(data, columnNames);

        types = new Class[]{Long.class, String.class, Object.class, Object.class, Object.class, Object.class, Object.class};
    }
    /*
     @Override
     public Class getColumnClass(int columnIndex) {
     return types[columnIndex];
     }
     */
}
