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
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author hilel
 */
public class MainFrame extends JFrame {

    static final Logger logger = Logger.getLogger(MainFrame.class.getName());
    static Preferences preferences = Preferences.userNodeForPackage(beeriprint.todo.gui.MainFrame.class);
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    GridBagConstraints gridBagConstraints = new GridBagConstraints();

    // project table
    JLabel projectTableLabel;
    ProjectTable projectTable;
    JScrollPane projectTableScroll;
    // project remarks
    JLabel projectRemarksLabel;
    JTextArea projectRemarksText;
    // task table
    JLabel taskTableLabel;
    TaskTable taskTable;
    JScrollPane taskTableScroll;
    // menu
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem newProjectMenuItem;
    JMenuItem saveProjectMenuItem;
    JMenuItem deleteProjectMenuItem;
    JMenuItem newTaskMenuItem;
    JMenuItem deleteTaskMenuItem;

    public MainFrame() {
        initComponents();
        loadPreferences();
        try {
            setup();
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        setLayout(new java.awt.GridBagLayout());
        setTitle("My Todo List");
        addMenuBar();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
        addProjectTable();
        addProjectRemarks();
        addTaskTable();
        addListeners();
    }

    private void addMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        setJMenuBar(menuBar);
        addFileMenu();
    }

    private void addFileMenu() {
        fileMenu = new JMenu("קובץ");
        fileMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuBar.add(fileMenu);
        // new project
        newProjectMenuItem = new JMenuItem("פרויקט חדש");
        newProjectMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        fileMenu.add(newProjectMenuItem);
        // save project
        saveProjectMenuItem = new JMenuItem("שמירה");
        saveProjectMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        fileMenu.add(saveProjectMenuItem);
        // delete project
        deleteProjectMenuItem = new JMenuItem("מחיקת פרויקט...");
        deleteProjectMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        fileMenu.add(deleteProjectMenuItem);
        // separator
        fileMenu.add(new JPopupMenu.Separator());
        // new task
        newTaskMenuItem = new JMenuItem("מטלה חדשה");
        newTaskMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        fileMenu.add(newTaskMenuItem);
        // delete task
        deleteTaskMenuItem = new JMenuItem("מחיקת מטלה...");
        deleteTaskMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        fileMenu.add(deleteTaskMenuItem);
    }

    private void addProjectTable() {
        // label
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.0;
        projectTableLabel = new JLabel("פרויקטים");
        projectTableLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        add(projectTableLabel, gridBagConstraints);
        // table
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        projectTable = new ProjectTable();
        projectTableScroll = new JScrollPane(projectTable);
        add(projectTableScroll, gridBagConstraints);
    }

    private void addProjectRemarks() {
        // label
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.0;
        projectRemarksLabel = new JLabel("הערות");
        projectRemarksLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        add(projectRemarksLabel, gridBagConstraints);
        // text-area
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        projectRemarksText = new JTextArea();
        projectRemarksText.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        add(projectRemarksText, gridBagConstraints);
    }

    private void addTaskTable() {
        // label
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.0;
        taskTableLabel = new JLabel("מטלות");
        taskTableLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        add(taskTableLabel, gridBagConstraints);
        // table
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        taskTable = new TaskTable();
        taskTableScroll = new JScrollPane(taskTable);
        add(taskTableScroll, gridBagConstraints);
    }

