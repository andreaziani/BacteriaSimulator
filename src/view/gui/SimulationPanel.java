package view.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
//import java.awt.Color;
//import java.awt.Point;
//import java.util.HashMap;
//import java.util.Map;
import java.util.Optional;

import javax.swing.JPanel;

import controller.SimulationState;
import view.Radius;
import view.model.ViewState;

/**
 * Panel that represent the running simulation.
 *
 */
public final class SimulationPanel extends JPanel implements SimulationStateUpdatable {
    // private static final int RADIUS = 15;
    // private final Map<Point, Color> foods = new HashMap<>();
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = 2015198232069587535L;
    private final ColorAssigner colorAssigner;
    private Optional<ViewState> state = Optional.empty();
    private SimulationState simState = SimulationState.NOT_READY;

    /**
     * 
     * @param width
     *            the max width of the panel.
     * @param height
     *            the max height of the panel.
     * @param colorAssigner
     *            a strategy that chooses colors for foods and bacterias.
     */
    public SimulationPanel(final int width, final int height, final ColorAssigner colorAssigner) {
        super();
        this.colorAssigner = colorAssigner;
        this.setSize(width, height);
        this.setLayout(new FlowLayout());
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
        this.setVisible(true);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (state.isPresent() && this.simState != SimulationState.NOT_READY) {
            state.get().getFoodsState().entrySet().stream().forEach(e -> {
                g.setColor(colorAssigner.getColorFromFood(e.getValue()));
                final Radius radius = e.getValue().getRadius();
                g.fillRect((int) e.getKey().getX() - radius.getXRadius(), (int) e.getKey().getY() - radius.getYRadius(),
                        2 * radius.getXRadius(), 2 * radius.getYRadius());
            });

            state.get().getBacteriaState().entrySet().stream().forEach(e -> {
                // TODO probably there is a better way
                final Graphics2D g2d = (Graphics2D) g;
                final Radius radius = e.getValue().getRadius();
                final Ellipse2D.Double circle = new Ellipse2D.Double((int) e.getKey().getX() - radius.getXRadius(),
                        (int) e.getKey().getY() - radius.getYRadius(), 2 * radius.getXRadius(),
                        2 * radius.getYRadius());
                g.setColor(colorAssigner.getColorFromSpecies(e.getValue().getSpecies()));
                g2d.fill(circle);
            });
        }
    }

    /**
     * Set the state of the objects in simulation panel.
     * 
     * @param state
     *            the state of the objects in the simulation.
     */
    public void setState(final Optional<ViewState> state) {
        this.state = state;
    }

    @Override
    public void updateSimulationState(final SimulationState state) {
        this.simState = state;
    }
}
