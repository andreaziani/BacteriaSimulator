package view.gui;

//import java.awt.Color;
import java.awt.Graphics;
//import java.awt.Point;
//import java.util.HashMap;
//import java.util.Map;

import javax.swing.JPanel;
import view.model.food.ViewFood;

/**
 * Panel that represent the running simulation.
 *
 */
public class SimulationPanel extends JPanel {
//    private static final int RADIUS = 30;
//    private final Map<Point, Color> foods = new HashMap<>();
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = 2015198232069587535L;

    @Override
    protected final void paintComponent(final Graphics g) {
//        super.paintComponent(g);
//        for (final Map.Entry<Point, Color> e : this.foods.entrySet()) {
//            g.setColor(e.getValue());
//            g.fillOval(e.getKey().x, e.getKey().y, RADIUS * 2, RADIUS * 2);
//        }
    }

    /**
     * 
     * @param x
     *            coordinate.
     * @param y
     *            coordinate.
     * @param food
     *            to add.
     */
    public void addFood(final int x, final int y, final ViewFood food) {
//        this.foods.put(new Point(x - RADIUS, y - RADIUS), food.getColor());
    }
}
