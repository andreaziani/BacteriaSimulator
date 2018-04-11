package view.gui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
    private final JButton createFood = new JButton("Create Food");
    private final JButton createSpecies = new JButton("Create Species");
    private final JButton setStrategy = new JButton("Set Strategy");
    private final JLabel selectFood = new JLabel("Select Food: ");
    private final JLabel selectStrategy = new JLabel("Select Strategy: ");
    private final JComboBox<String> foods = new JComboBox<>();
    private final JComboBox<String> strategies = new JComboBox<>();
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
    public SpeciesAndFoodPanel(final ViewController view, final JFrame main) {
        super();
        this.setLayout(new FlowLayout());
        this.foods.addItem("Select a food");
        this.foods.setEnabled(false);
        this.setStrategy.addActionListener(e -> {
            view.setDistributionStrategy(this.getSelectedStrategy());
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
        this.setBackground(Color.WHITE);
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
     * @param view
     *            from which to take existing food's name.
     */
    public void updateFoods(final ViewController view) {
        this.foods.removeAllItems();
        if (view.getFoodsType().isEmpty()) {
            this.foods.addItem("Select a Food");
            this.setEnabled(false);
        } else {
            this.foods.setEnabled(true);
            view.getFoodsName().forEach(f -> this.foods.addItem(f));
        }
    }

    private DistributionStrategy getSelectedStrategy() {
        return DistributionStrategy.valueOf((String) this.strategies.getSelectedItem());
    }

    @Override
    public void updateSimulationState(final SimulationState state) {
        SwingUtilities.invokeLater(() -> {
            if (state == SimulationState.NOT_READY || state == SimulationState.READY) {
                createFood.setEnabled(true);
                createSpecies.setEnabled(true);
                setStrategy.setEnabled(true);
                selectStrategy.setEnabled(true);
            } else {
                createFood.setEnabled(false);
                createSpecies.setEnabled(false);
                setStrategy.setEnabled(false);
                strategies.setEnabled(false);
            }
        });

    }
}
