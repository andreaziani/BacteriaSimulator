package view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import controller.SimulationState;
import model.PositionAlreadyOccupiedException;
import view.View;
import view.ViewController;
import view.model.ViewPositionImpl;
import view.model.ViewState;

/**
 * 
 * Represent the Main Frame of GUI and the entry-point for the user.
 */
public class UserInterface extends JFrame implements View, SimulationStateUpdatable {
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -6602885048333089318L;
    private boolean isSimulationRunning;
    private boolean isSimulationPaused;
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final int height = dim.height * 4 / 5;
    private final int width = dim.width * 4 / 5;
    private final LegendPanel legendPanel;
    private final SimulationPanel simulationPanel;
    private final TopPanel topPanel;
    private final ViewController view;

    /**
     * Constructor the MainFrame by passing a View.
     * 
     * @param view
     *            the View with which to interact.
     */
    public UserInterface(final ViewController view) {
        super("Bacteria Simulator");
        this.view = view;
        topPanel = new TopPanel(this.view, this);
        this.legendPanel = new LegendPanel(view);
        this.simulationPanel = new SimulationPanel(width, height, legendPanel);
        this.simulationPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent e) {
                if (!view.getFoodTypes().isEmpty() && (isSimulationRunning || isSimulationPaused)) {
                    try {
                        view.getController().addFoodFromView(view.getFoodTypes().get(topPanel.getSelectedFood()),
                                new ViewPositionImpl(e.getX(), e.getY()));
                    } catch (PositionAlreadyOccupiedException positionOccupied) {
                        JOptionPane.showMessageDialog(simulationPanel,
                                "You have already inserted a food in this position.");
                    }
                }

            }
        });
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent arg0) {
                view.setDimension(simulationPanel.getSize());
            }
        });
        this.viewSettings();
    }

    @Override
    public final void update(final ViewState state) {
        this.simulationPanel.setState(Optional.of(state));
        SwingUtilities.invokeLater(() -> simulationPanel.repaint());
        SwingUtilities.invokeLater(() -> simulationPanel.updateUI());
    }

    @Override
    public final void updateExistingFoods() {
        this.topPanel.updateFoods();
    }

    private void viewSettings() {
        this.view.setDimension(this.simulationPanel.getSize());
        this.setSize(width, height);
        this.getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.legendPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
        this.add(topPanel, BorderLayout.NORTH);
        this.add(simulationPanel, BorderLayout.CENTER);
        this.add(legendPanel, BorderLayout.EAST);
        this.setVisible(true);
    }

    @Override
    public final void updateSimulationState(final SimulationState state) {
        this.legendPanel.updateSimulationState(state);
        this.topPanel.updateSimulationState(state);
        this.simulationPanel.updateSimulationState(state);
        this.isSimulationRunning = (state == SimulationState.RUNNING);
        this.isSimulationPaused = (state == SimulationState.PAUSED);

        if (state == SimulationState.ENDED) {
            // TODO FIX? ONE MORE REPAINT TO COMPLETELY CLEAN THE PANEL,
            this.simulationPanel.setState(Optional.empty());
            SwingUtilities.invokeLater(() -> simulationPanel.repaint());
            SwingUtilities.invokeLater(() -> new AnalysisDialog(this, this.view));
        }
    }

    /**
     * Notify the presence of a change in species or foods that must be propagated
     * to all other panels interested.
     */
    public void notifyUpdate() {
        this.legendPanel.update();
    }
}
