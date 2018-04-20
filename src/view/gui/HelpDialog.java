package view.gui;

import java.awt.Font;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
    private final int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
    private final int fontSize = (int) Math.round(12.0 * screenRes / 100.0);
    private final Font font = new Font("Arial", Font.PLAIN, fontSize);
    private final JPanel panel = new JPanel();
    private final JTextArea text = new JTextArea();
    private final InputStream url = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/help.txt");
    /**
     * 
     * @param main
     *            the JFrame that will be blocked by this dialog.
     */
    public HelpDialog(final JFrame main) {
        super(main, "Help", true);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url, StandardCharsets.UTF_8))) {
            this.text.read(br, "jTextArea1");
            this.text.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Help file not found");
        } 
        this.panel.add(this.text);
        this.text.setEditable(false);
        this.add(panel);
        this.text.setFont(font);
        this.pack();
        this.setVisible(true);
    }
}
