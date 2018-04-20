package view.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.SimulationState;
import model.food.insertionstrategy.position.DistributionStrategy;
import view.ViewController;

/**
 * 
 * Panel that contains all the functions on Bacterias and Foods.
 *
 */
public class SpeciesAndFoodPanel extends JPanel implements SimulationStateUpdatable {
    private final int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
    private final int fontSize = (int) Math.round(12.0 * screenRes / 100.0);
    private final Font font = new Font("Arial", Font.PLAIN, fontSize);
    private final JButton createFood = new JButton("Create Food");
    private final JButton createSpecies = new JButton("Create Species");
    private final JButton setStrategy = new JButton("Set Strategy");
    private final JLabel selectFood = new JLabel("Select Food: ");
    private final JLabel selectStrategy = new JLabel("Select Strategy: ");
    private final JComboBox<String> foods = new JComboBox<>();
    private final JComboBox<String> strategies = new JComboBox<>();
    private final ViewController view;
    private final UserInterface mainFrame;
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -239604088646327360L;

    /**
     * Construct the panel by passing the view on which to handle the interactions.
     * 
     * @param view
     *            the view on which to handle the interactions.
     * @param main
     *            frame that's call this panel.
     */
    public SpeciesAndFoodPanel(final ViewController view, final UserInterface main) {
        super();
        this.mainFrame = main;
        this.setLayout(new FlowLayout());
        this.foods.addItem("Select a food");
        this.foods.setEnabled(false);
        this.view = view;
        this.setStrategy.addActionListener(e -> {
            view.getController().setDistributionStrategy(this.getSelectedStrategy());
        });
        view.getAvailableDistributionStrategies().forEach(d -> this.strategies.addItem(d));
        this.createSpecies.addActionListener(e -> {
            new SpeciesCreationDialog(view, main);
        });
        this.createFood.addActionListener(e -> {
            new FoodCreationDialog(view, this, main);
        });
        this.add(this.selectStrategy);
        this.add(this.strategies);
        this.add(this.setStrategy);
        this.add(this.selectFood);
        this.add(this.foods);
        this.add(this.createFood);
        this.add(this.createSpecies);
        this.setOpaque(true);
        this.createFood.setFont(font);
        this.createSpecies.setFont(font);
        this.foods.setFont(font);
        this.setStrategy.setFont(font);
        this.strategies.setFont(font);
        this.selectFood.setFont(font);
        this.selectStrategy.setFont(font);
    }

    /**
     * 
     * @return the index of selected food.
     */
    public int getSelectedFood() {
        return this.foods.getSelectedIndex();
    }

    /**
     * Update food's type.
     * 
     */
    public void updateFoods() {
        this.foods.removeAllItems();
        if (view.getFoodTypes().isEmpty()) {
            this.foods.addItem("Select a Food");
            this.setEnabled(false);
        } else {
            this.foods.setEnabled(true);
            view.getFoodNames().forEach(f -> this.foods.addItem(f));
        }
        mainFrame.notifyUpdate();
    }

    private DistributionStrategy getSelectedStrategy() {
        return DistributionStrategy.values()[this.strategies.getSelectedIndex()];
    }

    @Override
    public final void updateSimulationState(final SimulationState state) {
        SwingUtilities.invokeLater(() -> {
            updateFoods();
            if (state == SimulationState.NOT_READY || state == SimulationState.READY) {
                createFood.setEnabled(true);
                createSpecies.setEnabled(true);
                setStrategy.setEnabled(true);
                strategies.setEnabled(true);
            } else {
                createFood.setEnabled(false);
                createSpecies.setEnabled(false);
                setStrategy.setEnabled(false);
                strategies.setEnabled(false);
            }
        });

    }
}
