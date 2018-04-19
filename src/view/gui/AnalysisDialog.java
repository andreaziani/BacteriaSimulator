package view.gui;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Analysis;
import view.ViewController;

/**
 * 
 * @author Gloria
 *
 */

public class AnalysisDialog extends JDialog {
    /**
     * 
     */
<<<<<<< HEAD
    private static final long serialVersionUID = 1L;
/**
 * View of the Analysis.
 * @param mainframe
 *              Main frame of the bacteria simulator.
 * @param controller
 *              where get analysis.
 */
    public AnalysisDialog(final MainFrame mainframe, final ViewController controller) {
=======
    public AnalysisDialog(final UserInterface mainframe, final ViewController controller) {
>>>>>>> a3adf6849589fa00698ad4b72b7d22c442f5fb76
        super(mainframe, true);
        final JTextArea jTextArea = new JTextArea();
        final Analysis analysis = controller.getController().getAnalysis();
        final JPanel pAnalysis = new JPanel();
        final JButton bt = new JButton("Save Analysis");
        pAnalysis.setBackground(Color.WHITE);
        pAnalysis.add(jTextArea);
        jTextArea.setText(analysis.getDescription());
        jTextArea.setEditable(false);
        bt.addActionListener(e -> {
            final JFileChooser analysisChooser = new JFileChooser();
            analysisChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            analysisChooser.setDialogTitle("Choose a file");
            if (analysisChooser.showSaveDialog(mainframe) == JFileChooser.APPROVE_OPTION) {
                try {
                    controller.getController().saveAnalysis(analysisChooser.getSelectedFile().getPath());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "...");
                }
            }
        });
        this.add(pAnalysis);
        pAnalysis.add(bt);
        this.pack();
        this.setVisible(true);
    }
}
