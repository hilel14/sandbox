/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.todo;

import java.awt.ComponentOrientation;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 *
 * @author hilel
 */
public class BidiTextEditor extends javax.swing.JDialog {

    static final Logger logger = Logger.getLogger(BidiTextEditor.class.getName());
    static Preferences preferences = Preferences.userNodeForPackage(BidiTextEditor.class);
    String text = null;

    /**
     * Creates new form BidiTextEditor
     *
     * @param parent
     * @param modal
     */
    public BidiTextEditor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadPreferences();
    }

    public String showDialog() {
        setVisible(true);
        return text;
    }

    private void loadPreferences() {
        // Dialog size and position
        int x = preferences.getInt("BidiTextEditor.left", 10);
        int y = preferences.getInt("BidiTextEditor.top", 25);
        int w = preferences.getInt("BidiTextEditor.width", 800);
        int h = preferences.getInt("BidiTextEditor.height", 600);
        setBounds(x, y, w, h);
    }

    private void storePreferences() {
        // Dialog size and position
        preferences.putInt("BidiTextEditor.left", getBounds().x);
        preferences.putInt("BidiTextEditor.top", getBounds().y);
        preferences.putInt("BidiTextEditor.width", getBounds().width);
        preferences.putInt("BidiTextEditor.height", getBounds().height);
    }

    public void setInitialText(String text) {
        textField.setText(text);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textField = new javax.swing.JTextField();
        rtlButton = new javax.swing.JButton();
        ltrButton = new javax.swing.JButton();
        actionPanel = new javax.swing.JPanel();
        updateButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("BIDI Text Editor");
        getContentPane().add(textField, java.awt.BorderLayout.CENTER);

        rtlButton.setText("RTL");
        rtlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rtlButtonActionPerformed(evt);
            }
        });
        getContentPane().add(rtlButton, java.awt.BorderLayout.LINE_END);

        ltrButton.setText("LTR");
        ltrButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ltrButtonActionPerformed(evt);
            }
        });
        getContentPane().add(ltrButton, java.awt.BorderLayout.LINE_START);

        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });
        actionPanel.add(updateButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        actionPanel.add(cancelButton);

        getContentPane().add(actionPanel, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        text = textField.getText();
        storePreferences();
        dispose();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        storePreferences();
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void ltrButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ltrButtonActionPerformed
        textField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    }//GEN-LAST:event_ltrButtonActionPerformed

    private void rtlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rtlButtonActionPerformed
        textField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }//GEN-LAST:event_rtlButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton ltrButton;
    private javax.swing.JButton rtlButton;
    private javax.swing.JTextField textField;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
