package view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
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
 * View of the Analysis.
 *
 */

public class AnalysisDialog extends JDialog {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
    private final int fontSize = (int) Math.round(12.0 * screenRes / 100.0);
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final int height = dim.height * 3 / 5;
    private final int width = dim.width * 3 / 5;
    private final Font font = new Font("Arial", Font.PLAIN, fontSize);
/**
 * View of the Analysis.
 * @param mainframe
 *              interface of the bacteria simulator.
 * @param controller
 *              view controller that contains analysis of bacteria.
 */
    public AnalysisDialog(final UserInterface mainframe, final ViewController controller) {
        super(mainframe, true);

        //final JTextArea jTextArea = new JTextArea();
        final JTextArea wins = new JTextArea();
        final JTextArea nByS = new JTextArea();
        final JTextArea dead = new JTextArea();
        final JTextArea bactMutated = new JTextArea();
        final JTextArea survived = new JTextArea();
        final JTextArea jTextArea = new JTextArea();
        jTextArea.setFont(font);
        wins.setFont(font);
        nByS.setFont(font);
        dead.setFont(font);
        bactMutated.setFont(font);
        survived.setFont(font);
        final Analysis analysis = controller.getController().getAnalysis();
        final JPanel pAnalysis = new JPanel();
        final JButton bt = new JButton("Save Analysis");
        bt.setFont(font);
        pAnalysis.setBackground(Color.WHITE);

        //pAnalysis.add(jTextArea);
        pAnalysis.add(wins);
        pAnalysis.add(nByS);
        pAnalysis.add(dead);
        pAnalysis.add(bactMutated);
        pAnalysis.add(survived);

        //jTextArea.setText(analysis.getDescription());
        wins.setText("Predominant Species:" + "\n" + analysis.resultPredominant());
        nByS.setText("Quantity of bacteria per Species:" + "\n" + analysis.numberBySpecies());
        dead.setText("Species are dead:" + "\n" + analysis.resultDead());
        bactMutated.setText("Quantity of bacteria mutated per Species:" + "\n" + analysis.resultBactMutated());
        survived.setText("Species are survived:" + "\n" + analysis.resultSurvived());

        //jTextArea.setEditable(false);
        wins.setEditable(false);
        nByS.setEditable(false);
        dead.setEditable(false);
        bactMutated.setEditable(false);
        survived.setEditable(false);

        bt.addActionListener(e -> {
            final JFileChooser analysisChooser = new JFileChooser();
            analysisChooser.setPreferredSize(new Dimension(width, height));
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
