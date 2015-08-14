/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.todo.gui2;

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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    static Preferences preferences = Preferences.userNodeForPackage(beeriprint.todo.gui2.MainFrame.class);
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
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
        addProjectTable();
        addProjectRemarks();
        addTaskTable();
        addListeners();
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
        // project table mouse click
        projectTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                projectTableMouseClicked(evt);
            }
        });
        // form window closing
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
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

    private void showProjectDetails() {
        // get selected row
        int selection = projectTable.getSelectedRow();
        int index = projectTable.convertRowIndexToModel(selection);
        // extract project stored in the id column
        Project project = (Project) projectTable.getModel().getValueAt(index, 0);
        // fill lists
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

    private void projectTableMouseClicked(java.awt.event.MouseEvent evt) {
        showProjectDetails();
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        storePreferences();
    }
}
