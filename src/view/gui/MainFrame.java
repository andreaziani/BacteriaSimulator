package view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import controller.ControllerImpl;
import view.View;
import view.ViewImpl;
import view.model.ViewPositionImpl;

/**
 * 
 * Main Frame of GUI.
 */
public class MainFrame extends JFrame {
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -6602885048333089318L;
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final SimulationPanel centerPanel = new SimulationPanel(); //TODO pannello simulazione.
    private final int height = dim.height * 2 / 3;  // get 2/3 of the screen dimension.
    private final int width = dim.width * 2 / 3;    // get 2/3 of the screen dimension.
    /**
     * Constructor the MainFrame by passing a View.
     * @param view the View with which to interact.
     */
    public MainFrame(final View view) {
        super("Bacteria Simulator");
        final TopPanel topPanel = new TopPanel(view, this);
        this.setSize(width, height);
        this.centerPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent e) {
                if (!view.getFoodsType().isEmpty()) {
                    view.addFood(view.getFoodsType().get(topPanel.getSelectedFood()), new ViewPositionImpl(e.getX(), e.getY()));
//                    centerPanel.addFood(e.getX(), e.getY(), view.getFoodsType().get(topPanel.getSelectedFood()));
//                    centerPanel.repaint();
                }

            }
        });
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent arg0) {
                view.setDimension(centerPanel.getSize());
            }
        });
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    /**
     * Solo per testare.
     * @param args args.
     */
    public static void main(final String[] args) {
        new MainFrame(new ViewImpl(new ControllerImpl()));
    }
}
