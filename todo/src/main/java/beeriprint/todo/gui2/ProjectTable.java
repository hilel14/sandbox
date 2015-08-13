/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.todo.gui2;

import beeriprint.todo.controller.JdbcController;
import beeriprint.todo.model.Category;
import beeriprint.todo.model.Status;
import java.awt.ComponentOrientation;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author hilel
 */
public class ProjectTable extends javax.swing.JTable {

    Integer[] priorityList;
    Category[] categoryList;
    Status[] statusList;
    //DefaultComboBoxModel categoryComboModel = new DefaultComboBoxModel();
    JComboBox categoryCombo = new JComboBox();
    DefaultTableModel model;

    public ProjectTable() {
        super();
        Object[] columnNames = new String[]{"#", "נושא", "התחלה", "סיום", "קטגוריה", "חשיבות", "סטטוס"};
        model = new DefaultTableModel(columnNames, 0);
        setModel(model);
        setCellEditors2();
    }

    public ProjectTable(TableModel model) throws IOException, ClassNotFoundException, SQLException {
        super(model);
        setCellEditors2();
    }

    private void setCellEditors2() {
        getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(categoryCombo));
    }

    private void setCellEditors() throws IOException, ClassNotFoundException, SQLException {
        try (JdbcController controller = new JdbcController();) {
            // category column
            categoryList = controller.findAllCategories().toArray(new Category[0]);
            JComboBox categoryCombo = new JComboBox(new DefaultComboBoxModel(categoryList));
            categoryCombo.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(categoryCombo));
            // priority column
            priorityList = new Integer[]{1, 2, 3};
            getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JComboBox(new DefaultComboBoxModel(priorityList))));
            // status column
            statusList = controller.findAllStatuses().toArray(new Status[0]);
            getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JComboBox(new DefaultComboBoxModel(statusList))));
        }
    }
}
