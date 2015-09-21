/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sandbox.spring.batch.admin;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.DefaultListModel;
import my.sandbox.spring.batch.admin.util.JobExecutionWrapper;
import my.sandbox.spring.batch.admin.util.JobInstanceWrapper;
import my.sandbox.spring.batch.admin.util.StepExecutionWrapper;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author hilel-deb
 */
public class MainFrame extends javax.swing.JFrame {

    static final Logger logger = Logger.getLogger(MainFrame.class.getName());
    static Preferences preferences = Preferences.userNodeForPackage(MainFrame.class);
    private JobExplorer jobExplorer;
    private JobOperator jobOperator;

    /**
     * Injection setter for {@link JobExplorer}.
     *
     * @param jobExplorer the {@link JobExplorer} to set
     */
    public void setJobExplorer(JobExplorer jobExplorer) {
        this.jobExplorer = jobExplorer;
    }

    /**
     * Injection setter for {@link JobOperator}.
     *
     * @param jobOperator the {@link JobOperator} to set
     */
    public void setJobOperator(JobOperator jobOperator) {
        this.jobOperator = jobOperator;
    }

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        setup();
    }

    private void loadPreferences() {
        // frame size and location
        int x = preferences.getInt("MainFrame.left", 10);
        int y = preferences.getInt("MainFrame.top", 25);
        int w = preferences.getInt("MainFrame.width", 600);
        int h = preferences.getInt("MainFrame.height", 300);
        setBounds(x, y, w, h);
    }

    private void storePreferences() {
        // frame size and location
        preferences.putInt("MainFrame.left", getBounds().x);
        preferences.putInt("MainFrame.top", getBounds().y);
        preferences.putInt("MainFrame.width", getBounds().width);
        preferences.putInt("MainFrame.height", getBounds().height);
    }

    private void setup() {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("launch-context.xml");
        context.getAutowireCapableBeanFactory().autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
        loadPreferences();
        fillJobsList();
    }

    private void fillJobsList() {
        DefaultListModel model = new DefaultListModel();
        jobs.setModel(model);
        for (String job : jobExplorer.getJobNames()) {
            model.addElement(job);
        }
        jobs.setSelectedIndex(0);
        fillInstancesList();
    }

    private void fillInstancesList() {
        DefaultListModel model = new DefaultListModel();
        instances.setModel(model);
        String selectedJob = jobs.getSelectedValue().toString();
        for (JobInstance instance : jobExplorer.getJobInstances(selectedJob, 0, 999)) {
            model.addElement(new JobInstanceWrapper(instance, jobExplorer));
        }
        instances.setSelectedIndex(0);
        fillExecutionsList();
    }

    private void fillExecutionsList() {
        DefaultListModel model = new DefaultListModel();
        executions.setModel(model);
        JobInstanceWrapper selectedInstanceWrapper = (JobInstanceWrapper) instances.getSelectedValue();
        for (JobExecution jobExecution : jobExplorer.getJobExecutions(selectedInstanceWrapper.getJobInstance())) {
            model.addElement(new JobExecutionWrapper(jobExecution, jobExplorer));
        }
        executions.setSelectedIndex(0);
        JobExecutionWrapper selected = (JobExecutionWrapper) executions.getSelectedValue();
        stopButton.setEnabled(selected.isRunning() ? true : false);
        fillStepList();
    }

    private void fillStepList() {
        DefaultListModel model = new DefaultListModel();
        executionStepsList.setModel(model);
        JobExecutionWrapper selectedExecutionWrapper = (JobExecutionWrapper) executions.getSelectedValue();
        Collection<StepExecution> steps = selectedExecutionWrapper.getJobExecution().getStepExecutions();
        for (StepExecution step : steps) {
            model.addElement(new StepExecutionWrapper(step, jobExplorer));
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

        jobsLabel = new javax.swing.JLabel();
        jobsScroll = new javax.swing.JScrollPane();
        jobs = new javax.swing.JList();
        instanceLabel = new javax.swing.JLabel();
        instanceScroll = new javax.swing.JScrollPane();
        instances = new javax.swing.JList();
        executionsLabel = new javax.swing.JLabel();
        executionsScroll = new javax.swing.JScrollPane();
        executions = new javax.swing.JList();
        stopButton = new javax.swing.JButton();
        executionStepsLabel = new javax.swing.JLabel();
        executionStepsScroll = new javax.swing.JScrollPane();
        executionStepsList = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Spring Batch Admin");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jobsLabel.setText("Jobs");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jobsLabel, gridBagConstraints);

        jobs.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jobs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jobsMouseClicked(evt);
            }
        });
        jobs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jobsKeyReleased(evt);
            }
        });
        jobsScroll.setViewportView(jobs);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jobsScroll, gridBagConstraints);

        instanceLabel.setText("Instances");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(instanceLabel, gridBagConstraints);

        instances.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        instances.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                instancesMouseClicked(evt);
            }
        });
        instances.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                instancesKeyPressed(evt);
            }
        });
        instanceScroll.setViewportView(instances);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(instanceScroll, gridBagConstraints);

        executionsLabel.setText("Executions");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(executionsLabel, gridBagConstraints);

        executions.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        executionsScroll.setViewportView(executions);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(executionsScroll, gridBagConstraints);

        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(stopButton, gridBagConstraints);

        executionStepsLabel.setText("Steps");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(executionStepsLabel, gridBagConstraints);

        executionStepsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        executionStepsScroll.setViewportView(executionStepsList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(executionStepsScroll, gridBagConstraints);

        setBounds(0, 0, 530, 617);
    }// </editor-fold>//GEN-END:initComponents

    private void jobsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jobsKeyReleased
        fillInstancesList();
    }//GEN-LAST:event_jobsKeyReleased

    private void jobsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jobsMouseClicked
        fillInstancesList();
    }//GEN-LAST:event_jobsMouseClicked

    private void instancesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_instancesKeyPressed
        fillExecutionsList();
    }//GEN-LAST:event_instancesKeyPressed

    private void instancesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_instancesMouseClicked
        fillExecutionsList();
    }//GEN-LAST:event_instancesMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        storePreferences();
    }//GEN-LAST:event_formWindowClosing

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        JobExecutionWrapper selected = (JobExecutionWrapper) executions.getSelectedValue();
        try {
            jobOperator.stop(selected.getJobExecution().getId());
        } catch (NoSuchJobExecutionException | JobExecutionNotRunningException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_stopButtonActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
    private javax.swing.JLabel executionStepsLabel;
    private javax.swing.JList executionStepsList;
    private javax.swing.JScrollPane executionStepsScroll;
    private javax.swing.JList executions;
    private javax.swing.JLabel executionsLabel;
    private javax.swing.JScrollPane executionsScroll;
    private javax.swing.JLabel instanceLabel;
    private javax.swing.JScrollPane instanceScroll;
    private javax.swing.JList instances;
    private javax.swing.JList jobs;
    private javax.swing.JLabel jobsLabel;
    private javax.swing.JScrollPane jobsScroll;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
}
