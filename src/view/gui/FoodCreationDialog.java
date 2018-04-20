package view.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.tuple.Pair;

import model.AlreadyExistingFoodException;
import model.food.Nutrient;
import view.ViewController;
import view.model.food.CreationViewFoodImpl.ViewFoodBuilder;

/**
 * Frame for creation of new type of food.
 *
 */
public class FoodCreationDialog extends JDialog {
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = 82976646298898908L;
    private final int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
    private final int fontSize = (int) Math.round(12.0 * screenRes / 100.0);
    private final Font font = new Font("Arial", Font.PLAIN, fontSize);
    private ViewFoodBuilder builder;
    private final JPanel top = new JPanel();
    private final JPanel center = new JPanel();
    private final JPanel bot = new JPanel();
    private final JLabel setName = new JLabel("Name: ");
    private final JLabel selectNutrient = new JLabel("Select Nutrient: ");
    private final JLabel setQuantity = new JLabel("Set quantity: ");
    private final JTextField name = new JTextField(20);
    private final JTextField quantity = new JTextField(10);
    private final JComboBox<String> nutrients = new JComboBox<>();
    private final JButton addName = new JButton("Add name");
    private final JButton addNutrient = new JButton("Set Nutrient quantity");
    private final JButton createFood = new JButton("Create food");

    /**
     * Constructor the dialog by passing a View, his superPanel and the main frame.
     * 
     * @param view
     *            the View with which to interact.
     * @param superPanel
     *            the superPanel that's called the dialog.
     * @param main
     *            the JFrame that will be blocked by this dialog.
     */
    public FoodCreationDialog(final ViewController view, final SpeciesAndFoodPanel superPanel, final JFrame main) {
        super(main, "Create new Food", true);
        start(view);

        this.addName.addActionListener(e -> {
            try {
                this.builder = new ViewFoodBuilder(this.name.getText());
                this.addNutrient.setEnabled(true);
                this.name.setEditable(false);
                this.addName.setEnabled(false);
                this.quantity.setEnabled(true);
                this.nutrients.setEnabled(true);
            } catch (Exception exception) {
                final JLabel message = new JLabel("Food should have a name!");
                message.setFont(font);
                JOptionPane.showMessageDialog(this, message);
            }
        });

        this.addNutrient.addActionListener(e -> {
            try {
                this.builder.addNutrient(Pair.of(getSelectedNutrient(), Double.parseDouble(this.quantity.getText())));
                this.createFood.setEnabled(true);
            } catch (Exception exception1) {
                final JLabel message = new JLabel("ATTENTION SOMETHING'S WRONG!" + "\n"
                        + "-Check that you have entered numbers in quantity like 10.00");
                message.setFont(font);
                JOptionPane.showMessageDialog(this, message);
                this.dispose();
            }
        });

        this.createFood.addActionListener(e -> {
            try {
                view.getController().addNewTypeOfFood(this.builder.build());
                final JLabel message = new JLabel("Successful creation");
                message.setFont(font);
                JOptionPane.showMessageDialog(this, message);
                superPanel.updateFoods();
                this.dispose();
            } catch (AlreadyExistingFoodException exception2) {
                final JLabel message = new JLabel("INSERTION FAILED" + "\n" + "This food already exist!");
                message.setFont(font);
                JOptionPane.showMessageDialog(this, message);
                this.dispose();
            }
        });
        finalSettings();
    }

    private void start(final ViewController view) {
        this.setLayout(new BorderLayout());
        this.addNutrient.setEnabled(false);
        this.createFood.setEnabled(false);
        this.nutrients.setEnabled(false);
        this.quantity.setEnabled(false);
        this.name.setText("Food1");
        this.quantity.setText("300.00");
        view.getNutrients().forEach(n -> nutrients
                .addItem(n.substring(0, 1) + n.replaceAll("_", " ").substring(1).toLowerCase(Locale.getDefault())));
    }

    private void finalSettings() {
        this.top.add(this.setName);
        this.top.add(this.name);
        this.center.add(this.selectNutrient);
        this.center.add(this.nutrients);
        this.center.add(this.setQuantity);
        this.center.add(this.quantity);
        this.bot.add(this.addName);
        this.bot.add(this.addNutrient);
        this.bot.add(this.createFood);
        this.add(this.top, BorderLayout.NORTH);
        this.add(this.center, BorderLayout.CENTER);
        this.add(this.bot, BorderLayout.SOUTH);
        this.setName.setFont(font);
        this.name.setFont(font);
        this.selectNutrient.setFont(font);
        this.nutrients.setFont(font);
        this.setQuantity.setFont(font);
        this.quantity.setFont(font);
        this.addName.setFont(font);
        this.addNutrient.setFont(font);
        this.createFood.setFont(font);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private Nutrient getSelectedNutrient() {
        return Nutrient.values()[this.nutrients.getSelectedIndex()];
    }
}
