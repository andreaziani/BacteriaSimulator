package view.view;

import java.awt.BorderLayout;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import view.View;
/**
 * Top Panel of MainFrame.
 *
 */
public class TopPanel extends JPanel {
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("File");
    private final JMenu helpMenu = new JMenu("Help");
    private final JMenuItem loadSimulation = new JMenuItem("Load Simulation");
    private final JMenuItem help = new JMenuItem("Help");
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = 2643418239937346321L;
    /**
     * Constructor.
     * @param view the View with which to interact.
     */
    public TopPanel(final View view) {
        super(new BorderLayout());
        final JPanel bacteriaAndFood = new BacteriaAndFoodPanel(view);
        final JPanel choicesPanel = new ChoicesPanel(view);
        this.fileMenu.setActionCommand("File");
        this.fileMenu.add(loadSimulation);
        this.helpMenu.add(help);
        this.menuBar.add(this.fileMenu);
        this.menuBar.add(helpMenu);
        this.add(menuBar, BorderLayout.NORTH);
        this.add(choicesPanel, BorderLayout.SOUTH);
        this.add(bacteriaAndFood, BorderLayout.CENTER);
    }

}