    private void addListeners() {
        // form window closing
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        // new project menu item
        newProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newProjectMenuItemActionPerformed(evt);
            }
        });
        // save project menu item
        saveProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveProjectMenuItemActionPerformed(evt);
            }
        });
        // delete project menu item
        deleteProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProjectMenuItemActionPerformed(evt);
            }
        });
        // new task menu item
        newTaskMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newTaskMenuItemActionPerformed(evt);
            }
        });
        // delete task menu item
        deleteTaskMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTaskMenuItemActionPerformed(evt);
            }
        });
        // project table mouse click
        projectTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                projectTableMouseClicked(evt);
            }
        });
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        storePreferences();
        saveProjectTableData();
    }

    private void newProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        addProject();
    }

    private void saveProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        saveProject();
    }

    private void deleteProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        deleteProject();
    }

    private void newTaskMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        addTask();
    }

    private void deleteTaskMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        deleteTask();
    }

    private void projectTableMouseClicked(java.awt.event.MouseEvent evt) {
        showProjectDetails();
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

    private void setup() throws IOException, ClassNotFoundException, SQLException {
        try (JdbcController controller = new JdbcController();) {
            fillCategoryCombo(controller.findAllCategories());
            fillPriorityCombo(controller.findAllPriorities());
            fillStatusCombo(controller.findAllStatuses());
            fillProjectTable(controller);
        }
    }

    private void fillCategoryCombo(List<Category> categoryList) {
        DefaultComboBoxModel model = projectTable.getComboModel(4);
        for (Category category : categoryList) {
            model.addElement(category);
        }
    }

    private void fillPriorityCombo(List<Integer> priorities) {
        DefaultComboBoxModel model = projectTable.getComboModel(5);
        for (Integer priority : priorities) {
            model.addElement(priority);
        }
    }

    private void fillStatusCombo(List<Status> statusList) {
        DefaultComboBoxModel model = projectTable.getComboModel(6);
        for (Status status : statusList) {
            model.addElement(status);
        }
    }

    private void fillProjectTable(JdbcController controller) throws IOException, ClassNotFoundException, SQLException {
        DefaultTableModel model = (DefaultTableModel) projectTable.getModel();
        /*
         while (model.getRowCount() > 0) {
         model.removeRow(0);
         }
         */
        List<Project> projects = controller.findAllProjects();
        for (Project project : projects) {
            model.addRow(project.toTableRow());
        }
    }

    private Project getSelectedProject() {
        int selection = projectTable.getSelectedRow();
        if (selection < 0) {
            return null;
        }
        int index = projectTable.convertRowIndexToModel(selection);
        return (Project) projectTable.getModel().getValueAt(index, 0);
    }

    private void showProjectDetails() {
        Project project = getSelectedProject();
        projectRemarksText.setText(project.getRemarks());
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

    /**
     * Update Project and Task objects and save to database
     */
    private void saveProject() {
        // Make sure Project object stored in project table selected row is updated
        int index = projectTable.getSelectedRowConverted();
        Project project = getSelectedProject();
        try (JdbcController controller = new JdbcController();) {
            project.setTitle(projectTable.getValueAt(index, 1).toString());
            project.setStartDate(dateFormat.parse(projectTable.getValueAt(index, 2).toString()));
            Object endDate = projectTable.getValueAt(index, 3);
            project.setEndDate(endDate == null ? null : dateFormat.parse(endDate.toString()));
            project.setCategory((Category) projectTable.getValueAt(index, 4));
            project.setPriority(Integer.parseInt(projectTable.getValueAt(index, 5).toString()));
            project.setStatus((Status) projectTable.getValueAt(index, 6));
            project.setRemarks(projectRemarksText.getText());
            controller.updateProject(project);
            saveTasks(controller);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveTasks(JdbcController controller) throws SQLException {
        for (int i = 0; i < taskTable.getModel().getRowCount(); i++) {
            Task task = (Task) taskTable.getModel().getValueAt(i, 0);
            task.setCompleted(Boolean.parseBoolean(taskTable.getValueAt(i, 1).toString()));
            task.setDescription(taskTable.getValueAt(i, 2).toString());
            controller.updateTask(task);
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
                // clear task table                
                DefaultTableModel taskModel = (DefaultTableModel) taskTable.getModel();
                while (taskModel.getRowCount() > 0) {
                    taskModel.removeRow(0);
                }
                // delete task records
                for (Task task : project.getTasks()) {
                    controller.deleteTask(task.getId());
                }
                // clear project row
                DefaultTableModel projectModel = (DefaultTableModel) projectTable.getModel();
                projectModel.removeRow(selection);
                // delete project record
                controller.deleteProject(project.getId());
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addTask() {
        Project project = getSelectedProject();
        try (JdbcController controller = new JdbcController();) {
            int id = controller.insertTask(project.getId(), "New task " + new Date().toString());
            Task task = controller.findTaskById(id);
            project.getTasks().add(task);
            DefaultTableModel model = (DefaultTableModel) taskTable.getModel();
            model.addRow(task.toTableRow());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTask() {
        int index = taskTable.getSelectedRowConverted();
        Task task = (Task) taskTable.getModel().getValueAt(index, 0);
        String msg = "Delete task #" + task.getId() + "?";
        int retVal = JOptionPane.showConfirmDialog(null, msg, "Confirmation", JOptionPane.YES_NO_OPTION);
        if (retVal == JOptionPane.YES_OPTION) {
            Project project = getSelectedProject();
            project.getTasks().remove(task);
            try (JdbcController controller = new JdbcController();) {
                controller.deleteTask(task.getId());
                DefaultTableModel model = (DefaultTableModel) taskTable.getModel();
                model.removeRow(index);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveProjectTableData() {
        try (JdbcController controller = new JdbcController();) {
            for (int i = 0; i < projectTable.getModel().getRowCount(); i++) {
                Project project = (Project) projectTable.getValueAt(i, 0);
                project.setTitle(projectTable.getValueAt(i, 1).toString());
                project.setStartDate(dateFormat.parse(projectTable.getValueAt(i, 2).toString()));
                Object endDate = projectTable.getValueAt(i, 3);
                project.setEndDate(endDate == null ? null : dateFormat.parse(endDate.toString()));
                project.setCategory((Category) projectTable.getValueAt(i, 4));
                project.setPriority(Integer.parseInt(projectTable.getValueAt(i, 5).toString()));
                project.setStatus((Status) projectTable.getValueAt(i, 6));
                controller.updateProject(project);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
