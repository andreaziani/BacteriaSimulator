package view.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
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
    private final SpeciesAndFoodPanel speciesAndFood;
    private final ChoicesPanel choicesPanel;
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = 2643418239937346321L;
    /**
     * Constructor.
     * @param view the View with which to interact.
     * @param main frame that's call this panel.
     */
    public TopPanel(final View view, final JFrame main) {
        super(new BorderLayout());
        this.speciesAndFood = new SpeciesAndFoodPanel(view, main);
        this.choicesPanel = new ChoicesPanel(view);
        this.componentsSettings();
        this.add(menuBar, BorderLayout.NORTH);
        this.add(this.choicesPanel, BorderLayout.SOUTH);
        this.add(this.speciesAndFood, BorderLayout.CENTER);
    }
    /**
     * 
     * @return the name of the selected food.
     */
    public int getSelectedFood() {
        return this.speciesAndFood.getSelectedFood();
    }

    private void componentsSettings() {
        this.fileMenu.setActionCommand("File");
        this.fileMenu.add(loadSimulation);
        this.helpMenu.add(help);
        this.menuBar.add(this.fileMenu);
        this.menuBar.add(helpMenu);
    }
}
