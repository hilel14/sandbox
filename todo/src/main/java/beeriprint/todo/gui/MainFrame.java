/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.todo.gui;

import beeriprint.todo.controller.JdbcController;
import beeriprint.todo.model.Category;
import beeriprint.todo.model.Project;
import beeriprint.todo.model.Status;
import beeriprint.todo.model.Task;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author hilel
 */
public class MainFrame extends javax.swing.JFrame {

    static final Logger logger = Logger.getLogger(MainFrame.class.getName());
    static Preferences preferences = Preferences.userNodeForPackage(MainFrame.class);
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        loadPreferences();
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        fixComponentsOrientation();
        try {
            setup();
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        commandPanel = new javax.swing.JPanel();
        command1 = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        projectTableScroll = new javax.swing.JScrollPane();
        projectTable = new javax.swing.JTable();
        descriptionLable = new javax.swing.JLabel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionTextPane = new javax.swing.JTextPane();
        taskListLabel = new javax.swing.JLabel();
        taskTableScroll = new javax.swing.JScrollPane();
        taskTable = new javax.swing.JTable();
        statusLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        viewMenu = new javax.swing.JMenu();
        desktopTasksMenuItem = new javax.swing.JMenuItem();
        activeTasksMenuItem = new javax.swing.JMenuItem();
        allTasksMenuItem = new javax.swing.JMenuItem();
        projectMenu = new javax.swing.JMenu();
        newProjectMenuItem = new javax.swing.JMenuItem();
        editProjectMenuItem = new javax.swing.JMenuItem();
        saveProjectMenuItem = new javax.swing.JMenuItem();
        deleteProjectMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        saveAllMenuItem = new javax.swing.JMenuItem();
        taskMenu = new javax.swing.JMenu();
        newTaskMenuItem = new javax.swing.JMenuItem();
        editTaskMenuItem = new javax.swing.JMenuItem();
        saveTaskMenuItem = new javax.swing.JMenuItem();
        deleteTaskMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ToDo");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        command1.setText("Command 1");
        command1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                command1ActionPerformed(evt);
            }
        });
        commandPanel.add(command1);

        getContentPane().add(commandPanel, java.awt.BorderLayout.NORTH);

        mainPanel.setLayout(new java.awt.GridBagLayout());

        projectTable.setAutoCreateRowSorter(true);
        projectTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "נושא", "התחלה", "סיום", "קטגוריה", "חשיבות", "סטטוס"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        projectTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                projectTableMouseClicked(evt);
            }
        });
        projectTableScroll.setViewportView(projectTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        mainPanel.add(projectTableScroll, gridBagConstraints);

        descriptionLable.setText("Description");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        mainPanel.add(descriptionLable, gridBagConstraints);

        descriptionScrollPane.setViewportView(descriptionTextPane);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        mainPanel.add(descriptionScrollPane, gridBagConstraints);

        taskListLabel.setText("Tasks");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        mainPanel.add(taskListLabel, gridBagConstraints);

        taskTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "#", "בוצע", "תיאור"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Boolean.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        taskTableScroll.setViewportView(taskTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        mainPanel.add(taskTableScroll, gridBagConstraints);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        statusLabel.setText("Status:");
        getContentPane().add(statusLabel, java.awt.BorderLayout.SOUTH);

        viewMenu.setText("View");

        desktopTasksMenuItem.setText("Active projects");
        viewMenu.add(desktopTasksMenuItem);

        activeTasksMenuItem.setText("Open projects");
        viewMenu.add(activeTasksMenuItem);

        allTasksMenuItem.setText("All projects");
        viewMenu.add(allTasksMenuItem);

        menuBar.add(viewMenu);

        projectMenu.setText("Project");

        newProjectMenuItem.setText("New");
        newProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newProjectMenuItemActionPerformed(evt);
            }
        });
        projectMenu.add(newProjectMenuItem);

        editProjectMenuItem.setText("Edit...");
        editProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProjectMenuItemActionPerformed(evt);
            }
        });
        projectMenu.add(editProjectMenuItem);

        saveProjectMenuItem.setText("Save");
        saveProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveProjectMenuItemActionPerformed(evt);
            }
        });
        projectMenu.add(saveProjectMenuItem);

        deleteProjectMenuItem.setText("Delete...");
        deleteProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProjectMenuItemActionPerformed(evt);
            }
        });
        projectMenu.add(deleteProjectMenuItem);
        projectMenu.add(jSeparator1);

        saveAllMenuItem.setText("Save all");
        projectMenu.add(saveAllMenuItem);

        menuBar.add(projectMenu);

        taskMenu.setText("Task");

        newTaskMenuItem.setText("New");
        newTaskMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newTaskMenuItemActionPerformed(evt);
            }
        });
        taskMenu.add(newTaskMenuItem);

        editTaskMenuItem.setText("Edit...");
        taskMenu.add(editTaskMenuItem);

        saveTaskMenuItem.setText("Save");
        saveTaskMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveTaskMenuItemActionPerformed(evt);
            }
        });
        taskMenu.add(saveTaskMenuItem);

        deleteTaskMenuItem.setText("Delete...");
        deleteTaskMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTaskMenuItemActionPerformed(evt);
            }
        });
        taskMenu.add(deleteTaskMenuItem);

        menuBar.add(taskMenu);

        setJMenuBar(menuBar);

        setBounds(0, 0, 865, 534);
    }// </editor-fold>//GEN-END:initComponents

    private void command1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_command1ActionPerformed
        BidiTextEditor dialog = new BidiTextEditor(this, true);
        String text = dialog.showDialog();
        System.out.println(text);
    }//GEN-LAST:event_command1ActionPerformed

    private void projectTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_projectTableMouseClicked
        showProjectDetails();
    }//GEN-LAST:event_projectTableMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        storePreferences();
    }//GEN-LAST:event_formWindowClosing

    private void editProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProjectMenuItemActionPerformed
        int selection = projectTable.convertRowIndexToModel(projectTable.getSelectedRow());
        //Project project = (Project) projectTable.getModel().getValueAt(selection, 0);
        String title = projectTable.getModel().getValueAt(selection, 1).toString();
        BidiTextEditor dialog = new BidiTextEditor(this, true);
        dialog.setInitialText(title);
        String input = dialog.showDialog();
        if (input != null) {
            projectTable.getModel().setValueAt(input, selection, 1);
        }
    }//GEN-LAST:event_editProjectMenuItemActionPerformed

    private void saveProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveProjectMenuItemActionPerformed
        saveProject();
    }//GEN-LAST:event_saveProjectMenuItemActionPerformed

    private void saveTaskMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveTaskMenuItemActionPerformed
        saveTask();
    }//GEN-LAST:event_saveTaskMenuItemActionPerformed

    private void newProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newProjectMenuItemActionPerformed
        addProject();
    }//GEN-LAST:event_newProjectMenuItemActionPerformed

    private void deleteProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteProjectMenuItemActionPerformed
        deleteProject();
    }//GEN-LAST:event_deleteProjectMenuItemActionPerformed

    private void newTaskMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newTaskMenuItemActionPerformed
        addTask();
    }//GEN-LAST:event_newTaskMenuItemActionPerformed

    private void deleteTaskMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteTaskMenuItemActionPerformed
        deleteTask();
    }//GEN-LAST:event_deleteTaskMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem activeTasksMenuItem;
    private javax.swing.JMenuItem allTasksMenuItem;
    private javax.swing.JButton command1;
    private javax.swing.JPanel commandPanel;
    private javax.swing.JMenuItem deleteProjectMenuItem;
    private javax.swing.JMenuItem deleteTaskMenuItem;
    private javax.swing.JLabel descriptionLable;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JTextPane descriptionTextPane;
    private javax.swing.JMenuItem desktopTasksMenuItem;
    private javax.swing.JMenuItem editProjectMenuItem;
    private javax.swing.JMenuItem editTaskMenuItem;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newProjectMenuItem;
    private javax.swing.JMenuItem newTaskMenuItem;
    private javax.swing.JMenu projectMenu;
    private javax.swing.JTable projectTable;
    private javax.swing.JScrollPane projectTableScroll;
    private javax.swing.JMenuItem saveAllMenuItem;
    private javax.swing.JMenuItem saveProjectMenuItem;
    private javax.swing.JMenuItem saveTaskMenuItem;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JLabel taskListLabel;
    private javax.swing.JMenu taskMenu;
    private javax.swing.JTable taskTable;
    private javax.swing.JScrollPane taskTableScroll;
    private javax.swing.JMenu viewMenu;
    // End of variables declaration//GEN-END:variables

    private void setup() throws IOException, ClassNotFoundException, SQLException {
        try (JdbcController controller = new JdbcController();) {
            // category column
            Category[] categories = controller.findAllCategories().toArray(new Category[0]);
            JComboBox categoryCombo = new JComboBox(new DefaultComboBoxModel(categories));
            categoryCombo.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            projectTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(categoryCombo));
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            //Component component=renderer.getTableCellRendererComponent(null, null, false, false, 0, 0);
            renderer.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            projectTable.getColumnModel().getColumn(4).setCellRenderer(renderer);
            // priority column
            Integer[] priorities = new Integer[]{1, 2, 3};
            projectTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JComboBox(new DefaultComboBoxModel(priorities))));
            // status column
            Status[] statuses = controller.findAllStatuses().toArray(new Status[0]);
            projectTable.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JComboBox(new DefaultComboBoxModel(statuses))));
            // data
            fillProjectTable(controller);
            projectTable.setRowSelectionInterval(0, 0);
            showProjectDetails();
        }
    }

    private void fillProjectTable(JdbcController controller) throws IOException, ClassNotFoundException, SQLException {
        DefaultTableModel model = (DefaultTableModel) projectTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        List<Project> projects = controller.findAllProjects();
        for (Project project : projects) {
            model.addRow(project.toTableRow());
        }
    }

    private void showProjectDetails() {
        // get selected row
        int selection = projectTable.getSelectedRow();
        int index = projectTable.convertRowIndexToModel(selection);
        // extract project stored in the id column
        Project project = (Project) projectTable.getModel().getValueAt(index, 0);
        // fill lists
        descriptionTextPane.setText(project.getRemarks());
        fillTaskTable(project);
    }

    private void fillTaskTable(Project project) {
        DefaultTableModel model = (DefaultTableModel) taskTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        for (Task task : project.getTasks()) {
            model.addRow(task.toTableRow());
        }
    }

    private void loadPreferences() {
        // Main frame size and position
        int x = preferences.getInt("MainFrame.left", 10);
        int y = preferences.getInt("MainFrame.top", 25);
        int w = preferences.getInt("MainFrame.width", 800);
        int h = preferences.getInt("MainFrame.height", 600);
        setBounds(x, y, w, h);
        // Project table columns with
        TableColumnModel model = projectTable.getColumnModel();
        for (int i = 0; i < model.getColumnCount(); i++) {
            int width = preferences.getInt("ProjectTable.column." + i, 75);
            model.getColumn(i).setPreferredWidth(width);
        }
        // hide id column
        //model.getColumn(0).setPreferredWidth(0);
        //model.getColumn(0).setMinWidth(0);
        //model.getColumn(0).setMaxWidth(0);
        // Task table columns with
        model = taskTable.getColumnModel();
        for (int i = 0; i < model.getColumnCount(); i++) {
            int width = preferences.getInt("TaskTable.column." + i, 75);
            model.getColumn(i).setPreferredWidth(width);
        }
    }

    private void storePreferences() {
        // Main frame size and position
        preferences.putInt("MainFrame.left", getBounds().x);
        preferences.putInt("MainFrame.top", getBounds().y);
        preferences.putInt("MainFrame.width", getBounds().width);
        preferences.putInt("MainFrame.height", getBounds().height);
        // Project table columns with
        TableColumnModel model = projectTable.getColumnModel();
        for (int i = 0; i < model.getColumnCount(); i++) {
            int width = model.getColumn(i).getPreferredWidth();
            preferences.putInt("ProjectTable.column." + i, width);
        }
        // Task table columns with
        model = taskTable.getColumnModel();
        for (int i = 0; i < model.getColumnCount(); i++) {
            int width = model.getColumn(i).getPreferredWidth();
            preferences.putInt("TaskTable.column." + i, width);
        }
    }

    private void saveProject() {
        // get selected row
        int selection = projectTable.getSelectedRow();
        int index = projectTable.convertRowIndexToModel(selection);
        // extract project stored in the id column
        Project project = (Project) projectTable.getModel().getValueAt(index, 0);
        // update project data
        try (JdbcController controller = new JdbcController();) {
            project.setTitle(projectTable.getValueAt(index, 1).toString());
            project.setStartDate(dateFormat.parse(projectTable.getValueAt(index, 2).toString()));
            Object endDate = projectTable.getValueAt(index, 3);
            project.setEndDate(endDate == null ? null : dateFormat.parse(endDate.toString()));
            project.setCategory((Category) projectTable.getValueAt(index, 4));
            project.setPriority(Integer.parseInt(projectTable.getValueAt(index, 5).toString()));
            project.setStatus((Status) projectTable.getValueAt(index, 6));
            // description
            project.setRemarks(descriptionTextPane.getText());
            // update database record
            controller.updateProject(project);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveTask() {
        // get selected row
        int selection = taskTable.getSelectedRow();
        int index = taskTable.convertRowIndexToModel(selection);
        // extract task stored in the id column
        Task task = (Task) taskTable.getModel().getValueAt(index, 0);
        // update task data
        try (JdbcController controller = new JdbcController();) {
            task.setCompleted(Boolean.parseBoolean(taskTable.getValueAt(index, 1).toString()));
            task.setDescription(taskTable.getValueAt(index, 2).toString());
            // update database record
            controller.updateTask(task);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProject() {
        try (JdbcController controller = new JdbcController();) {
            int id = controller.insertProject("New project " + new Date().toString());
            Project project = controller.findProjectById(id);
            DefaultTableModel model = (DefaultTableModel) projectTable.getModel();
            model.addRow(project.toTableRow());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProject() {
        int selection = projectTable.convertRowIndexToModel(projectTable.getSelectedRow());
        Project project = (Project) projectTable.getModel().getValueAt(selection, 0);
        String msg = "Delete project #" + project.getId() + " and all related taks?";
        int retVal = JOptionPane.showConfirmDialog(null, msg, "Confirmation", JOptionPane.YES_NO_OPTION);
        if (retVal == JOptionPane.YES_OPTION) {
            try (JdbcController controller = new JdbcController();) {
                for (Task task : project.getTasks()) {
                    controller.deleteTask(task.getId());
                }
                controller.deleteProject(project.getId());
                DefaultTableModel model = (DefaultTableModel) projectTable.getModel();
                model.removeRow(selection);
                projectTable.setRowSelectionInterval(0, 0);
                showProjectDetails();
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addTask() {
        // get selected row
        int selection = projectTable.getSelectedRow();
        int index = projectTable.convertRowIndexToModel(selection);
        // extract project stored in the id column
        Project project = (Project) projectTable.getModel().getValueAt(index, 0);
        // update project data
        try (JdbcController controller = new JdbcController();) {
            int id = controller.insertTask(project.getId(), "New task " + new Date().toString());
            Task task = controller.findTaskById(id);
            project.getTasks().add(task);
            fillTaskTable(project);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fixComponentsOrientation() {
        ComponentOrientation orientation = getComponentOrientation();
        //menuBar.setComponentOrientation(orientation);
        projectTable.setComponentOrientation(orientation);
        descriptionTextPane.setComponentOrientation(orientation);
        taskTable.setComponentOrientation(orientation);
        // fix project table title and task table description
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setComponentOrientation(orientation);
        projectTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
        taskTable.getColumnModel().getColumn(2).setCellRenderer(renderer);
    }

    private void deleteTask() {
        int selection = taskTable.convertRowIndexToModel(taskTable.getSelectedRow());
        Task task = (Task) taskTable.getModel().getValueAt(selection, 0);
        String msg = "Delete task #" + task.getId() + "?";
        int retVal = JOptionPane.showConfirmDialog(null, msg, "Confirmation", JOptionPane.YES_NO_OPTION);
        if (retVal == JOptionPane.YES_OPTION) {
            try (JdbcController controller = new JdbcController();) {
                controller.deleteTask(task.getId());
                DefaultTableModel model = (DefaultTableModel) taskTable.getModel();
                model.removeRow(selection);
                taskTable.setRowSelectionInterval(0, 0);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}