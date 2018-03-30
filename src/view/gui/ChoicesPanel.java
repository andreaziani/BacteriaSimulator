package view.gui;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import view.View;
/**
 * Panel that contains all the components that allow the user
 * to choose whether to start and stop the simulation.
 *
 */
public class ChoicesPanel extends JPanel {
    private final JButton startSimulation = new JButton("Start");
    private final JButton stopSimulation = new JButton("Stop");
    private final JButton pauseSimulation = new JButton("Pause");
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -5569246934783157059L;
    /**
     * Construct the panel by passing the view on which to handle the interactions.
     * @param view the view on which to handle the interactions.
     */
    public ChoicesPanel(final View view) {
        super();
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.add(this.startSimulation);
        this.add(this.pauseSimulation);
        this.add(this.stopSimulation);
        this.stopSimulation.setEnabled(false);
        this.pauseSimulation.setEnabled(false);
    }

}
