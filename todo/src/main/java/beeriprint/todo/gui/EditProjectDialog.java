/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.todo.gui;

import beeriprint.todo.controller.JdbcController;
import beeriprint.todo.model.Category;
import beeriprint.todo.model.Project;
import java.awt.ComponentOrientation;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 *
 * @author c7
 */
public class EditProjectDialog extends javax.swing.JDialog {

    static final Logger logger = Logger.getLogger(EditProjectDialog.class.getName());
    static Preferences preferences = Preferences.userNodeForPackage(EditProjectDialog.class);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Project project;
    private MainFrame mainFrame;

    /**
     * Creates new form EditProjectDialog
     *
     * @param parent
     * @param modal
     */
    public EditProjectDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadPreferences();
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

        titleLabel = new javax.swing.JLabel();
        titleText = new javax.swing.JTextField();
        titleDirectionButton = new javax.swing.JButton();
        startLabel = new javax.swing.JLabel();
        startText = new javax.swing.JTextField();
        endLabel = new javax.swing.JLabel();
        endText = new javax.swing.JTextField();
        categoryLabel = new javax.swing.JLabel();
        categoryCombo = new javax.swing.JComboBox();
        priorityLabel = new javax.swing.JLabel();
        priorityCombo = new javax.swing.JComboBox();
        activeLabel = new javax.swing.JLabel();
        activeCheckBox = new javax.swing.JCheckBox();
        desktopLabel = new javax.swing.JLabel();
        desktopCheckBox = new javax.swing.JCheckBox();
        actionPanel = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Project");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        titleLabel.setText("Title");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 2);
        getContentPane().add(titleLabel, gridBagConstraints);

        titleText.setText("Project one");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        getContentPane().add(titleText, gridBagConstraints);

        titleDirectionButton.setText("Dir");
        titleDirectionButton.setToolTipText("Click to toggle component orientation");
        titleDirectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleDirectionButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        getContentPane().add(titleDirectionButton, gridBagConstraints);

        startLabel.setText("Start");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(startLabel, gridBagConstraints);

        startText.setColumns(10);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 2, 10, 10);
        getContentPane().add(startText, gridBagConstraints);

        endLabel.setText("End");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(endLabel, gridBagConstraints);

        endText.setColumns(10);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 2, 10, 10);
        getContentPane().add(endText, gridBagConstraints);

        categoryLabel.setText("Category");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(categoryLabel, gridBagConstraints);

        categoryCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 2, 10, 10);
        getContentPane().add(categoryCombo, gridBagConstraints);

        priorityLabel.setText("Priority");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(priorityLabel, gridBagConstraints);

        priorityCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 2, 10, 10);
        getContentPane().add(priorityCombo, gridBagConstraints);

        activeLabel.setText("Active");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(activeLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 2, 10, 10);
        getContentPane().add(activeCheckBox, gridBagConstraints);

        desktopLabel.setText("Desktop");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(desktopLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 2, 10, 10);
        getContentPane().add(desktopCheckBox, gridBagConstraints);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        actionPanel.add(cancelButton);

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        actionPanel.add(saveButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        getContentPane().add(actionPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        storePreferences();
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void titleDirectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleDirectionButtonActionPerformed
        ComponentOrientation orientation = titleText.getComponentOrientation();
        titleText.setComponentOrientation(orientation.equals(ComponentOrientation.RIGHT_TO_LEFT) ? ComponentOrientation.LEFT_TO_RIGHT : ComponentOrientation.RIGHT_TO_LEFT);
    }//GEN-LAST:event_titleDirectionButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        storePreferences();
        try {
            saveProject();
            mainFrame.setup();
            dispose();
        } catch (IOException | ClassNotFoundException | SQLException | ParseException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JCheckBox activeCheckBox;
    private javax.swing.JLabel activeLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox categoryCombo;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JCheckBox desktopCheckBox;
    private javax.swing.JLabel desktopLabel;
    private javax.swing.JLabel endLabel;
    private javax.swing.JTextField endText;
    private javax.swing.JComboBox priorityCombo;
    private javax.swing.JLabel priorityLabel;
    private javax.swing.JButton saveButton;
    private javax.swing.JLabel startLabel;
    private javax.swing.JTextField startText;
    private javax.swing.JButton titleDirectionButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField titleText;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }

    private void loadPreferences() {
        // Dialog size and position
        int x = preferences.getInt("EditProjectDialog.left", 10);
        int y = preferences.getInt("EditProjectDialog.top", 25);
        int w = preferences.getInt("EditProjectDialog.width", 800);
        int h = preferences.getInt("EditProjectDialog.height", 600);
        setBounds(x, y, w, h);
    }

    private void storePreferences() {
        // Dialog size and position
        preferences.putInt("EditProjectDialog.left", getBounds().x);
        preferences.putInt("EditProjectDialog.top", getBounds().y);
        preferences.putInt("EditProjectDialog.width", getBounds().width);
        preferences.putInt("EditProjectDialog.height", getBounds().height);
    }

    public void setup() throws IOException, ClassNotFoundException, SQLException {
        try (JdbcController controller = new JdbcController();) {
            fillCategoryList(controller);
            showProject();
        }
    }

    private void fillCategoryList(JdbcController controller) throws SQLException {
        categoryCombo.removeAllItems();
        List<Category> categories = controller.findAllCategories();
        for (Category category : categories) {
            categoryCombo.addItem(category);
        }
    }

    private void showProject() {
        titleText.setText(project.getTitle());
        startText.setText(dateFormat.format(project.getStartDate()));
        endText.setText(project.getEndDate() == null ? "" : dateFormat.format(project.getEndDate()));
        categoryCombo.setSelectedItem(project.getCategory());
        priorityCombo.setSelectedItem(String.valueOf(project.getPriority()));
        activeCheckBox.setSelected(project.isActive());
        desktopCheckBox.setSelected(project.isOnDesktop());
    }

    private void saveProject() throws IOException, ClassNotFoundException, SQLException, ParseException {
        collectInput();
        try (JdbcController controller = new JdbcController();) {
            controller.updateProject(project);
        }
    }

    private void collectInput() throws ParseException {
        project.setTitle(titleText.getText());
        String startDate = startText.getText().trim();
        project.setStartDate(dateFormat.parse(startDate));
        String endDate = endText.getText().trim();
        project.setEndDate(endDate.isEmpty() ? null : dateFormat.parse(endDate));
        project.setCategory((Category) categoryCombo.getSelectedItem());
        project.setPriority(Integer.parseInt(priorityCombo.getSelectedItem().toString()));
        project.setActive(activeCheckBox.isSelected());
        project.setOnDesktop(desktopCheckBox.isSelected());
    }

    /**
     * @return the mainFrame
     */
    public MainFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * @param mainFrame the mainFrame to set
     */
    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}
