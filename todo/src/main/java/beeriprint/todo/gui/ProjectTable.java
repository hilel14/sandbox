/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.todo.gui;

import java.awt.ComponentOrientation;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hilel
 */
public class ProjectTable extends javax.swing.JTable {

    final Object[] columnNames = new String[]{"#", "נושא", "התחלה", "סיום", "קטגוריה", "חשיבות", "סטטוס"};

    public ProjectTable() {
        super();
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        setAutoCreateRowSorter(true);
        setModel(new DefaultTableModel(columnNames, 0));
        setCellRenderers();
        setCellEditors();
    }

    private void setCellRenderers() {
        //Component component = renderer.getTableCellRendererComponent(null, null, false, false, 0, 0);
        //getColumnModel().getColumn(1).getCellRenderer().getTableCellRendererComponent(null, null, false, false, 0, 0).setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // title
        DefaultTableCellRenderer titleRenderer = new DefaultTableCellRenderer();
        titleRenderer.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        getColumnModel().getColumn(1).setCellRenderer(titleRenderer);

        // category
        DefaultTableCellRenderer categoryRenderer = new DefaultTableCellRenderer();
        categoryRenderer.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        getColumnModel().getColumn(4).setCellRenderer(categoryRenderer);

        // priority
        DefaultTableCellRenderer priorityRenderer = new DefaultTableCellRenderer();
        priorityRenderer.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        getColumnModel().getColumn(5).setCellRenderer(priorityRenderer);

        // status
        DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer();
        statusRenderer.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        getColumnModel().getColumn(6).setCellRenderer(statusRenderer);

    }

    private void setCellEditors() {
        //title
        JTextField text = new JTextField();
        text.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(text));

        // category
        JComboBox categoryComboBox = new JComboBox();
        /* this has no effect */
        categoryComboBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(categoryComboBox));

        // priority
        getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JComboBox()));

        // status
        getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JComboBox()));
    }

    public DefaultComboBoxModel getComboModel(int columnIndex) {
        JComboBox component = (JComboBox) getColumnModel().getColumn(columnIndex).getCellEditor().getTableCellEditorComponent(null, null, false, 0, 0);
        return (DefaultComboBoxModel) component.getModel();
    }

    public int getSelectedRowConverted() {
        return convertRowIndexToModel(getSelectedRow());
    }
}
