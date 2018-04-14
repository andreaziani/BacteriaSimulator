package view.gui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.SimulationState;
import view.ViewController;

/**
 * Panel that contains all the components that allow the user to choose whether
 * to start and stop the simulation.
 *
 */
public class ChoicesPanel extends JPanel implements SimulationStateUpdatable {
    private final JButton startSimulation = new JButton("Start");
    private final JButton stopSimulation = new JButton("Stop");
    private final JButton pauseSimulation = new JButton("Pause");
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -5569246934783157059L;

    /**
     * Construct the panel by passing the view on which to handle the interactions.
     * 
     * @param view
     *            the view controller on which to handle the interactions.
     */
    public ChoicesPanel(final ViewController view) {
        super();
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.startSimulation.addActionListener(e -> {
            //if (!view.getFoodsType().isEmpty() && !view.isSpeciesEmpty()) {
                view.startSimulation();
            //} else {
                //JOptionPane.showMessageDialog(this, "You must insert one species and one type of food");
            //}
        });
        this.add(this.startSimulation);
        this.add(this.pauseSimulation);
        this.add(this.stopSimulation);
        this.startSimulation.setEnabled(false);
        this.stopSimulation.setEnabled(false);
        this.pauseSimulation.setEnabled(false);
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
    }

    @Override
    public final void updateSimulationState(final SimulationState state) {
        SwingUtilities.invokeLater(() -> {
            switch (state) {
            case RUNNING:
            case REPLAY:
                startSimulation.setEnabled(false);
                stopSimulation.setEnabled(true);
                pauseSimulation.setEnabled(true);
                break;
            case READY: 
            case PAUSED:
                startSimulation.setEnabled(true);
                pauseSimulation.setEnabled(false);
                stopSimulation.setEnabled(false);
                break;
            default:
                startSimulation.setEnabled(false);
                stopSimulation.setEnabled(false);
                pauseSimulation.setEnabled(false);
                break;
            }
        }); 
    }
}
