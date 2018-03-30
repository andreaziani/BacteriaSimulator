package view.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 
 * Dialog that explains how to use the simulator.
 *
 */
public class HelpDialog extends JDialog {
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -8816763436346148360L;
    private final File file = new File("help.txt");
    private final JPanel panel = new JPanel();
    private final JTextArea text = new JTextArea();

    /**
     * 
     * @param main
     *            the JFrame that will be blocked by this dialog.
     */
    public HelpDialog(final JFrame main) {
        super(main, "Help", true);
        try {
            final FileReader reader = new FileReader(this.file);
            final BufferedReader br = new BufferedReader(reader);
            this.text.read(br, "jTextArea1");
            br.close();
            this.text.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Help file not found");
        } 
        this.panel.add(this.text);
        this.text.setEditable(false);
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }
}
