package view.view;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.tuple.Pair;

import model.food.Nutrient;
import utils.exceptions.AlreadyExistingFoodException;
import view.View;
import view.model.food.ViewFoodImpl.ViewFoodBuilder;

/**
 * Frame for creation of new type of food.
 *
 */
public class FoodCreation extends JFrame {
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = 82976646298898908L;
    private ViewFoodBuilder builder;
    private final JPanel top = new JPanel();
    private final JPanel center = new JPanel();
    private final JPanel bot = new JPanel();
    private final JLabel setName = new JLabel("Name: ");
    private final JTextField name = new JTextField(20);
    private final JLabel selectNutrient = new JLabel("Select Nutrient: ");
    private final JComboBox<String> nutrients = new JComboBox<>();
    private final JLabel setQuantity = new JLabel("Set quantity: ");
    private final JTextField quantity = new JTextField(10);
    private final JButton addName = new JButton("Add name");
    private final JButton addNutrient = new JButton("Add Nutrient");
    private final JButton createFood = new JButton("Create food");

    /**
     * Constructor the frame by passing a View.
     * 
     * @param view the View with which to interact.
     * @param superPanel that's called the frame.
     */
    public FoodCreation(final View view, final BacteriaAndFoodPanel superPanel) {
        super("Food Creation");
        this.setLayout(new BorderLayout());
        start(view);
        this.addName.addActionListener(e -> {
            try {
                this.builder = new ViewFoodBuilder(this.name.getText());
                this.addNutrient.setEnabled(true);
                this.addName.setEnabled(false);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Food should have a name!");
            }
        });

        this.addNutrient.addActionListener(e -> {
            try {
                this.builder.addNutrient(Pair.of(getSelectedNutrient(), Double.parseDouble(this.quantity.getText())));
                this.createFood.setEnabled(true);
            } catch (Exception exception1) {
                exception1.printStackTrace();
                JOptionPane.showMessageDialog(this, "ATTENTION SOMETHING'S WRONG!" + "\n"
                        + "-Check that you have entered numbers in quantity like 10.00");
            }
        });

        this.createFood.addActionListener(e -> {
            try {
                view.addNewTypeOfFood(this.builder.build());
                this.dispose();
                superPanel.updateFoods(view); //TODO PERCHE' NON WORKA?
            } catch (AlreadyExistingFoodException exception2) {
                JOptionPane.showMessageDialog(this, "This food already exist!");
                this.dispose();
            }
        });
        top.add(this.setName);
        top.add(this.name);
        center.add(this.selectNutrient);
        center.add(this.nutrients);
        center.add(this.setQuantity);
        center.add(this.quantity);
        bot.add(this.addName);
        bot.add(this.addNutrient);
        bot.add(this.createFood);
        this.add(this.top, BorderLayout.NORTH);
        this.add(this.center, BorderLayout.CENTER);
        this.add(this.bot, BorderLayout.SOUTH);
        
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private void start(final View view) {
        this.addNutrient.setEnabled(false);
        this.createFood.setEnabled(false);
        this.name.setText("Food1");
        this.quantity.setText("10.00");
        view.getNutrients().forEach(n -> nutrients.addItem(n));
    }

    private Nutrient getSelectedNutrient() {
        return Nutrient.valueOf((String) this.nutrients.getSelectedItem());
    }
}
