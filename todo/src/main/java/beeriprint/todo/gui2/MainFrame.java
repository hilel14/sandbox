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
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
    ProjectTableModel projectTableModel;
    ProjectTable projectTable;
    JScrollPane projectTableScroll;
    // project description
    JLabel projectDescriptionLabel;
    JTextArea projectDescriptionText;
    // task table
    JLabel taskTableLabel;
    TaskTableModel taskTableModel;
    TaskTable taskTable;
    JScrollPane taskTableScroll;

    public MainFrame() {
        try {
            initComponents();
            loadPreferences();
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

    private void initComponents() throws IOException, ClassNotFoundException, SQLException {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        setLayout(new java.awt.GridBagLayout());
        setTitle("My Todo List");
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
        addProjectTable();
        addProjectDescriptionText();
        addTaskTable();
        addListeners();
    }

    private void addProjectTable() throws IOException, ClassNotFoundException, SQLException {
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
        ProjectTableModel model = new ProjectTableModel();
        projectTable = new ProjectTable();
        projectTable.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        projectTableScroll = new JScrollPane(projectTable);
        add(projectTableScroll, gridBagConstraints);
    }

    private void addProjectDescriptionText() {
        // label
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.0;
        projectDescriptionLabel = new JLabel("תיאור");
        projectDescriptionLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        add(projectDescriptionLabel, gridBagConstraints);
        // text-area
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        // create ojects
        projectDescriptionText = new JTextArea();
        projectDescriptionText.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        // add to frame
        add(projectDescriptionText, gridBagConstraints);
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
        TaskTableModel model = new TaskTableModel();
        taskTable = new TaskTable(model);
        taskTable.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        taskTableScroll = new JScrollPane(taskTable);
        add(taskTableScroll, gridBagConstraints);
    }

    private void addListeners() {
        // formWindowClosing
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
            //fillProjectTable(controller);
            //projectTable.setRowSelectionInterval(0, 0);
            //showProjectDetails();
        }
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        storePreferences();
    }
}
