package view.gui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.ViewController;
/**
 * 
 * Panel that contains all the functions on Bacterias and Foods.
 *
 */
public class SpeciesAndFoodPanel extends JPanel {
    private final JButton createFood = new JButton("Create Food");
    private final JButton createSpecies = new JButton("Create Species");
    private final JLabel selectFood = new JLabel("Select Food: ");
    private final JComboBox<String> foods = new JComboBox<>();
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -239604088646327360L;
    /**
     * Construct the panel by passing the view on which to handle the interactions.
     * @param view the view on which to handle the interactions.
     * @param main frame that's call this panel.
     */
    public SpeciesAndFoodPanel(final ViewController view, final JFrame main) {
        super();
        this.setLayout(new FlowLayout());
        this.foods.addItem("Select a food");
        this.createSpecies.addActionListener(e -> {
            new SpeciesCreationDialog(view, main);
        });
        this.createFood.addActionListener(e -> {
            new FoodCreationDialog(view, this, main);
        });
        this.add(this.selectFood);
        this.add(this.foods);
        this.add(this.createSpecies);
        this.add(this.createFood);
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
     * @param view from which to take existing food's name.
     */
    public void updateFoods(final ViewController view) {
        this.foods.removeAllItems();
        view.getFoodsType().forEach(f -> this.foods.addItem(f.getName()));
    }
}
