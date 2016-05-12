package com.mycompany.pdf;

import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripperByArea;

/**
 *
 * @author hilel
 */
public class MainFrame extends javax.swing.JFrame {

    static final Logger LOGGER = Logger.getLogger(MainFrame.class.getName());
    static Preferences preferences = Preferences.userNodeForPackage(MainFrame.class);
    final JFileChooser chooser = new JFileChooser();
    File pdfFile;
    BufferedImage image;
    int areaLabelX;
    int areaLabelY;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        loadPreferences();
        setup();
    }

    private void setup() {
        chooser.setFileFilter(new FileNameExtensionFilter("PDF files", "pdf", "PDF"));
        areaLabelToTextInput();
    }

    private void loadPreferences() {
        // Main frame size and position
        int x = preferences.getInt("MainFrame.left", 10);
        int y = preferences.getInt("MainFrame.top", 25);
        int w = preferences.getInt("MainFrame.width", 800);
        int h = preferences.getInt("MainFrame.height", 600);
        setBounds(x, y, w, h);
    }

    private void storePreferences() {
        // Main frame size and position
        preferences.putInt("MainFrame.left", getBounds().x);
        preferences.putInt("MainFrame.top", getBounds().y);
        preferences.putInt("MainFrame.width", getBounds().width);
        preferences.putInt("MainFrame.height", getBounds().height);
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

        toolsPanel = new javax.swing.JPanel();
        xLabel = new javax.swing.JLabel();
        xText = new javax.swing.JTextField();
        yLabel = new javax.swing.JLabel();
        yText = new javax.swing.JTextField();
        wLabel = new javax.swing.JLabel();
        wText = new javax.swing.JTextField();
        hLabel = new javax.swing.JLabel();
        hText = new javax.swing.JTextField();
        imageScroll = new javax.swing.JScrollPane();
        imageLayeredPane = new javax.swing.JLayeredPane();
        areaLabel = new javax.swing.JLabel();
        imageLabel = new javax.swing.JLabel();
        actionPanel = new javax.swing.JPanel();
        textScroll = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        extractButton = new javax.swing.JButton();
        statusLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openPdfMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        openJobMenuItem = new javax.swing.JMenuItem();
        saveJobMenuItem = new javax.swing.JMenuItem();
        saveJobAsMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PDF Text Marker");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        xLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        xLabel.setText("X");
        toolsPanel.add(xLabel);

        xText.setColumns(3);
        xText.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        xText.setText("10");
        xText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xTextActionPerformed(evt);
            }
        });
        toolsPanel.add(xText);

        yLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        yLabel.setText("Y");
        toolsPanel.add(yLabel);

        yText.setColumns(3);
        yText.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        yText.setText("10");
        yText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yTextActionPerformed(evt);
            }
        });
        toolsPanel.add(yText);

        wLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        wLabel.setText("W");
        toolsPanel.add(wLabel);

        wText.setColumns(3);
        wText.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        wText.setText("300");
        wText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wTextActionPerformed(evt);
            }
        });
        toolsPanel.add(wText);

        hLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        hLabel.setText("H");
        toolsPanel.add(hLabel);

        hText.setColumns(3);
        hText.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        hText.setText("50");
        hText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hTextActionPerformed(evt);
            }
        });
        toolsPanel.add(hText);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(toolsPanel, gridBagConstraints);

        imageLayeredPane.setPreferredSize(new java.awt.Dimension(595, 842));

        areaLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        areaLabel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                areaLabelMouseDragged(evt);
            }
        });
        areaLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                areaLabelMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                areaLabelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                areaLabelMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                areaLabelMouseEntered(evt);
            }
        });
        imageLayeredPane.add(areaLabel);
        areaLabel.setBounds(100, 60, 300, 50);

        imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hilel14.jpg"))); // NOI18N
        imageLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        imageLayeredPane.add(imageLabel);
        imageLabel.setBounds(0, 0, 200, 200);

        imageScroll.setViewportView(imageLayeredPane);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.3;
        getContentPane().add(imageScroll, gridBagConstraints);

        actionPanel.setLayout(new java.awt.GridBagLayout());

        textArea.setColumns(20);
        textArea.setRows(5);
        textScroll.setViewportView(textArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        actionPanel.add(textScroll, gridBagConstraints);

        extractButton.setText("Extract Text");
        extractButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                extractButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        actionPanel.add(extractButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        getContentPane().add(actionPanel, gridBagConstraints);

        statusLabel.setText("Status...");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        getContentPane().add(statusLabel, gridBagConstraints);

        fileMenu.setText("File");

        openPdfMenuItem.setText("Open PDF...");
        openPdfMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openPdfMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openPdfMenuItem);
        fileMenu.add(jSeparator1);

        openJobMenuItem.setText("Open Job...");
        fileMenu.add(openJobMenuItem);

        saveJobMenuItem.setText("Save Job");
        fileMenu.add(saveJobMenuItem);

        saveJobAsMenuItem.setText("Save Job As...");
        fileMenu.add(saveJobAsMenuItem);

        jMenuBar1.add(fileMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        storePreferences();
    }//GEN-LAST:event_formWindowClosing

    private void extractButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_extractButtonActionPerformed
        extractTextByArea();
    }//GEN-LAST:event_extractButtonActionPerformed

    private void areaLabelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_areaLabelMouseDragged
        int x = areaLabel.getBounds().x + evt.getX() - areaLabelX;
        xText.setText(String.valueOf(x));
        int y = areaLabel.getBounds().y + evt.getY() - areaLabelY;
        yText.setText(String.valueOf(y));
        areaLabel.setBounds(x, y, areaLabel.getBounds().width, areaLabel.getBounds().height);
    }//GEN-LAST:event_areaLabelMouseDragged

    private void areaLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_areaLabelMouseClicked

    }//GEN-LAST:event_areaLabelMouseClicked

    private void openPdfMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openPdfMenuItemActionPerformed
        showOpenDialog();
    }//GEN-LAST:event_openPdfMenuItemActionPerformed

    private void areaLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_areaLabelMouseEntered
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_areaLabelMouseEntered

    private void areaLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_areaLabelMouseExited
        setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_areaLabelMouseExited

    private void xTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xTextActionPerformed
        setAreaLabelBounds();
    }//GEN-LAST:event_xTextActionPerformed

    private void yTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yTextActionPerformed
        setAreaLabelBounds();
    }//GEN-LAST:event_yTextActionPerformed

    private void wTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wTextActionPerformed
        setAreaLabelBounds();
    }//GEN-LAST:event_wTextActionPerformed

    private void hTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hTextActionPerformed
        setAreaLabelBounds();
    }//GEN-LAST:event_hTextActionPerformed

    private void areaLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_areaLabelMousePressed
        areaLabelX = evt.getX();
        areaLabelY = evt.getY();
    }//GEN-LAST:event_areaLabelMousePressed

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
    private javax.swing.JPanel actionPanel;
    private javax.swing.JLabel areaLabel;
    private javax.swing.JButton extractButton;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel hLabel;
    private javax.swing.JTextField hText;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLayeredPane imageLayeredPane;
    private javax.swing.JScrollPane imageScroll;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem openJobMenuItem;
    private javax.swing.JMenuItem openPdfMenuItem;
    private javax.swing.JMenuItem saveJobAsMenuItem;
    private javax.swing.JMenuItem saveJobMenuItem;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JTextArea textArea;
    private javax.swing.JScrollPane textScroll;
    private javax.swing.JPanel toolsPanel;
    private javax.swing.JLabel wLabel;
    private javax.swing.JTextField wText;
    private javax.swing.JLabel xLabel;
    private javax.swing.JTextField xText;
    private javax.swing.JLabel yLabel;
    private javax.swing.JTextField yText;
    // End of variables declaration//GEN-END:variables

    private void showOpenDialog() {
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            pdfFile = chooser.getSelectedFile();
            //pdfFileText.setText(pdfFile.getAbsolutePath());
            new Worker().execute();
        }
    }

    private void extractTextByArea() {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDPage page = document.getPage(0);
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.addRegion("region1", areaLabel.getBounds());
            stripper.extractRegions(page);
            textArea.setText(stripper.getTextForRegion("region1"));
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            statusLabel.setText(ex.toString());
        }
    }

    private void areaLabelToTextInput() {
        Rectangle rect = areaLabel.getBounds();
        xText.setText(String.valueOf(rect.x));
        yText.setText(String.valueOf(rect.y));
        wText.setText(String.valueOf(rect.width));
        hText.setText(String.valueOf(rect.height));
    }

    private void setAreaLabelBounds() {
        int x = Integer.parseInt(xText.getText());
        int y = Integer.parseInt(yText.getText());
        int w = Integer.parseInt(wText.getText());
        int h = Integer.parseInt(hText.getText());
        areaLabel.setBounds(x, y, w, h);
    }

    class Worker extends SwingWorker<Void, Integer> {

        @Override
        protected Void doInBackground() {
            // adjust gui controls
            openPdfMenuItem.setEnabled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            statusLabel.setText("Loading PDF file...");
            // render first page of PDF file and display it in image label
            try (PDDocument document = PDDocument.load(pdfFile)) {
                long start = Calendar.getInstance().getTimeInMillis();
                PDFRenderer renderer = new PDFRenderer(document);
                image = renderer.renderImageWithDPI(0, 72, ImageType.RGB);
                //imageLabel.setBackground(new Color(0,0,0,0));
                imageLabel.setBounds(0, 0, image.getWidth(), image.getHeight());
                //imageLabel.getGraphics().drawImage(image, 0, 0, null);
                imageLabel.setIcon(new ImageIcon(image));
                long end = Calendar.getInstance().getTimeInMillis();
                String msg = "Document loaded in " + String.valueOf(end - start) + " milliseconds";
                statusLabel.setText(msg);
                repaint();
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Error loading PDF file", ex);
                statusLabel.setText(ex.toString());
            }
            // restore gui controls
            openPdfMenuItem.setEnabled(true);
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            // done
            return null;
        }
    }
}
