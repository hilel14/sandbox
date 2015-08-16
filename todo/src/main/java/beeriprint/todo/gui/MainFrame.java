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
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
    final List<Integer> arrowKeys = new ArrayList<>();
    int projectTableSelectedRow = -1;

    // menu
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem newProjectMenuItem;
    JMenuItem deleteProjectMenuItem;
    JMenuItem newTaskMenuItem;
    JMenuItem deleteTaskMenuItem;

    // Frame layout
    GridBagConstraints gridBagConstraints = new GridBagConstraints();

    // Action panel
    JPanel actionPanel;
    JButton newProjectButton;
    JButton deleteProjectButton;
    JButton newTaskButton;
    JButton deleteTaskButton;

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
    // Status label
    JLabel statusLabel;

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
        addActionPanel();
        addProjectTable();
        addProjectRemarks();
        addTaskTable();
        addStatusLabel();
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

    private void addActionPanel() {
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.0;
        actionPanel = new JPanel(new FlowLayout());
        actionPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        add(actionPanel, gridBagConstraints);
        // new project button
        newProjectButton = new JButton("פרויקט חדש");
        actionPanel.add(newProjectButton);
        // delete project button
        deleteProjectButton = new JButton("מחיקת פרויקט");
        deleteProjectButton.setForeground(Color.red);
        actionPanel.add(deleteProjectButton);
        // new task button
        newTaskButton = new JButton("מטלה חדשה");
        actionPanel.add(newTaskButton);
        // delete task button
        deleteTaskButton = new JButton("מחיקת מטלה");
        deleteTaskButton.setForeground(Color.red);
        actionPanel.add(deleteTaskButton);
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

    private void addStatusLabel() {
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.0;
        statusLabel = new JLabel("status:");
        //taskTableLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        add(statusLabel, gridBagConstraints);
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

        // new project button
        newProjectButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newProjectButtonActionPerformed(evt);
            }
        });
        // delete project button
        deleteProjectButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProjectButtonActionPerformed(evt);
            }
        });
        // new task button
        newTaskButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newTaskButtonActionPerformed(evt);
            }
        });
        // delete task button
        deleteTaskButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTaskButtonActionPerformed(evt);
            }
        });

        // project table mouse click
        projectTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                projectTableMouseClicked(evt);
            }
        });
        // project table key released
        projectTable.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                projectTableKeyReleased(evt);
            }
        });
        // task table mouse click
        taskTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                taskTableMouseClicked(evt);
            }
        });
        // task table key released
        taskTable.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                taskTableKeyReleased(evt);
            }
        });
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        storePreferences();
        int index = projectTable.getSelectedRowConverted();
        if (index >= 0) {
            saveProject(index);
        }
    }

    private void newProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        addProject();
    }

    private void saveProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        //saveProject();
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

    private void newProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {
        addProject();
    }

    private void deleteProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {
        deleteProject();
    }

    private void newTaskButtonActionPerformed(java.awt.event.ActionEvent evt) {
        addTask();
    }

    private void deleteTaskButtonActionPerformed(java.awt.event.ActionEvent evt) {
        deleteTask();
    }

    private void projectTableMouseClicked(java.awt.event.MouseEvent evt) {
        showProjectDetails();
        enableButtonsAndMenues();
    }

    private void projectTableKeyReleased(java.awt.event.KeyEvent evt) {
        if (arrowKeys.contains(evt.getKeyCode())) {
            showProjectDetails();
            enableButtonsAndMenues();
        }
    }

    private void taskTableMouseClicked(java.awt.event.MouseEvent evt) {
        enableButtonsAndMenues();
    }

    private void taskTableKeyReleased(java.awt.event.KeyEvent evt) {
        if (arrowKeys.contains(evt.getKeyCode())) {
            enableButtonsAndMenues();
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
        arrowKeys.add(KeyEvent.VK_UP);
        arrowKeys.add(KeyEvent.VK_DOWN);
        arrowKeys.add(KeyEvent.VK_KP_UP);
        arrowKeys.add(KeyEvent.VK_KP_DOWN);
        try (JdbcController controller = new JdbcController();) {
            fillCategoryCombo(controller.findAllCategories());
            fillPriorityCombo(controller.findAllPriorities());
            fillStatusCombo(controller.findAllStatuses());
            fillProjectTable(controller);
            enableButtonsAndMenues();
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

    private void showProjectDetails() {
        Project project;
        if (projectTableSelectedRow >= 0) {
            int selectedRow = projectTable.getSelectedRowConverted();
            if (selectedRow != projectTableSelectedRow) {
                saveProject(projectTableSelectedRow);
            }
        }
        projectTableSelectedRow = projectTable.getSelectedRowConverted();
        project = (Project) projectTable.getModel().getValueAt(projectTableSelectedRow, 0);
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
    private void saveProject(int index) {
        // Make sure Project object stored in project table selected row is updated
        Project project = (Project) projectTable.getModel().getValueAt(index, 0);
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
        int index = projectTable.getSelectedRowConverted();
        Project project = (Project) projectTable.getModel().getValueAt(index, 0);
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
                projectModel.removeRow(index);
                // delete project record
                controller.deleteProject(project.getId());
                projectTableSelectedRow = -1;
                enableButtonsAndMenues();
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addTask() {
        Project project = (Project) projectTable.getModel().getValueAt(projectTable.getSelectedRowConverted(), 0);
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
            Project project = (Project) projectTable.getModel().getValueAt(projectTable.getSelectedRowConverted(), 0);
            project.getTasks().remove(task);
            try (JdbcController controller = new JdbcController();) {
                controller.deleteTask(task.getId());
                DefaultTableModel model = (DefaultTableModel) taskTable.getModel();
                model.removeRow(index);
                enableButtonsAndMenues();
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void enableButtonsAndMenues() {
        // menues        
        deleteProjectMenuItem.setEnabled((projectTable.getSelectedRow() >= 0));
        newTaskMenuItem.setEnabled((projectTable.getSelectedRow() >= 0));
        deleteTaskMenuItem.setEnabled(taskTable.getSelectedRow() >= 0);
        // buttons
        deleteProjectButton.setEnabled((projectTable.getSelectedRow() >= 0));
        newTaskButton.setEnabled((projectTable.getSelectedRow() >= 0));
        deleteTaskButton.setEnabled(taskTable.getSelectedRow() >= 0);
    }
}
