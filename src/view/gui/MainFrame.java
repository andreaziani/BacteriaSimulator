package view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import utils.exceptions.PositionAlreadyOccupiedException;
import view.View;
import view.ViewController;
import view.model.ViewPositionImpl;
import view.model.ViewState;

/**
 * 
 * Main Frame of GUI.
 */
public class MainFrame extends JFrame implements View {
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -6602885048333089318L;
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final int height = dim.height * 2 / 3;
    private final int width = dim.width * 2 / 3;
    private final SimulationPanel simulationPanel = new SimulationPanel(width, height);
    private final TopPanel topPanel;
    private final ViewController view;
    /**
     * Constructor the MainFrame by passing a View.
     * @param view the View with which to interact.
     */
    public MainFrame(final ViewController view) {
        super("Bacteria Simulator");
        this.view = view;
        topPanel = new TopPanel(this.view, this);
        this.simulationPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent e) {
                if (!view.getFoodsType().isEmpty() && view.isSimulationStarter()) {
                    try {
                        view.addFood(view.getFoodsType().get(topPanel.getSelectedFood()), new ViewPositionImpl(e.getX(), e.getY()));
                    } catch (PositionAlreadyOccupiedException positionOccupied) {
                        JOptionPane.showMessageDialog(simulationPanel, "You have already inserted a food in this position.");
                    }
                }

            }
        });
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent arg0) {
                view.setDimension(simulationPanel.getSize());
//                System.out.println(simulationPanel.getSize().width + " " + simulationPanel.getSize().height);
            }
        });
        this.viewSettings();
    }
    @Override
    public void update(final ViewState state) {
        this.simulationPanel.setState(state);
        simulationPanel.repaint();
    }
    @Override
    public void updateExistingFoods() {
        this.topPanel.updateFoods(this.view);
    }
    private void viewSettings() {
        this.setSize(width, height);
        this.view.setDimension(this.simulationPanel.getSize());
        this.getContentPane().setBackground(Color.WHITE);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(simulationPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
