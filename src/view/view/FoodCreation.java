package view.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.View;
/**
 * Frame for creation of new type of food.
 *
 */
public class FoodCreation extends JFrame {
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = 82976646298898908L;

    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final int height = dim.height * 2 / 5;  // get 2/5 of the screen dimension.
    private final int width = dim.width * 2 / 5;    // get 2/5 of the screen dimension.
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
     * @param view the View with which to interact.
     */
    public FoodCreation(final View view) {
        super("Food Creation");
        this.setLayout(new BorderLayout());
        this.setSize(width, height);
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
        this.setVisible(true);
    }
}
