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
 * Analysis dialog.
 *
 */
public class AnalysisDialog extends JDialog {

    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = 7438374778430885270L;

    /**
     * 
     * @param mainframe the MainFrame.
     * @param controller the ViewController.
     */
    public AnalysisDialog(final UserInterface mainframe, final ViewController controller) {
        super(mainframe, true);
        final JTextArea jTextArea = new JTextArea();
        final Analysis analysis = controller.getController().getAnalysis();
        final JPanel pAnalysis = new JPanel();
        pAnalysis.setBackground(Color.WHITE);
        pAnalysis.add(jTextArea);
        jTextArea.setText(analysis.getDescription());
        jTextArea.setEditable(false);
        final JButton bt = new JButton("Save Analysis");
        bt.addActionListener(e -> {
            final JFileChooser analysisChooser = new JFileChooser();
            analysisChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            analysisChooser.setDialogTitle("Choose a file");
            if (analysisChooser.showSaveDialog(mainframe) == JFileChooser.APPROVE_OPTION) {
                try {
                    controller.getController().saveAnalysis(analysisChooser.getSelectedFile().getPath());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "An error occurred trying to save the analysis");
                }
            }
        });
        this.add(pAnalysis);
        pAnalysis.add(bt);
        this.pack();
        this.setVisible(true);
    }
}
